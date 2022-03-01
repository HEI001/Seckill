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
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.OrderDetailVo;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        //秒杀商品表的库存
        GoodsSeckill goods_Seckill = goodsSeckillService.getOne(new QueryWrapper<GoodsSeckill>().eq("goods_id",
                goodsVo.getId()));
        goods_Seckill.setStockCount(goods_Seckill.getStockCount() - 1);
        /*boolean result = goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>().set("stock_count",
                goods_Seckill.getStockCount()).eq("id", goods_Seckill.getId()).gt("stock_count", 0));*/
        boolean result = goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>().setSql("stock_count = stock_count-1").eq(
                "goods_id", goodsVo.getId()).gt("stock_count", 0));
        if (!result) {
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


}
