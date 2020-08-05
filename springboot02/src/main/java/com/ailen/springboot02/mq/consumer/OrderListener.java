package com.ailen.springboot02.mq.consumer;

import com.ailen.springboot02.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareBatchMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 秒杀-消费者监听器
 * 消费者消费订单消息，做业务处理。
 * 看一下监听器（消费者）OrderListener：
 */
@Component
public class OrderListener implements ChannelAwareBatchMessageListener {
    private static final Logger logger = LogManager.getLogger(OrderListener.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try{
            //获取交付tag
            long tag = message.getMessageProperties().getDeliveryTag();
            String str = new String(message.getBody(),"utf-8");
            logger.info("order消费者接收到的消息：{}",str);
            JSONObject obj = JSONObject.parseObject(str);
            //下单，操作数据库
            orderService.order(obj.getString("userId"),obj.getString("goodsId"));
            //确认消费
            channel.basicAck(tag,true);
        }catch(Exception e){
            logger.error("消息监听确认机制发生异常：",e.fillInStackTrace());
        }
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }

    @Override
    public void onMessageBatch(List<Message> messages) {

    }

    @Override
    public void onMessageBatch(List<Message> list, Channel channel) {

    }
}
