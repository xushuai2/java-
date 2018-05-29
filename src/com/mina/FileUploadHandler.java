package com.mina;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;  
  
public class FileUploadHandler extends IoHandlerAdapter {  
  
	 private BufferedOutputStream out;  
	  
	 private int count;  
	 private int count2 = 0;
	  
	 private static final Log log = LogFactory.getLog(FileUploadHandler.class);  
	  
	  
	 public void sessionOpened(IoSession session) throws Exception {  
		 count2++;
	     System.out.println("第 " + count2 + " 个 client 登陆！address： : "+ session.getRemoteAddress());
	 }  
	  
	 public void exceptionCaught(IoSession session, Throwable cause)  
	 throws Exception {  
		 System.out.println("--------exception");  
		 session.close(true);  
		 super.exceptionCaught(session, cause);  
	 }  
	  
	 public void messageReceived(IoSession session, Object message) {  
		 System.out.println("----------server received");  
		 System.out.println("----------message instanceof FileUploadRequest="+(message instanceof FileUploadRequest));  
		 try {  
			 if (message instanceof FileUploadRequest) {  
			     //FileUploadRequest 为传递过程中使用的DO。  
				 FileUploadRequest request = (FileUploadRequest) message;  
				 System.out.println(request.getFilename());  
				 String filename = request.getFilename();
				 String f2 = filename.substring(request.getFilename().lastIndexOf("/")+1);
				 
				 //新建一个文件输入对象BufferedOutputStream，随便定义新文件的位置  
				 out = new BufferedOutputStream(new FileOutputStream(  "D:/" + "shuai-mina-"+new Date().getTime()+f2));  
				 out.write(request.getFileContent());  
				 count += request.getFileContent().length;  
			  
			 } else if (message instanceof String) {  
				 if (((String)message).equals("finish")) {  
					 System.out.println("size is="+count);  
					 //这里是进行文件传输后，要进行flush和close否则传递的文件不完整。  
					 out.flush();  
					 out.close();
					 //回执客户端信息，上传文件成功  
					 session.write("success");  
				 }  
			 }  
			 count = 0;
		 } catch (Exception e) {  
			 e.printStackTrace();  
		 }  
	 }  
	  
	 public void sessionClosed(IoSession session) throws Exception {  
		 System.out.println("one Clinet Disconnect !");
	 }  
	 
     public void messageSent(IoSession session, Object message) {
        System.out.println("信息已经传送给客户端");
     }
	 
	 // 当连接空闲时触发此方法.
     @Override
     public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println(session.getId()+"-----------连接空闲");
     }
}  
