package com.zjy.study.modules.netty.ch2;

public class ServerBoot {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        new Server(PORT).start();
    }
}
