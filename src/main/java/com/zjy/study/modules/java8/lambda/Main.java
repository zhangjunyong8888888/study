package com.zjy.study.modules.java8.lambda;


import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) {
        // 并行流求最小值
        System.out.println(IntStream.of(new int[]{1,5,3,-4,9,8,7,366}).parallel().min().getAsInt());
    }
}
