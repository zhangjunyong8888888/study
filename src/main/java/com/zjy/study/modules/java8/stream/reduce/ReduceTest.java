package com.zjy.study.modules.java8.stream.reduce;

import com.google.common.collect.Lists;

import java.math.BigDecimal;

/**
 * @ClassName : ReduceTest
 * @Description :
 * @Author : zjy
 * @Date: 2020-05-12 23:00
 */
public class ReduceTest {

    public static void main(String[] args) {
        BigDecimal reduce = Lists.newArrayList(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, new BigDecimal(10))
                .stream()
                .parallel()
                .reduce(new BigDecimal(1000), BigDecimal::add, BigDecimal::add);
        System.out.println(reduce);
    }
}
