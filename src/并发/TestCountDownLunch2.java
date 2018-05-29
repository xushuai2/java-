package 并发;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/*
 * 
 *  CountDownLatch则不同，CountDownLatch有一个导游线程在等待，每个线程报到一下即可无须等待，等到导游线程发现所有人都已经报到了，就结束了自己的等待。
 * await(long timeout, TimeUnit unit)
await(long timeout, TimeUnit unit)：表示等待最长时间。*/
public class TestCountDownLunch2 {
	public static void main(String[] args) throws InterruptedException {
        //new CountDownLatch(0)当为0时，latch.await()无效果
        CountDownLatch latch = new CountDownLatch(1);
        new Thread() {
            @Override
            public void run() {
                try {
                	System.out.println("--------------------------");
                    Thread.sleep(100000);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        /**
         * 最多等待10秒
         * 1.如果10秒内，没有countDown为0，10秒后发行
         * 2.如果10秒内，countDown为0，立刻发行，不用等待10秒
         */
        latch.await(10,TimeUnit.SECONDS);
        System.out.println("==========");
    }
}
