package Lock与synchronized性能比较;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
/*JDK 1.6以上synchronized也改用CAS来实现了，所以两者性能差不多
Lock提供的功能丰富点，synchronized的使用简单点*/
public class SynLockTest {
	static class SynRunner implements Runnable { 
        private long v = 0;
        @Override 
        public synchronized void run() { 
            v = v + 1; 
        } 
    }
    static class LockRunner implements Runnable { 
        private ReentrantLock lock = new ReentrantLock(); 
        private long v = 0;
        @Override 
        public void run() { 
            lock.lock(); 
            try { 
                v = v + 1; 
            } finally { 
                lock.unlock(); 
            } 
        }
    }
    static class Tester { 
        private AtomicLong runCount = new AtomicLong(0); 
        private AtomicLong start = new AtomicLong(); 
        private AtomicLong end = new AtomicLong();
        public Tester(final Runnable runner, int threadCount) { 
            final ExecutorService pool = Executors.newFixedThreadPool(threadCount); 
            Runnable task = new Runnable() { 
                @Override 
                public void run() { 
                    while (true) { 
                        runner.run(); 
                        long count = runCount.incrementAndGet(); 
                        if(count<20){
                    		System.out.println(runner.getClass().getSimpleName()+"------------------"+count);
                    	}
                        
                        if (count == 1) { 
                            start.set(System.nanoTime()); 
                        } else if (count >= 100000000) { 
                        	
                        	
                            if (count == 100000000) { 
                                end.set(System.nanoTime()); 
                                long s =start.longValue();
                                long e =end.longValue();
                                System.out.println(runner.getClass().getSimpleName() + ", cost: "+ (e-s)/1000000 + "ms");   
                            } 
                            pool.shutdown(); 
                            return; 
                        } 
                    } 
                } 
            }; 
            for (int i = 0; i < threadCount; i++) { 
                pool.submit(task); 
            } 
        } 
    }
    public static void main(String[] args) { 
        new Tester(new SynRunner(), 3); 
        new Tester(new LockRunner(), 3); 
    }
}
