package com.ailen.springboot02.controller;

import com.ailen.springboot02.enums.StatusCode;
import com.ailen.springboot02.request.OrderRequest;
import com.ailen.springboot02.service.CheckService;
import com.ailen.springboot02.service.SeckillService;
import com.ailen.springboot02.util.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀controller
 */
@RequestMapping("seckill")
@RestController
public class SeckillController {

    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private CheckService checkService;

    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀入口
     *
     * @param order 下单参数
     * @return
     */
    @RequestMapping(value = "go", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BaseResponse seckillGoods(OrderRequest order) {

        BaseResponse response = new BaseResponse(StatusCode.Success);

        try {
            //判断该用户是否首次秒杀该商品
            if (checkService.checkSeckillUser(order)) {
                seckillService.seckill(order);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            response = new BaseResponse(StatusCode.Fail);
        }

        return response;

    }

}
