package com.zjy.study.modules.thread.demo;

import java.util.concurrent.Exchanger;

/**
 * 进行数据交换
 *
 * 线程阻塞进行数据交换,会阻塞等待， 只能有两个线程
 *
 * 执行一次就失效了
 */
public class ExchangerDemo {

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

  public static void main(String[] args) {

    new Thread(
            () -> {
              String KEY = "K1";
              try {
                String exchange = EXCHANGER.exchange(KEY);
                  System.out.println(String.format("%s 交换到数据 %s",KEY,exchange));
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();

      new Thread(
              () -> {
                  String KEY = "K2";
                  try {
                      String exchange = EXCHANGER.exchange(KEY);
                      System.out.println(String.format("%s 交换到数据 %s",KEY,exchange));
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              })
              .start();

//      new Thread(
//              () -> {
//                  String KEY = "K3";
//                  try {
//                      String exchange = EXCHANGER.exchange(KEY);
//                      System.out.println(String.format("%s 交换到数据 %s",KEY,exchange));
//                  } catch (InterruptedException e) {
//                      e.printStackTrace();
//                  }
//              })
//              .start();
  }
}
