package com.hei001.seckill.controller;


import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.utils.DetailVo;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 优化前 628
     * 优化后2966
     *
     * 跳转商品查询页面
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
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
        //redis中获取页面，如果不为空，则直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("goodsList",goodsService.findGoodsVo());
        model.addAttribute("user",user);
       // return "goods_list";
        //如果为空，手动渲染，存入Redis并返回
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }


    @RequestMapping("/detail/{goodsId}" )
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId){
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
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(seckillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);

    }

    @RequestMapping(value = "/toDetail2/{goodsId}" ,produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId,HttpServletRequest request, HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //Redis中获取页面，如果不为空，直接返回页面
        String html = (String) valueOperations.get("goods_detail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
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

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goods_detail:" + goodsId, html, 60, TimeUnit.SECONDS);
        }
        return html;
    }
}
