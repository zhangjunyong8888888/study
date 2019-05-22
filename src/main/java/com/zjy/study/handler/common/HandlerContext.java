package com.zjy.study.handler.common;

import com.zjy.study.handler.utils.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

public class HandlerContext {

    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractHandler) BeanUtil.getBean(clazz);
    }

}
