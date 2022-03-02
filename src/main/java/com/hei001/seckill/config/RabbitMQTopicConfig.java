/*
package com.hei001.seckill.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * Topic模式
 * @author HEI001
 * @date 2022/3/1 23:46
 *//*

@Configuration
public class RabbitMQTopicConfig {

    private static final String QUEUE1 = "queue_topic01";
    private static final String QUEUE2 = "queue_topic02";
    public static final String EXCHANGE= "topicExchange";
    public static final String ROUTINGKEY01= "#.queue.#";
    public static final String ROUTINGKEY02= "*.queue.#";

    @Bean
    public Queue queueTopic01(){
        return new Queue(QUEUE1);
    }
    @Bean
    public Queue queueTopic02(){
        return new Queue(QUEUE2);
    }
    @Bean
    public TopicExchange TopicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingTopic01(){
        return BindingBuilder.bind(queueTopic01()).to(TopicExchange()).with(ROUTINGKEY01);
    }
    //绑定交换机和队列
    @Bean
    public Binding bindingTopic02(){
        return BindingBuilder.bind(queueTopic02()).to(TopicExchange()).with(ROUTINGKEY02);

    }
}
*/
