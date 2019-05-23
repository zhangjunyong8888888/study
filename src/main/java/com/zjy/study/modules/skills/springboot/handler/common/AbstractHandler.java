package com.zjy.study.modules.skills.springboot.handler.common;

import com.zjy.study.modules.skills.springboot.handler.bean.OrderDTO;

public abstract class AbstractHandler {

    abstract public String handle(OrderDTO dto);
}
