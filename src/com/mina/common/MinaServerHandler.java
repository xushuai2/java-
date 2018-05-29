package com.mina.common;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;  
public class MinaServerHandler extends IoHandlerAdapter {  
	private final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
      
    @Override  
    public void sessionCreated(IoSession session) throws Exception {  
        System.out.println("服务端与客户端创建连接...");
        sessions.add(session);
        String remoteaddress = session.getRemoteAddress().toString();
        String clientip = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress(); 
        session.write("welcome to the chat room !   ----clientip="+clientip+"---remoteaddress="+remoteaddress);
    }  
      
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
        System.out.println("服务端与客户端打开连接");
    }  
      
    @Override  
    public void sessionClosed(IoSession session) throws Exception {  
    	System.out.println("关闭当前session：{}#{}"+session.getId()+"----"+session.getRemoteAddress());
    	sessions.remove(session);
        CloseFuture closeFuture = session.close(true);
        closeFuture.addListener(new IoFutureListener<IoFuture>() {
            public void operationComplete(IoFuture future) {
                if (future instanceof CloseFuture) {
                    ((CloseFuture) future).setClosed();
                    System.out.println("sessionClosed CloseFuture setClosed-->{},sessionid="+future.getSession().getId());
                }
            }
        });
    }  
      
    //这个方法用于接收监听接收到的报文数据，以进行相关的业务处理  
    @Override  
    public void messageReceived(IoSession session, Object message) throws Exception{  
    	
    	 String str = message.toString();
         if( str.trim().equalsIgnoreCase("quit") ) {
             session.close(true);
             return;
         }
    	
    	String content = message.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = sdf.format(new Date());
        // 拿到所有的客户端Session
        Collection<IoSession> sessions = session.getService().getManagedSessions().values();
        // 向所有客户端发送数据
        for (IoSession sess : sessions) {
            sess.write(datetime + "\t" + content);
        }
        
        
    }  
      
    @Override  
    public void messageSent(IoSession session, Object message) throws Exception {  
        System.out.println("服务端发送消息成功！");
    }  
      
    @Override  
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {  
        System.out.println("服务端进入空闲状态...");
    }  
      
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
        System.out.println("服务端发送异常..."+ cause);
    }  
      
      
}  
