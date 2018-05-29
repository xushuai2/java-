package 并发;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		// 声明一个容量为100的缓存队列
		//阻塞队列LinkedBlockingQueue用法
		BlockingQueue queue = new LinkedBlockingQueue(100);
		System.out.println("----程序开始运行----");  
		Date date1 = new Date();  
		
		for(int i=0;i<80;i++){
			queue.put("A"+i);
		}
		
		// 借助Executors  创建一个线程池  
		ExecutorService service = Executors.newCachedThreadPool();
		 // 创建多个任务  
		for(int i=0;i<2;i++){
			Consumer consumer = new Consumer(queue);
			service.execute(consumer);
		}
		// 退出Executor
		service.shutdown();	
	}
}



class Consumer implements Runnable {
	private BlockingQueue<?> queue;
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
	public Consumer(BlockingQueue<?> queue) {
		this.queue = queue;
	}
	
	public void run() {
		Random r = new Random();
		boolean isRunning = true;
		try {
			System.out.println(Thread.currentThread().getName()+"正从队列获取数据...");
			while (isRunning) {
				String data = (String) queue.poll(2, TimeUnit.SECONDS);
				if (null != data) {
					System.out.println(Thread.currentThread().getName()+"正在消费数据：" + data+"---queue.size()="+queue.size());
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
				} else {
					// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
					isRunning = false;
				}
			}
			if(queue.isEmpty()){
				System.out.println(">>>queue.size=("+queue.size()+")--------------------------时间："+new Date().getTime());
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName()+"退出消费者线程！");
		}
	}


}
