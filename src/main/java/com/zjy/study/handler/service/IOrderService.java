package com.zjy.study.handler.service;

import com.zjy.study.handler.bean.OrderDTO;

public interface IOrderService {

    /**
     * 根据订单的不同类型作出不同的处理
     *
     * @param dto 订单实体
     * @return 为了简单，返回字符串
     */
    String handle(OrderDTO dto);
}
