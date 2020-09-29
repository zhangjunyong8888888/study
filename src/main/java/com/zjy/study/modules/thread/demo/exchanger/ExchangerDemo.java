package com.zjy.study.modules.thread.demo.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 进行数据交换
 *
 * 线程阻塞进行数据交换,会阻塞等待， 只能有两个线程
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

    new Thread(
            () -> {
              String KEY = "K3";
              try {
                String exchange = EXCHANGER.exchange(KEY, 5, TimeUnit.SECONDS);
                System.out.println(String.format("%s 交换到数据 %s", KEY, exchange));
              } catch (InterruptedException e) {
                e.printStackTrace();
              } catch (TimeoutException e) {
                System.out.println(KEY + " 等待交换数据超时...");
              }
            })
        .start();

//      new Thread(
//              () -> {
//                  String KEY = "K4";
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
