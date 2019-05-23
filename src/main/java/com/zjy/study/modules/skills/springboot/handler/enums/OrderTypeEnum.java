package com.zjy.study.modules.skills.springboot.handler.enums;

public enum  OrderTypeEnum {

    A("1","普通订单"),
    B("2","团购订单"),
    C("3","促销订单");


    public String code;
    public String msg;

    OrderTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public  String getCode() {
        return code;
    }

    public  String getMsg() {
        return msg;
    }
}
