package com.zjy.study.modules.java8.lambda;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class VarDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        Consumer<String> consumer = s -> System.out.println(s + list.size());
        consumer.accept("1-");
        list.add("2");
        consumer.accept("2-");

    }
}
