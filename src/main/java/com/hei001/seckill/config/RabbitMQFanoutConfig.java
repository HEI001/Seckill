/*
package com.hei001.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



*/
/**
 * @author HEI001
 * @date 2022/3/1 22:48
 *//*

@Configuration
public class RabbitMQFanoutConfig {

    //Fanout模式
    private static final String QUEUE1 = "queue_fanout01";
    private static final String QUEUE2 = "queue_fanout02";
    public static final String EXCHANGE= "fanoutExchange";


    @Bean
    public Queue queue(){
        return new Queue("queue",true);
    }

    @Bean
    public Queue queue01(){
        return new Queue(QUEUE1);
    }
    @Bean
    public Queue queue02(){
        return new Queue(QUEUE2);
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }

    //绑定交换机和队列
    @Bean
    public Binding binding01(){
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }
    //绑定交换机和队列
    @Bean
    public Binding binding02(){
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }
}
*/
