package 开启两个线程a一个模拟数据提交b另一个模拟数据读取;

import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;

public class Thread1 extends Thread{
	private Queue<String> queue = new LinkedList<String>();
	private static int m = 1;
	public Thread1(Queue queue1){
		this.queue = queue1; 
	}
	
	@Override
	public synchronized  void run() {
		while(true){
			Random rnd = new Random();  
            int len = rnd.nextInt(10)*10000;// 每次随机生产10个一下个元素加入队列  
  
            for (int i = 0; i < len; i++) {  
                queue.offer("element:" + (m++));  
            }  
            System.out.println("当前运行的线程是：" + Thread.currentThread().getName() + "\t 本次新加入元素数：" + len + "\t 队列中元素总数是："  
                    + queue.size());  
            try {  
                Thread.sleep(rnd.nextInt(5000));// 随机休眠5秒之内的时间  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
		}
			
	}
}
