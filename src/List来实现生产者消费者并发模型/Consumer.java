package List来实现生产者消费者并发模型;

import java.util.List;
/**
 * 消费者
 *
 */
public class Consumer implements Runnable {

	private List<Task> buffer;

    public Consumer(List<Task> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(buffer) {
                while(buffer.isEmpty()) {
                    try {
                    	System.out.println("Consumer 等待");
                        buffer.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Task task = buffer.remove(0);
                buffer.notifyAll();
                System.out.println("******Consumer 产品数量："+buffer.size());
                System.out.println("Consumer[" + Thread.currentThread().getName() + "] got " + task);
            }
        }
    }

}
