package com.zjy.study.modules.handler.common;

import com.zjy.study.modules.handler.bean.OrderDTO;

public abstract class AbstractHandler {

    abstract public String handle(OrderDTO dto);
}
