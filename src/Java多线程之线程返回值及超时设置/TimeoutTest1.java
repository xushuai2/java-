package Java多线程之线程返回值及超时设置;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeoutTest1 {

	 public static void main(String[] args) {
	  final ExecutorService service = Executors.newFixedThreadPool(1);

	  TaskThread taskThread = new TaskThread();
	  System.out.println("提交任务...begin");
	  Future<Object> taskFuture = service.submit(taskThread);
	  System.out.println("提交任务...end");
	  try {
	   Object re = taskFuture.get(4000, TimeUnit.MILLISECONDS);//超时设置，6s
	   System.out.println(re);
	  } catch (InterruptedException e) {
	   e.printStackTrace();
	  } catch (ExecutionException e) {
	   e.printStackTrace();
	  } catch (TimeoutException e) {
	   System.out.println("超时 取消任务");
	   taskFuture.cancel(true);
	   System.out.println("超时 取消任务OK");
	  } finally {
	   service.shutdown();
	  }

	 }

	}

class TaskThread implements Callable<Object> {

	 public Object call() throws Exception {
		  String result = "空结果";
		  try {
			   System.out.println("任务开始....");
			   Thread.sleep(5000);
			   result = "正确结果";
			   System.out.println("任务结束....");
		  } catch (Exception e) {
			   System.out.println("Task is interrupted!");
		  }
		  return result;
	 }

}


