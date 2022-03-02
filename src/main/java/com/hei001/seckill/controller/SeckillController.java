package com.hei001.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hei001.seckill.config.AccessLimit;
import com.hei001.seckill.exception.GlobalException;
import com.hei001.seckill.pojo.Order;
import com.hei001.seckill.pojo.OrderInfo;
import com.hei001.seckill.pojo.SeckillMessage;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.rabbitmq.MQSender;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IOrderInfoService;
import com.hei001.seckill.service.IOrderService;
import com.hei001.seckill.utils.JsonUtil;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author HEI001
 * @date 2022/3/1 9:11
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderInfoService iOrderInfoService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;
    @Autowired
    private RedisScript<Long> redisScript;
    private Map<Long,Boolean> EmptyStocking=new HashMap<>();

    /**
     * 秒杀
     * 优化后 QPS ：2493
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/{path}/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, User user, Long goodsId){
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        boolean check=orderService.checkPath(user,goodsId,path);
        if (!check){
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
        }


        //判断是否重复抢购
        // OrderInfo one = iOrderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        Order order1 = (Order) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(order1!=null){
            //model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        /**
         * 通过内存标记减少Redis的访问
         */
        if (EmptyStocking.get(goodsId)){
            return RespBean.error(RespBeanEnum.Empty_STOCK);
        }

        //预减库存操作
       // Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
        //System.out.println(stock);
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock<0){
            EmptyStocking.put(goodsId,true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.Empty_STOCK);
        }
        //下单
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);


        /*GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.Empty_STOCK.getMessage());
            return RespBean.error(RespBeanEnum.Empty_STOCK);
        }

        //判断是否重复抢购
       // OrderInfo one = iOrderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        Order order1 = (Order) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsVo.getId());
        if(order1!=null){
            //model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        OrderInfo order = orderService.seckill(user, goodsVo);
        return RespBean.success(order);*/
        //return null;
    }

    /**
     * 获取秒杀地址
     * @param user
     * @param goodsId
     * @param captcha
     * @param request
     * @return
     */
    @AccessLimit(second=5,maxCount=5,needLogin=true)
    @RequestMapping(value = "/path",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getPath(User user, Long goodsId, String captcha, HttpServletRequest request){
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        boolean check=orderService.checkCaptcha(user,goodsId,captcha);
        if (!check){
            return RespBean.error(RespBeanEnum.ERROR_CAPTCHA);
        }
        String str=orderService.createPath(user,goodsId);
        return RespBean.success(str);
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


    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     * orderId 成功 -1 失败  0 排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId=orderService.getResult(user,goodsId);
        return RespBean.success(orderId);
    }

    /**
     * 验证码
     * @param user
     * @param goodsId
     * @param response
     */
    @RequestMapping("/captcha")
    public void verifyCode(User user, Long goodsId, HttpServletResponse response){
        if(user==null || goodsId<0)
            throw new GlobalException(RespBeanEnum.REQUEST_ILLEGAL);
        response.setContentType("image/jpg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32, 3);
        redisTemplate.opsForValue().set("captcha:"+user.getId()+":"+goodsId,captcha.text(),300, TimeUnit.SECONDS);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.error("验证码生成失败"+e.getMessage());
        }
    }

    /**
     * 系统初始化，将商品加载到库存里
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)){
            return ;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(),goodsVo.getStockCount());
            EmptyStocking.put(goodsVo.getId(),false);
        });
    }
}
