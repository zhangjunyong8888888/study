package com.zjy.study.modules.handler.selfHandlers;

import com.zjy.study.modules.handler.annotation.HandlerType;
import com.zjy.study.modules.handler.bean.OrderDTO;
import com.zjy.study.modules.handler.common.AbstractHandler;
import org.springframework.stereotype.Component;

@Component
@HandlerType("1")
public class NormalHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "普通";
    }
}