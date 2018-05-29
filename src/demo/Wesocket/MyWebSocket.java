package demo.Wesocket;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/websocket")
public class MyWebSocket {
	private static final String GUEST_PREFIX = "guest";
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
     
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;// 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private final String nickname;//定义一个记录客户端的聊天昵称
    
    private final Map<String, Session> smap = new HashMap<String, Session>();
    
    public MyWebSocket() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }
    
    
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        smap.put(nickname, session);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        String message = nickname + ",has joined";
        broadcast2(message);
    }
    
    private static void broadcast(String msg) {
        HashMap<String,String> map = new HashMap<String,String>();
        String msgString  = msg.toString();
        String m[] = msgString.split(",");
        map.put("fromName", m[0]);
        map.put("toName", m[1]);
        map.put("content", m[2]);
        //上线用户集合类map
        String fromName = map.get("fromName");    //消息来自人 的userId
        String toName = map.get("toName");        //消息发往人的 userId
        String mess = map.get("content");         //消息
        
        for (MyWebSocket client : webSocketSet) {
        	if(client.nickname.equals(toName)){
        		try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText(fromName+" : "+mess);
                    }
                } catch (IOException e) {
                	System.out.println("Chat Error: Failed to send message to client");
                	webSocketSet.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                    	
                    }
                    String message = String.format("* %s %s",client.nickname, "has been disconnected.");
                    broadcast(message);
                }
        	}
        }
    }
    
    
	private  void broadcast2(String msg) {
        
        for (MyWebSocket client : webSocketSet) {
        	
        	if(client.nickname.equals(this.nickname)){
        		try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText(msg);
                        break;
                    }
                } catch (IOException e) {
                	System.out.println("Chat Error: Failed to send message to client");
                	webSocketSet.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                    	
                    }
                    String message = String.format("* %s %s",client.nickname, "has been disconnected.");
                    broadcast(message);
                }
        	}
        }
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1    
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        String message = nickname + ",has disconnected.";
        broadcast2(message);
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的密文消息:" + message);
        //message = Base64Util.decodeToString(message);
        try {
			message = URLDecoder.decode(URLDecoder.decode(message, "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        System.out.println("来自客户端的明文消息:" + message);
        broadcast(message);
    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
