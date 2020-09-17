
package com.zjy.study.modules.thread.demo;


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量 - 同时允许几个线程一起执行，
 */
public class SemaphoreDemo {

    /**
     * 同时允许 1 个线程允许
     */
    private static final Semaphore SEMAPHORE = new Semaphore(2);

      public static void main(String[] args) {

          new Thread(()->{
             try{
                 // 只有成功才可以往下走,不成功阻塞等待
                 SEMAPHORE.acquire();
                 System.out.println("run 1 start");
                 TimeUnit.SECONDS.sleep(3);
                 System.out.println("run 1 end");
             }catch (Exception e){
                 e.printStackTrace();
             }finally{
                 SEMAPHORE.release();
             }
          }).start();

    new Thread(
            () -> {
              try {
                // 只有成功才可以往下走
                SEMAPHORE.acquire();
                System.out.println("run 2 start");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("run 2 end");
              } catch (Exception e) {
                e.printStackTrace();
              } finally {
                SEMAPHORE.release();
              }
            })
        .start();
      }
}
