package com.ailen.springboot02.service;

import com.ailen.springboot02.request.OrderRequest;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SeckillService {

    private static final Logger logger = LogManager.getLogger(SeckillService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    /**
     * 生产消息
     * @param order
     */
    public void seckill(OrderRequest order){
        //设置交换机
        rabbitTemplate.setExchange(env.getProperty("order.mq.exchange.name"));
        //设置routingkey
        rabbitTemplate.setRoutingKey(env.getProperty("order.mq.routing.key"));
        //创建消息体
        Message msg = MessageBuilder.withBody(JSON.toJSONString(order).getBytes()).build();
        //发送消息
        rabbitTemplate.convertAndSend(msg);
    }
}
