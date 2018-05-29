package demo.CSCallBack;

public class Client implements CSCallBack {
	private Server server;

    public Client(Server server) {
        this.server = server;
    }

   /* 1、接口作为方法参数，其实际传入引用指向的是实现类
    2、Client的sendMsg方法中，参数为final，因为要被内部类一个新的线程可以使用。这里就体现了异步。
    3、调用server的getClientMsg()，参数传入了Client本身（对应第一点）。*/
    public void sendMsg(final String msg){
        System.out.println("客户端：发送的消息为：" + msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.getClientMsg(Client.this,msg);
            }
        }).start();
        System.out.println("客户端：异步发送成功");
    }
    @Override
    public void process(String status) {
        System.out.println("客户端：服务端回调状态为：" + status);
    }

}
