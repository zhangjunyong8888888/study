package com.zjy.study.modules.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioClientHandler implements Runnable {
  private Selector selector;

  public NioClientHandler(Selector selector) {
    this.selector = selector;
  }

  @Override
  public void run() {
    try {
      for (; ; ) {
        int readyChannels = selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
          SelectionKey selectionKey = iterator.next();
          iterator.remove();
          if (selectionKey.isReadable()) {
            readHandler(selectionKey, selector);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readHandler(SelectionKey selectionKey, Selector selector) throws IOException {
    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
    /** 创建buffer */
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    /** 循环读取服务器端响应信息 */
    String response = "";
    while (socketChannel.read(byteBuffer) > 0) {
      /** 切换buffer为读模式 */
      byteBuffer.flip();

      /** 读取buffer中的内容 */
      response += Charset.forName("UTF-8").decode(byteBuffer);
      /** 将channel再次注册到selector上，监听他的可读事件 */
      socketChannel.register(selector, SelectionKey.OP_READ);
      /** 将服务器端响应信息打印到本地 */
      if (response.length() > 0) {
        System.out.println(response);
      }
    }
  }
}
