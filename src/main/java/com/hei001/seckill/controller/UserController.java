package com.hei001.seckill.controller;


import com.hei001.seckill.pojo.User;
import com.hei001.seckill.rabbitmq.MQSender;
import com.hei001.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Controller
@RequestMapping("/user")
public class UserController {
/*    @Autowired
    private MQSender mqSender;
    *//**
     * 用户信息
     * 专门用来测试
     * @param user
     * @return
     *//*
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

    *//**
     * 测试发送消息
     *//*
    @RequestMapping("/mq")
    @ResponseBody
    public  void mq(){
        mqSender.send("Hello");
    }

    *//**
     * 测试Fanout模式
     *//*
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public  void mq01(){
        mqSender.send("HelloFanout");
    }

    *//**
     * 测试Direct模式
     *//*
    @RequestMapping("/mq/direct01")
    @ResponseBody
    public  void direct01(){
        mqSender.sendDirect01("HelloRED");
    }

    *//**
     * 测试Direct模式
     *//*
    @RequestMapping("/mq/direct02")
    @ResponseBody
    public  void direct02(){
        mqSender.sendDirect02("HelloGREEN");
    }


    *//**
     * 测试Topic模式
     *//*
    @RequestMapping("/mq/Topic01")
    @ResponseBody
    public  void topic01(){
        mqSender.sendTopic01("hello01");
    }

    *//**
     * 测试Topic模式
     *//*
    @RequestMapping("/mq/Topic02")
    @ResponseBody
    public  void topic02(){
        mqSender.sendTopic02("hello02");
    }
    *//**
     * 测试Topic模式
     *//*
    @RequestMapping("/mq/Topic03")
    @ResponseBody
    public  void topic03(){
        mqSender.sendTopic03("hello03");
    }


    *//**
     * 测试header模式
     *//*
    @RequestMapping("/mq/header01")
    @ResponseBody
    public  void header01(){
        mqSender.sendHeaders01("hello0102");
    }
    *//**
     * 测试header模式
     *//*
    @RequestMapping("/mq/header02")
    @ResponseBody
    public  void header02(){
        mqSender.sendHeaders02("hello0111111");
    }*/
}
