package com.zjy.study.test;

import java.time.LocalDateTime;

public class Test2 extends Test1{

    public Test2(String value) {
        super(value);
    }

  public static void main(String[] args) {
    System.out.println(LocalDateTime.now().toString());
  }

}
