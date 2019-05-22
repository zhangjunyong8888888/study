package com.zjy.study.handler.service.impl;

import com.zjy.study.handler.bean.OrderDTO;
import com.zjy.study.handler.common.AbstractHandler;
import com.zjy.study.handler.common.HandlerContext;
import com.zjy.study.handler.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private HandlerContext handlerContext;

    @Override
    public String handle(OrderDTO dto) {
        AbstractHandler handler =handlerContext.getInstance(dto.getType());
        return handler.handle(dto);
    }
}
