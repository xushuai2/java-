package 并发;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * CyclicBarrier是参与的所有的线程彼此等待，
 * CountDownLatch则不同，CountDownLatch有一个导游线程在等待，每个线程报到一下即可无须等待，等到导游线程发现所有人都已经报到了，就结束了自己的等待。
 * 
 * CyclicBarrier是一个用于线程同步的辅助类，它允许一组线程等待彼此，直到所有线程都到达集合点，然后执行某个设定的任务。
 * 有个很好的例子来形容：几个人约定了某个地方集中，然后一起出发去旅行。每个参与的人就是一个线程，CyclicBarrier就是那个集合点，所有人到了之后，就一起出发。
*/
public class TestCyclicBarrier {
	
	public static void main(String[] args) {
        int count = 10;//并发线程数
/*        
 * CyclicBarrier的构造函数有两个：
     // parties是参与等待的线程的数量，barrierAction是所有线程达到集合点之后要做的动作
        public CyclicBarrier(int parties, Runnable barrierAction);

        // 达到集合点之后不执行操作的构造函数
        public CyclicBarrier(int parties)*/
        
        
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        int n = 0;
        for (int i = 0; i < count; i++) {
            executorService.execute(new TestCyclicBarrier().new Task(cyclicBarrier, n));
            n++;
        }
        executorService.shutdown(); // 关闭线程池
        // 判断是否所有的线程已经运行完
       /* isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
        ExecutorService的 isTerminated()实现子线程先运行完后再推出主线程*/
        while (!executorService.isTerminated()) {
            // 所有线程池中的线程执行完毕，执行后续操作
//			System.out.println("........");
        }
        
        System.out.println("----------------------------------over");
    }

    public class Task implements Runnable {
        private CyclicBarrier cyclicBarrier;
        int n = 0;

        public Task(CyclicBarrier cyclicBarrier, int n) {
            this.cyclicBarrier = cyclicBarrier;
            this.n = n;
        }

        @Override
        public void run() {	
            try {
                // 等待所有任务准备就绪
                System.out.println("赛马" + n + "到达栅栏前");
//                线程是通过调用CyclicBarrier的await方法来等待其他线程
                cyclicBarrier.await();
                System.out.println("赛马" + n + "开始跑");
                // 测试内容
//                System.out.println("hello: " + n);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
