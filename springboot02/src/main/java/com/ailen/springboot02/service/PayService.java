package com.ailen.springboot02.service;

import com.ailen.springboot02.mapper.SeckillMapper;
import com.ailen.springboot02.pojo.OrderRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class PayService {

    private static final Logger logger = LogManager.getLogger(PayService.class);

    @Resource
    private SeckillMapper seckillMapper;

    /**
     * 确认是否支付
     * @param orderId
     */
    public void confirmPay(String orderId){
        OrderRecord orderRecord = seckillMapper.selectNoPayOrderById(orderId);
        //根据订单号校验该用户是否已支付
        if(checkPay(orderId)){
            //已支付
            orderRecord.setPayStatus(1);
            seckillMapper.updatePayStatus(orderRecord);
            logger.info("用户{}已支付",orderId);
        }else{
            //未支付
            orderRecord.setPayStatus(0);
            seckillMapper.updatePayStatus(orderRecord);
            logger.info("用户{}未支付",orderId);
        }
    }

    /**
     * 模拟判断订单支付成功或失败，成功失败随机
     * @param orderId
     * @return
     */
    public boolean checkPay(String orderId){
        Random random = new Random();
        int res = random.nextInt(2);
        return res==0?false:true;
    }


}
