package com.zjy.study.modules.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

  private void start() throws IOException {
    /** 1.创建selector */
    Selector selector = Selector.open();
    /** 2.通过ServerSocketChannel创建Channel通道 */
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    /** 3.为channel通道绑定监听端口 */
    serverSocketChannel.bind(new InetSocketAddress(8888));
    /** 4.设置channel 为阻塞模式 */
    serverSocketChannel.configureBlocking(false);
    /** 5.将channel注册到selector上，监听连接事件 */
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println("服务器启动成功！");
    /** 6.无限循环等待新连接接入 */
    for (; ; ) {
      int readyChannels = selector.select();
      if (readyChannels == 0) continue;
      /** 获取可用的channel集合 */
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = selectionKeys.iterator();
      while (iterator.hasNext()) {
        /** 获取selectionKey实例 */
        SelectionKey selectionKey = iterator.next();
        iterator.remove();
        /** 根据就绪状态调用不同的事件 */
        if (selectionKey.isAcceptable()) {
          acceptHandler(serverSocketChannel, selector);
        }
        if (selectionKey.isReadable()) {
          readHandler(selectionKey,selector);
        }
      }
    }
  }

  private void readHandler(SelectionKey selectionKey, Selector selector) throws IOException {
    /** 要从 selectionKey 中获取到已经就绪的channel */
    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
    /** 创建buffer */
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    /** 循环读取客户端请求信息 */
    String request = "";
    while (socketChannel.read(byteBuffer) > 0) {
      /** 切换buffer为读模式 */
      byteBuffer.flip();
      /** 读取buffer中的内容 */
      request += Charset.forName("UTF-8").decode(byteBuffer);
    }
    /** 将channel再次注册到selector上，监听他的可读事件 */
    socketChannel.register(selector, SelectionKey.OP_READ);
    /** 将客户端发送的请求信息 广播给其他客户端 */
    if (request.length() > 0) {
      // 广播给其他客户端
      broadCast(selector, socketChannel, request);
    }
  }

  private void broadCast(Selector selector, SocketChannel socketChannel, String request) {
    /** 获取到所有已接入的客户端channel */
    Set<SelectionKey> selectionKeySet = selector.keys();

    /** 循环向所有channel广播信息 */
    selectionKeySet.forEach(
        selectionKey -> {
          Channel targetChannel = selectionKey.channel();

          // 剔除发消息的客户端
          if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
            try {
              // 将信息发送到targetChannel客户端
              ((SocketChannel) targetChannel).write(Charset.forName("UTF-8").encode(request));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private void acceptHandler(ServerSocketChannel serverSocketChannel, Selector selector)
      throws IOException {
    /** 如果是接入事件，创建SocketChannel */
    SocketChannel socketChannel = serverSocketChannel.accept();
    /** 设置socketChannel为非阻塞模式 */
    socketChannel.configureBlocking(false);
    /** 将socketChannel注册到selector上 */
    socketChannel.register(selector, SelectionKey.OP_READ);
    /** 活肤客户端提示信息 */
    socketChannel.write(Charset.forName("UTF-8").encode("欢迎登陆！"));
  }

  public static void main(String[] args) throws IOException {
        new NioServer().start();
  }
}
