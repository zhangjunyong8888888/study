package com.zjy.study.handler.selfHandlers;

import com.zjy.study.handler.annotation.HandlerType;
import com.zjy.study.handler.bean.OrderDTO;
import com.zjy.study.handler.common.AbstractHandler;
import org.springframework.stereotype.Component;

@Component
@HandlerType("3")
public class PromotionHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "促销";
    }
}
