package synchronized和lock;

import java.util.concurrent.locks.Lock;

public class ConsumerLock implements Runnable{
	private Lock lock;
    public ConsumerLock(Lock lock) {
           this. lock = lock;
    }
    @Override
    public void run() {
           int count = 10;
           while( count > 0 ) {
                try {
                    lock.lock();
                    count --;
                    System. out.print( "B-");
               } finally {
                     lock.unlock(); //主动释放锁
                     try {
                          Thread. sleep(91L);
                    } catch (InterruptedException e) {
                          e.printStackTrace();
                    }
               }
          }

    }
}
