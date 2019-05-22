package com.zjy.study.handler.common;

import com.zjy.study.handler.bean.OrderDTO;

public abstract class AbstractHandler {

    abstract public String handle(OrderDTO dto);
}
