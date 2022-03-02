/*
package com.hei001.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * Dircet模式
 * @author HEI001
 * @date 2022/3/1 23:16
 *//*

@Configuration
public class RabbitMQDirectConfig {
    private static final String QUEUE1 = "queue_direct01";
    private static final String QUEUE2 = "queue_direct02";
    public static final String EXCHANGE= "dircetExchange";
    public static final String ROUTINGKEY01= "queue.red";
    public static final String ROUTINGKEY02= "queue.green";

    @Bean
    public Queue queueDirect01(){
        return new Queue(QUEUE1);
    }
    @Bean
    public Queue queueDirect02(){
        return new Queue(QUEUE2);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE);
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingDirect01(){
        return BindingBuilder.bind(queueDirect01()).to(directExchange()).with(ROUTINGKEY01);
    }
    //绑定交换机和队列
    @Bean
    public Binding bindingDirect02(){
        return BindingBuilder.bind(queueDirect02()).to(directExchange()).with(ROUTINGKEY02);

    }
}
*/
