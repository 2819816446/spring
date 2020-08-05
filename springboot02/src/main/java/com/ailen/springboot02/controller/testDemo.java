package com.ailen.springboot02.controller;

import com.ailen.springboot02.Springboot02Application;
import com.ailen.springboot02.util.RedisTool;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Springboot02Application.class})
public class testDemo {

    @Resource
    private RedisTool redisTool;

    @Test
    public void testGetExpire() {
        redisTool.set("aaaKey", "aaaValue");
        redisTool.expire("aaaKey", 100);
        // 设置了缓存就会及时的生效，所以缓存时间小于最初设置的时间
        Assert.assertTrue(redisTool.getExpire("aaaKey") < 100L);

    }




}
