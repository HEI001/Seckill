package com.hei001.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hei001.seckill.exception.GlobalException;
import com.hei001.seckill.mapper.OrderInfoMapper;
import com.hei001.seckill.pojo.OrderInfo;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IOrderInfoService;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.OrderDetailVo;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId==null){
            throw  new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        OrderInfo order = orderInfoMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(order.getGoodsId());

        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);

        return detail;
    }
}
