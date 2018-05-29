package synchronized和lock;

public class Consumer implements Runnable {
	/*synchronized 用在代码块的使用方式：synchronized(obj){
		//todo code here
	}
	当线程运行到该代码块内，就会拥有obj对象的对象锁，如果多个线程共享同一个Object对象，那么此时就会形成互斥！
	特别的，当obj == this时，表示当前调用该方法的实例对象。*/
    @Override
    public synchronized void run() {
           int count = 10;
           while(count > 0) {
                synchronized (Test.obj) {
                    System.out.print("B"+"-");
                    count --;
                    Test.obj.notify(); // 主动释放对象锁
                    try {
                          Test.obj.wait();
                    } catch (InterruptedException e) {
                          e.printStackTrace();
                    }
               }
               
          }
    }
}
