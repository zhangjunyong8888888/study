package com.zjy.study.modules.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto implements Cloneable{

    private String paramA;
    private String paramB;

    @Override
    protected TestDto clone()  {
        TestDto clone = null;
        try {
            clone = (TestDto)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
