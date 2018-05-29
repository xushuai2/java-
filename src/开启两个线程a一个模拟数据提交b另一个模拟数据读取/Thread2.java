package 开启两个线程a一个模拟数据提交b另一个模拟数据读取;

import java.util.LinkedList;
import java.util.Queue;

public class Thread2 extends Thread{
	private Queue<String> queue = new LinkedList<String>();  
    private static int j = 1;  
  
    public Thread2(Queue<String> queue2) {  
        this.queue = queue2;  
    }  
  
    public synchronized void run() {  
        while (true) {  
            String str;  
            while (!queue.isEmpty()) {  
                int k = 10000;// 每次提交数设置为10，可以根据需要修改  
                while ((str = queue.poll()) != null) {  
                    --k;  
                    if(k%200==0){
                    	System.out.println(k + "\t 取出的值：" + str);  
                    }
                    if (k == 0) {  
                        break;  
                    }  
                }  
                System.out.println("当前运行的线程是："+Thread.currentThread().getName()+"\t 第"+j+"次提交！队列还剩元素数"+ queue.size());  
                j++;  
                try {  
                    Thread.sleep(5000);// 每次提交以后休眠5秒  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}
