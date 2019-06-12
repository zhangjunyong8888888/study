package com.zjy.study.modules.java8.stream;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class CreateStream {
    public static void main(String[] args) {

        List list = new ArrayList<>();
        list.stream();

        Arrays.stream(new int[]{1,2,3});

        IntStream.of(1,2,3);
        IntStream.range(1,10);
        IntStream.rangeClosed(1,10);

        new Random().ints().limit(10);
        new Random().doubles().limit(10);
        new Random().longs().limit(10);

        Random random = new Random();
        Stream.generate(()-> random.nextInt()).limit(10);

    }
}
