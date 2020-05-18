package com.zjy.study.modules.lombok;

/**
 * @ClassName : GetterTest
 * @Description : @Getter
 * @Author : zjy
 * @Date: 2020-05-17 14:22
 */

import lombok.AccessLevel;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @Getter注解
 * 为属性生成get方法
 */
public class GetterTest {
    /**
     * 增加懒加载，只有在get的时候才会赋值 针对于final属性
     */
    @Getter(
            lazy = true
    )
    private final String field1 = "zjy";

    @Getter(
            // 设置为 private 默认 PUBLIC
            value = AccessLevel.PRIVATE,
            // since 1.8 在生成的get方法上增加注解
            onMethod_={@NotNull}
    )
    private String field2;

    public String getField1() {
        System.out.println("1231231");
        return field2;
    }
}
