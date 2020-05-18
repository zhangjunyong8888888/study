package com.zjy.study.modules.lombok;


import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Setter注解
 * 为属性生成set方法
 */
@ToString(
        // 是否显示字段名
        includeFieldNames = false,
        // 忽略的字段值
        exclude = {"field1"},
        // 显示toString 显示的字段值
        of = {"field2"},
        // 为true则不使用get方法输出值
        doNotUseGetters = false
)
@RequiredArgsConstructor
public class SetterTest {

    @Setter
    private String field1;

    @Setter(
            value = AccessLevel.PRIVATE,
            onParam_={@NotNull}
    )
    private String field2;

    public static void main(String[] args) {
        SetterTest setterTest = new SetterTest();
        setterTest.setField1("123132");
        System.out.println(setterTest.toString());
    }

    public  void out(@NonNull String test){
        System.out.println(test);
    }
}
