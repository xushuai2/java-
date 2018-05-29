package List来实现生产者消费者并发模型;

import java.util.UUID;
/**
 * 工作任务
 *
 */
public class Task {
	private String id;  // 任务的编号

    public Task() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Task[" + id + "]";
    }
}
