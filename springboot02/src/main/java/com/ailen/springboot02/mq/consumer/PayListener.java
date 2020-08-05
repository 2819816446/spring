package com.ailen.springboot02.mq.consumer;

import com.ailen.springboot02.service.PayService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayListener implements ChannelAwareMessageListener {

    private static final Logger logger = LogManager.getLogger(PayListener.class);

    @Autowired
    private PayService payService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Long tag = message.getMessageProperties().getDeliveryTag();
        try {
            String str = new String(message.getBody(), "utf-8");
            logger.info("支付消费者接收到的消息：{}",str);
            JSONObject json = JSON.parseObject(str);
            String orderId = json.getString("id");
            payService.confirmPay(orderId);
            channel.basicAck(tag, true);
        }catch(Exception e){
            logger.info("支付消息消费出错：{}",e.getMessage());
            logger.info("出错的tag:{}",tag);
        }
    }
}
