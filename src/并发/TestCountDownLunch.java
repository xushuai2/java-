package 并发;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 案例：
 * 取到一批数据，利用两个线程对数据进行逻辑处理
 * 当处理完过后，再用这批数据做一些其它事情
 * 
 * 
 *  CountDownLatch则不同，CountDownLatch有一个导游线程在等待，每个线程报到一下即可无须等待，等到导游线程发现所有人都已经报到了，就结束了自己的等待。
 * 
 * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。
 * 每当一个线程完成了自己的任务后，计数器的值就会减1。
 * 当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
 */
public class TestCountDownLunch {
	private static Random random = new Random(System.currentTimeMillis());
    private static ExecutorService executor = Executors.newFixedThreadPool(2);
    private static CountDownLatch latch = new CountDownLatch(9);
 
    public static void main(String[] args) throws InterruptedException {
        //获取数组
        int[] data = query();
        //两次线程去操作逻辑
        for (int i = 0; i < data.length; i++) {
            executor.execute(new SimpleRunnAble(data, i, latch));
 
        }
        //保证所有线程执行完毕，再执行下面程序
        latch.await();
        executor.shutdown();
        //获取处理过的数据
        System.out.println("取到数字" + Arrays.toString(data));
    }
 
    static class SimpleRunnAble implements Runnable {
        private final int[] data;
        private final int index;
        private final CountDownLatch latch;
 
        SimpleRunnAble(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }
 
        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 
            int value = data[index];
            if (value % 2 == 0) {
                data[index] = value * 2;
            } else {
                data[index] = value * 10;
            }
            System.out.println(Thread.currentThread().getName() + "结束");
            latch.countDown();
        }
    }
 
    private static int[] query() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }
}
