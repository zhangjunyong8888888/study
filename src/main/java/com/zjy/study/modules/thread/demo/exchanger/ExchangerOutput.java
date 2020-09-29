package com.zjy.study.modules.thread.demo.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerOutput {


    private static final Exchanger<String> exchanger = new Exchanger<>();

    /**
     * 26个字母
     */
    private static final String[] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static void main(String[] args) {


        new Thread(()->{
            for (int i = 0; i < 26; i++) {
                try {
                    String exchange = exchanger.exchange(LETTER[i]);
                    try {
                        TimeUnit.MICROSECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        try {
            TimeUnit.MICROSECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(
                () -> {
                    for (int i = 0; i < 26; i++) {
                        try {
                            String exchange = exchanger.exchange(String.valueOf(i));
                            System.out.print(exchange);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .start();
    }
}
