package com.zjy.study.modules.guava;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName : ImmuatableTest
 * @Description : 不可变集合
 * @Author : zjy
 * @Date: 2020-05-14 22:35
 */
public class ImmutableTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        //JDK 自带
        List<Integer> newList = Collections.unmodifiableList(list);


        //Guava 与 JDK 类似
        ImmutableList<Integer> unList = ImmutableList.copyOf(list);

        ImmutableList<Integer> integers = ImmutableList.of(1, 2, 3, 4, 5);

        ImmutableList<Object> build = ImmutableList.builder().add(1).add(2).add(3).build();
    }
}
