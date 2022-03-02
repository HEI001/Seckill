/*
package com.hei001.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

*/
/**
 * headers 模式
 *
 * @author HEI001
 * @date 2022/3/2 10:33
 *//*

@Configuration
public class RabbitMQHeadersConfig {
    private static final String QUEUE1 = "queue_headers01";
    private static final String QUEUE2 = "queue_headers02";
    public static final String EXCHANGE= "headersExchange";

    @Bean
    public Queue queueHeaders01(){
        return new Queue(QUEUE1);
    }
    @Bean
    public Queue queueHeaders02(){
        return new Queue(QUEUE2);
    }
    @Bean
    public HeadersExchange headerExchange(){
        return new HeadersExchange(EXCHANGE);
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingHeader01(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("color","red");
        map.put("speed","low");
        return BindingBuilder.bind(queueHeaders01()).to(headerExchange()).whereAny(map).match();
    }
    //绑定交换机和队列
    @Bean
    public Binding bindingHeaders02(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("color","red");
        map.put("speed","fast");
        return BindingBuilder.bind(queueHeaders02()).to(headerExchange()).whereAll(map).match();

    }
}
*/
