package com.mengzhaoxu.eblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yixin
 * @date 2020/6/30 4:54 下午
 * @description
 */
@Configuration
public class MqConfig {

    /**
     * queue
     */
    public static final String POST_QUEUE = "post.queue";


    /**
     * exchange
     */
    public static final String POST_EXCHANGE = "post.exchange";


    /**
     * routingkey
     */
    public static final String POST_ROUTINGKEY = "post.exchangekey";

    @Bean
    public Queue post_queue(){
        return new Queue(POST_QUEUE);
    }

    /**
     * Direct模式 交换机Exchange
     * @param
     */
    @Bean
    public Queue queue(){
        return new Queue(POST_QUEUE);
    }


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(POST_EXCHANGE);
    }

    @Bean
    public Binding post_binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(POST_ROUTINGKEY);
    }

}
