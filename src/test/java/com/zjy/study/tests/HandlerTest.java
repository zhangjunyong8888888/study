package com.zjy.study.tests;

import com.zjy.study.StudyApplicationTests;
import com.zjy.study.modules.handler.bean.OrderDTO;
import com.zjy.study.modules.handler.service.IOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HandlerTest extends StudyApplicationTests {

    @Autowired
    private IOrderService orderService;

    @Test
    public void TestHandler(){
        OrderDTO order = new OrderDTO();
        order.setCode("1");
        order.setPrice(new BigDecimal(100));
        order.setType("1");
        System.out.println(orderService.handle(order));
    }
}
