package synchronized和lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*使用建议：
在并发量比较小的情况下，使用synchronized是个不错的选择，但是在并发量比较高的情况下，其性能下降很严重，此时ReentrantLock是个不错的方案。*/
public class TestLock {
	public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        /*
        private ReentrantLock lock = new ReentrantLock(); //参数默认false，不公平锁
        private ReentrantLock lock = new ReentrantLock(true); //公平锁
        
                   不公平锁与公平锁的区别：
		公平情况下，操作会排一个队按顺序执行，来保证执行顺序。（会消耗更多的时间来排队）
		不公平情况下，是无序状态允许插队，jvm会自动计算如何处理更快速来调度插队。（如果不关心顺序，这个速度会更快）
		ReentrantLock  :  重入锁; 可重入锁
		可重入的意思是某一个线程是否可多次获得一个锁，比如synchronized就是可重入的
        */
        ProducerLock producer = new ProducerLock(lock);
        ConsumerLock consumer = new ConsumerLock(lock);
        
        new Thread( producer).start();
        new Thread(consumer).start();
        
  }
}
