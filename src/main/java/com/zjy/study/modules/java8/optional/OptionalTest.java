package com.zjy.study.modules.java8.optional;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName : OptionalTest
 * @Description :
 * @Author : zjy
 * @Date: 2020-05-14 22:22
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional.ofNullable(null).orElseThrow(RuntimeException::new);
    }

}
