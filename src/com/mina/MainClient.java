package com.mina;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;  
//1. mina-core.jar         2. slf4j-api.jar         3.slf4j-simple.jar
public class MainClient {  
  
 private static final int PORT = 8080;  
 
 public static void main(String[] args) throws Exception {  
	 //客户端的实现  
	 NioSocketConnector connector = new NioSocketConnector();  
	 // 设置链接超时时间  
     connector.setConnectTimeout(30000);  
     // 添加过滤器  
	 connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));  
	 connector.getFilterChain().addLast("logging", new LoggingFilter());  
	// 添加业务逻辑处理器类 
	 FileUploadClientHandler h = new FileUploadClientHandler();  
	 connector.setHandler(h);  
	 //本句需要加上，否则无法调用下面的readFuture来从session中读取到服务端返回的信息。  
	 connector.getSessionConfig().setUseReadOperation(true);  
	 // ip  port
	 ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",PORT));  
	  
	 IoSession session;  
	 //等待连接成功  
	 cf.awaitUninterruptibly();  
	 session = cf.getSession();  
	  
	 System.out.println("------------------------client send begin");  
	 //传递文件开始  
	 String fileName = "D:/testfile.txt";  
	 FileInputStream fis = new FileInputStream(new File(fileName));  
	 
	 //创建一个长度为4*1024的字节数组,每次都读取4kb,目的是缓存,如果不用缓冲区,用fis.read(),就会效率低,一个一个读字节
	 byte[] a = new byte[1024 * 4];  
	
	 FileUploadRequest request = new FileUploadRequest();  
	 
	 request.setFilename(fileName);  
	 
	 request.setHostname("localhost");  
	 
	 int len = -1;
	 
	 while ((len = fis.read(a, 0, a.length))!= -1) {  
		 System.out.println("*************************len="+len);
		 if(len<a.length){
			 byte[] a2 = new byte[len];  
			//因为最后一次len不同
			 System.arraycopy(a, 0, a2, 0, len);
			 request.setFileContent(a2);  
		 }else{
			 request.setFileContent(a);  
		 }
		 //像session中写入信息供服务端获得  
		 session.write(request);  
	 }  
	 
	 //发送完成的标志  
	 session.write(new String("finish"));  
	  
	 System.out.println("client send finished and wait success");  
	 //接上面来取得服务端的信息  
	 Object result = session.read().awaitUninterruptibly().getMessage();  
	 if (result.equals("success")) {  
			 System.out.println("success!");  
			 //关闭客户端  
			 connector.dispose();  
		 }  
	 }  
}  
