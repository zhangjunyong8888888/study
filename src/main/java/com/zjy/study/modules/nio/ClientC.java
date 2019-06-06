package com.zjy.study.modules.nio;

import java.io.IOException;

public class ClientC {
  public static void main(String[] args) throws IOException {
    new NioClient().start("C");
  }
}
