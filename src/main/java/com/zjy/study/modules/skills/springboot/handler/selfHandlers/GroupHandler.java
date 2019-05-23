package com.zjy.study.modules.skills.springboot.handler.selfHandlers;

import com.zjy.study.modules.skills.springboot.handler.annotation.HandlerType;
import com.zjy.study.modules.skills.springboot.handler.bean.OrderDTO;
import com.zjy.study.modules.skills.springboot.handler.common.AbstractHandler;
import org.springframework.stereotype.Component;

@Component
@HandlerType("2")
public class GroupHandler extends AbstractHandler {

    @Override
    public String handle(OrderDTO dto) {
        return "团购";
    }
}
