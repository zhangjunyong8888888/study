package com.zjy.study.modules.handler.selfHandlers;

import com.zjy.study.modules.handler.annotation.HandlerType;
import com.zjy.study.modules.handler.bean.OrderDTO;
import com.zjy.study.modules.handler.common.AbstractHandler;
import org.springframework.stereotype.Component;

@Component
@HandlerType("2")
public class GroupHandler extends AbstractHandler {

    @Override
    public String handle(OrderDTO dto) {
        return "团购";
    }
}
