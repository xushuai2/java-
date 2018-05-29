package 多线程mysql;
import java.util.concurrent.CountDownLatch;
public class JDBCThread {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " is running...");
        int threadnum = 3;
        CountDownLatch cnt = new CountDownLatch(threadnum);
        for(int i = 0; i < threadnum; i++)
        {
            JDBCwork jt = new JDBCwork(cnt);
            jt.start();
        }
        try
        {
            cnt.await();
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is done...");
	}

}
