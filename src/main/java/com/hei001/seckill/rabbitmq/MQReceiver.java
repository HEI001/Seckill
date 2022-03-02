package com.hei001.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 消息的消费者
 * @author HEI001
 * @date 2022/3/1 22:52
 */
@Service
@Slf4j
public class MQReceiver {

    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接受消息",msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接受消息",msg);
    }
    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接受消息"+msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg){
        log.info("queue_direct01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg){
        log.info("queue_direct02接受消息",msg);
    }

    @RabbitListener(queues = "queue_topic01")
    public void receive05(Object msg){
        log.info("topict01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_topic02")
    public void receive06(Object msg){
        log.info("topict02接受消息"+msg);

    }


    @RabbitListener(queues = "queue_headers01")
    public void receive05(Message msg){
        log.info("queue_headers01接受消息对象"+msg);
        log.info("queue_headers01接受消息"+new String(msg.getBody()));

    }
    @RabbitListener(queues = "queue_headers02")
    public void receive06(Message msg){
        log.info("queue_headers02接受消息对象"+msg);
        log.info("queue_headers02接受消息"+new String(msg.getBody()));


    }
}
