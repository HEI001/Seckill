package com.hei001.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hei001.seckill.exception.GlobalException;
import com.hei001.seckill.mapper.OrderInfoMapper;
import com.hei001.seckill.mapper.OrderMapper;
import com.hei001.seckill.pojo.GoodsSeckill;
import com.hei001.seckill.pojo.Order;
import com.hei001.seckill.pojo.OrderInfo;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IGoodsSeckillService;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IOrderService;
import com.hei001.seckill.utils.MD5Util;
import com.hei001.seckill.utils.UUIDUtil;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.OrderDetailVo;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IGoodsSeckillService goodsSeckillService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀
     *
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    @Override
    public OrderInfo seckill(User user, GoodsVo goodsVo) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //秒杀商品表的库存
        GoodsSeckill goods_Seckill = goodsSeckillService.getOne(new QueryWrapper<GoodsSeckill>().eq("goods_id",
                goodsVo.getId()));
        goods_Seckill.setStockCount(goods_Seckill.getStockCount() - 1);
        /*boolean result = goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>().set("stock_count",
                goods_Seckill.getStockCount()).eq("id", goods_Seckill.getId()).gt("stock_count", 0));*/
        boolean result = goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>().setSql("stock_count = stock_count-1").eq(
                "goods_id", goodsVo.getId()).gt("stock_count", 0));
        if (goods_Seckill.getStockCount()<1) {
            //判断是否还有库存
            valueOperations.set("isStockEmpty"+goodsVo.getId(),"0");
            return null;
        }
        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goodsVo.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderInfoMapper.insert(orderInfo);

        //生成秒杀订单
        Order order = new Order();
        order.setGoodsId(goodsVo.getId());
        order.setOrderId(orderInfo.getId());
        order.setUserId(user.getId());
        orderMapper.insert(order);
        //将信息存在redis中
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goodsVo.getId(), order);
        return orderInfo;
    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("user_id", user.getId()).eq("goods_id",
                goodsId));
        if (order!=null){
            return order.getOrderId();
        }else if(redisTemplate.hasKey("isStockEmpty:"+goodsId)){
            return -1L;
        }else {
            return 0L;
        }

    }

    /**
     * 获取秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);

        return str;
    }

    /**
     * 校验秒杀地址
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user==null||goodsId<0){
            return false;
        }

        String redisPath=(String)redisTemplate.opsForValue().get("seckillPath:"+user.getId()+":"+goodsId);
        boolean equals = path.equals(redisPath);
        return equals;

    }


}
