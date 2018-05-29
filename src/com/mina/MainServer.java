package com.mina;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;  
public class MainServer {
	 private static final int PORT = 8080;  
	  
	 public static void main(String[] args) throws Exception {  
		 //服务端的实例  
		//创建一个非阻塞的server端Socket ，用NIO
		 NioSocketAcceptor accept=new NioSocketAcceptor();  
		 //添加filter，codec为序列化方式。这里为对象序列化方式，即表示传递的是对象。  
		 //创建接收数据的过滤器       请注意，过滤器可以使用mina提供的过滤器，也可以自己根据需要编写过滤器。
		 accept.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));  
		 // 设置读取数据的缓冲区大小为1M  
		 accept.getSessionConfig().setReadBufferSize(1024*1024);  
         // 读写通道10秒内无操作进入空闲状态  
		 accept.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);  
		 //添加filter，日志信息  
		 accept.getFilterChain().addLast("logging",new LoggingFilter());  
		 //设置服务端的handler  
		 accept.setHandler(new FileUploadHandler());  
		 //绑定ip  
		 accept.bind(new InetSocketAddress(PORT));  
		 System.out.println("upload  server started.");   
	 }  
}
