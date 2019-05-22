package com.zjy.study.handler.selfHandlers;

import com.zjy.study.handler.annotation.HandlerType;
import com.zjy.study.handler.bean.OrderDTO;
import com.zjy.study.handler.common.AbstractHandler;
import org.springframework.stereotype.Component;

@Component
@HandlerType("1")
public class NormalHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "普通";
    }
}
