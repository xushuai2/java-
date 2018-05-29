package com.mina.common;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;  
  
public class MinaServer {  
	private static final int  PORT  = 8080; // 定义监听端口  
    
    private static IoAcceptor acceptor;  
      
    public static void main(String[] args) throws IOException {  
        acceptor = new NioSocketAcceptor();  
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());  
        //acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));// 指定编码过滤器  
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory()));//支持中文  
        acceptor.setHandler(new MinaServerHandler());// 指定业务逻辑处理器  
        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));// 设置端口号  
        acceptor.bind();// 启动监听  
    }
}  
