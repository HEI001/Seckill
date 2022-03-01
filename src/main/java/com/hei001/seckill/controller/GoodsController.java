package com.hei001.seckill.controller;

import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author HEI001
 * @date 2022/2/28 14:03
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    IGoodsService goodsService;

    /**
     * 跳转商品查询页面
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String toList( Model model,User user){
/*        //判断是否登录
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        //不通过session，通过redis
        //User user = (User) session.getAttribute(ticket);
        User user = userService.getUserByCookie(response, ticket);
        if(null==user){
            return "login";
        }*/
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        model.addAttribute("user",user);
        return "goods_list";
    }


    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId){
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(goodsId);
        //开始时间
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        //当前时间
        Date nowDate = new Date();
        //秒杀状态
        int seckillStatus=0;
        //秒杀倒计时
        int remainSeconds=0;
        if (nowDate.before(startDate)){
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime())) / 1000);
        }else if(nowDate.after(endDate)){
            //秒杀结束
            seckillStatus=2;
            remainSeconds=-1;
        }else {
            seckillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("seckillStatus",seckillStatus);
        System.out.println(seckillStatus);
        model.addAttribute("goods",goodsVo);
        return "goods_detail";
    }
}
