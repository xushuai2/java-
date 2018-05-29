package demo.common;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WatchThreadBingXing {
	private String name = UUID.randomUUID().toString();  
	  
    /** 
     * 测试函数 
     * @throws InterruptedException 
     */  
    public void testThread() throws InterruptedException {  
        int threadNum = 10;  
        // 初始化countDown  
        CountDownLatch threadSignal = new CountDownLatch(threadNum);  
        // 创建固定长度的线程池  
        //Executor executor = Executors.newFixedThreadPool(threadNum);  
        //此处不可以用接口 需要使用Executor的实现类 ExecutorService  Executor未提供shutdown等方法  
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);  
        for (int i = 0; i < threadNum; i++) { // 开threadNum个线程  
            Runnable task = new TestThread(threadSignal);  
            executor.execute(task);  // 执行  
        }  
        threadSignal.await(); // 等待所有子线程执行完  
        //固定线程池执行完成后 将释放掉资源 退出主进程  
        executor.shutdown();//并不是终止线程的运行，而是禁止在这个Executor中添加新的任务  
        // do work end  退出主进程  
        System.out.println(Thread.currentThread().getName() + "+++++++结束.");  
    }  
  
    /** 
     *  
     * @author wangmuming 
     * 此可以做完内部类 也可以不做未内部类 
     * 作为内部类的时候 有一个好处 就是可以直接引用给类的主对象的成员变量 如此处的name 
     * 当然 
     */  
    private class TestThread implements Runnable {  
        private CountDownLatch threadsSignal;  
        /*
         * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
         *主要方法
		 public CountDownLatch(int count);
		 public void countDown();
		 public void await() throws InterruptedException
		 
		 构造方法参数指定了计数的次数
		 countDown方法，当前线程调用此方法，则计数减1
		 awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0*/
        public TestThread(CountDownLatch threadsSignal) {  
            this.threadsSignal = threadsSignal;  
        }  
        
        public void run() {  
            System.out.println(Thread.currentThread().getName() + "开始..." + name);  
            System.out.println("-------->开始了线程：" + threadsSignal.getCount());  
            // do shomething  
            // 核心处理逻辑  
            // 用到成员变量name作为参数  
            // 线程结束时计数器减1  
            threadsSignal.countDown();//必须等核心处理逻辑处理完成后才可以减1  
            System.out.println(Thread.currentThread().getName() + "结束;------ 还有"+ threadsSignal.getCount() + " 个线程");  
        }  
    }  
    
    public static void main(String[] args) throws InterruptedException {
    	WatchThreadBingXing ww =new WatchThreadBingXing();
    	ww.testThread();
	}
}
