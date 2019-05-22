package com.zjy.study.netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

public class Server {

  public static void main(String[] args) {
      EventLoopGroup bossGroup = new NioEventLoopGroup(1);
      EventLoopGroup workerGroup = new NioEventLoopGroup();
      try{
          ServerBootstrap bootstrap = new ServerBootstrap();
          bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                  .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
                  .handler(new ServerHandler())
                  .childHandler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      public void initChannel(SocketChannel ch) {
                          ch.pipeline().addLast(new AuthHandler());
                      }
                  });

      }finally{
          bossGroup.shutdownGracefully();
          workerGroup.shutdownGracefully();
      }
  }
}
