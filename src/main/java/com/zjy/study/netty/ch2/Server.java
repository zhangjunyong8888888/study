package com.zjy.study.netty.ch2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，端口:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("服务端启动失败，端口:" + port);
        }
    }
    
    public void start(){
        doStart();

        System.out.println("无法执行到我?");
    }

    private void doStart() {
        try {
            Socket client = serverSocket.accept();
            new ClientHandler(client).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
