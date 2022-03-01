package com.hei001.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hei001.seckill.pojo.Order;
import com.hei001.seckill.pojo.OrderInfo;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IOrderInfoService;
import com.hei001.seckill.service.IOrderService;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author HEI001
 * @date 2022/3/1 9:11
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderInfoService iOrderInfoService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(Model model, User user, Long goodsId){
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.Empty_STOCK.getMessage());
            return RespBean.error(RespBeanEnum.Empty_STOCK);
        }

        //判断是否重复抢购
        OrderInfo one = iOrderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(one!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        OrderInfo order = orderService.seckill(user, goodsVo);
        return RespBean.success(order);
    }
    @RequestMapping(value = "/doSeckill2")
    public String doSeckill2(Model model, User user,Long goodsId){
        if (user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.Empty_STOCK.getMessage());
            return "seckill_fail";
        }

        //判断是否重复抢购
        OrderInfo one = iOrderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(one!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckill_fail";
        }
        OrderInfo order = orderService.seckill(user, goodsVo);
        model.addAttribute("orderInfo",order);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }
}
