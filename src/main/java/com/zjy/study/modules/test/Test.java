package com.zjy.study.modules.test;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<TestDto> testDtoList = new ArrayList<>();
        TestDto testDto = new TestDto("A","B");
        testDtoList.add(testDto);
        TestDto testDto1 = testDto.clone();
        testDto1.setParamA("A-1");
        testDtoList.add(testDto1);
        System.out.println(JSON.toJSONString(testDtoList));
    }
}
