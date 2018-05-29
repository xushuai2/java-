package 读写锁ReadWriteLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
	public static void main(String[] args) {  
        //创建并发访问的账户  
        MyCount myCount = new MyCount("95599200901215522", 10000);  
        //创建一个锁对象 
        //参数默认false，不公平锁
        /*不公平锁与公平锁的区别：
		公平情况下，操作会排一个队按顺序执行，来保证执行顺序。（会消耗更多的时间来排队）
		不公平情况下，是无序状态允许插队，jvm会自动计算如何处理更快速来调度插队。（如果不关心顺序，这个速度会更快）*/
        ReadWriteLock lock = new ReentrantReadWriteLock(false);  
        //创建一个线程池  
        ExecutorService pool = Executors.newFixedThreadPool(2);  
        //创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊  
        User u1 = new User("张三", myCount, -4000, lock, false);  
        User u2 = new User("张三他爹", myCount, 6000, lock, false);  
        User u3 = new User("张三他弟", myCount, -8000, lock, false);  
        User u4 = new User("张三", myCount, 800, lock, false);  
        User u5 = new User("张三他爹", myCount, 0, lock, true);  
        //在线程池中执行各个用户的操作  
        pool.execute(u1);  
        pool.execute(u2);  
        pool.execute(u3);  
        pool.execute(u4);  
        pool.execute(u5);  
        //关闭线程池  
        pool.shutdown();  
}  
}
