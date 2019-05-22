package com.zjy.study.modules.netty.ch2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler {

    public static final int MAX_DATA_LEN = 1024;
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void start(){
        System.out.println("新客户端连入");
        new Thread(new Runnable() {
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart(){
        try {
            InputStream inputStream = socket.getInputStream();
            //socket.setSoTimeout(1000);
            while (true){
                byte[] data = new byte[MAX_DATA_LEN];
                int len;
                while ((len = inputStream.read(data))!=-1){
                    String message = new String(data,0,len);
                    System.out.println("客户端传来新消息：" + message);
                    socket.getOutputStream().write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
