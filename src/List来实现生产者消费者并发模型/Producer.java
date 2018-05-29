package List来实现生产者消费者并发模型;

import java.util.List;

public class Producer implements Runnable {
	private List<Task> buffer;

    public Producer(List<Task> buffer) {
        this.buffer = buffer;
    }
	@Override
	public void run() {
		while(true) {
            synchronized (buffer) {
                while(buffer.size() >= Constants.MAX_BUFFER_SIZE) {
                    try {
                    	System.out.println("Producer 等待");
                        buffer.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                Task task = new Task();
                buffer.add(task);
                buffer.notifyAll();
                System.out.println("Producer 产品数量："+buffer.size());
                System.out.println("Producer[" + Thread.currentThread().getName() + "] put " + task);
            }
        }

	}

}
