package com.zjy.study.modules.handler.service.impl;

import com.zjy.study.modules.handler.bean.OrderDTO;
import com.zjy.study.modules.handler.common.AbstractHandler;
import com.zjy.study.modules.handler.common.HandlerContext;
import com.zjy.study.modules.handler.service.IOrderService;
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
