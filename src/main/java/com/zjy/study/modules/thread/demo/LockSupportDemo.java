package com.zjy.study.modules.thread.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞 ， 可唤醒指定线程， 更灵活的 wait()
 *
 *  unpark  可先于 part 调用
 */

public class LockSupportDemo {

  public static void main(String[] args) {
    Thread t =
        new Thread(
            () -> {
              for (int i = 0; i < 10; i++) {
                System.out.println(i);
                // 当 i 等于 5 时 线程阻塞
                if (i % 2 == 0) {
                  System.out.println("准备 part ...");
                  LockSupport.park();
                }
                try {
                  TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            });

      t.start();

      LockSupport.unpark(t);

//      try {
//          TimeUnit.SECONDS.sleep(8);
//      } catch (InterruptedException e) {
//          e.printStackTrace();
//      }
//
//
//      LockSupport.unpark(t);

  }
}
