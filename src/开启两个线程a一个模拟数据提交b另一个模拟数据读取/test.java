package 开启两个线程a一个模拟数据提交b另一个模拟数据读取;

import java.util.LinkedList;
import java.util.Queue;

public class test {
	/*java 大量数据提交时，使用队列定时提交思路的程序实现
	 * 思路如下：
		一个队列，提交的信息不断的放入队列；
		每隔一段时间定时提交一次，不管队列中元素是否达到预定的个数；
		
		模拟程序的思路：
		开启两个线程，一个模拟数据提交；另一个模拟数据读取（取出来插入数据库）；
	 * */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {  
        Queue<String> queue = new LinkedList<String>();  
        // 创建两个线程，让其不间断运行  
        // 线程1模拟随机添加元素  
        // 线程2模拟每隔5秒每次提交10个元素  
        Thread1 t1 = new Thread1(queue);
        Thread2 t2 = new Thread2(queue);
  
        t1.setName("线程一");  
        t2.setName("线程二");  
  
        t1.start();  
        t2.start();  
        try {
			Thread.currentThread().sleep(10000);
			System.out.println("-----"+queue.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
        System.out.println("-----"+queue.size());
        demo d =new demo(queue);
        System.out.println("-----"+queue.size());
        
        
        StringBuffer s = new StringBuffer("good");
        StringBuffer s2= s;
        s2.append(" afternoon!");
        System.out.println(s);
    }  
}
