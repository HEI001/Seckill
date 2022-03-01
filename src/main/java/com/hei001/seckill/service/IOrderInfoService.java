package com.hei001.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hei001.seckill.pojo.OrderInfo;
import com.hei001.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);
}
