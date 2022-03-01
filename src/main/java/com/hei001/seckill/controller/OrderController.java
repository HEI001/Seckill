package com.hei001.seckill.controller;


import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IOrderInfoService;
import com.hei001.seckill.service.IOrderService;
import com.hei001.seckill.vo.OrderDetailVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private IOrderInfoService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detaul(User user, Long orderId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);

        return RespBean.success(detail);
    }
}
