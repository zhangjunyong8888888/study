package com.zjy.study.netty.ch2;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static final String HOSt = "127.0.0.1";
    public static final int  PORT = 8000;
    public static final int  SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOSt,PORT);
    new Thread(
            new Runnable() {
              public void run() {
                System.out.println("客户端启动成功！");
                while (true) {
                  String message = "hello word!";
                  try {
                      System.out.println("客户端发送数据：" + message);
                      socket.getOutputStream().write(message.getBytes());
                      sleep();
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }
              }
            })
        .start();
    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
