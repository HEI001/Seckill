package com.hei001.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息的发送者
 * @author HEI001
 * @date 2022/3/1 22:50
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀信息
     * @param msg
     */
    public void sendSeckillMessage(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("seckillExchange","seckill.message",msg);
    }
/*    public void send(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }
    public void sendDirect01(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("dircetExchange","queue.red",msg);
    }
    public void sendDirect02(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("dircetExchange","queue.green",msg);
    }
    public void sendTopic01(Object msg){
        log.info("发送消息01接受"+msg);
        rabbitTemplate.convertAndSend("topicExchange","queue.green",msg);
    }
    public void sendTopic02(Object msg){
        log.info("发送消息01接受"+msg);
        rabbitTemplate.convertAndSend("topicExchange","queue.red.green",msg);
    }
    public void sendTopic03(Object msg){
        log.info("发送消息都接受"+msg);
        rabbitTemplate.convertAndSend("topicExchange","rea.queue.red.green",msg);
    }
    public void sendHeaders01(String msg){
        log.info("发送消息（两个接受）:"+msg);
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setHeader("color","red");
        messageProperties.setHeader("speed","fast");
        Message message=new Message(msg.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("headersExchange","",message);
    }
    public void sendHeaders02(String msg){
        log.info("发送消息（01接受）:"+msg);
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setHeader("color","red");
        messageProperties.setHeader("speed","normal");
        Message message=new Message(msg.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("headersExchange","",message);
    }*/
}
