package java多线程模拟并行计算框架;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
 
//用十个线程计算一个数组的和
class TaskWithResult implements Callable<Long>  {
    private Integer[] ints;
 
    public TaskWithResult(Integer[] ints) {
        this.ints = ints;
    }
 
    private long sumFromArray() {
        long result = 0;
        for (int a : this.ints) {
            result += a;
        }
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return result;
    }
 
    public Long call() throws Exception {
        return sumFromArray();
    }
}
 
public class Paiallel1 {
    public static Integer[] segArray(int start, int end, int[] array) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = start; i < end; i++) {
            result.add(array[i]);
        }
 
        Integer[] ary = result.toArray(new Integer[result.size()]);
 
        return ary;
    }
    
    public static int getrandom(){
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
        return new Random().nextInt(100);
    }
 
    public static long getSum(int[] array) {
        long result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return result;
    }
 
	public static void main(String[] args) throws InterruptedException,
            ExecutionException {
		int  xcnum = 3;
        long start1 = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(xcnum);
        int num = 10000000;
        int[] array = new int[num];
        for(int i=0;i<num;i++){
        	array[i]=40;
        }
 
        List<FutureTask<Long>> tasks = new ArrayList<FutureTask<Long>>();
 
        for (int i = 0; i < 10; i++) {
            int incre = array.length / 10;
            int start = i * incre;
            int end = (i + 1) * incre;
            if (end > array.length)
                end = array.length;
            Integer[] prt = segArray(start, end, array);
 
            TaskWithResult calbTask = new TaskWithResult(prt);
            //创建一个异步任务
            FutureTask<Long> task = new FutureTask<Long>(calbTask);
            tasks.add(task);
            if (!exec.isShutdown()) {
            	//提交异步任务到线程池，让线程池管理任务 特爽把。
                //由于是异步并行任务，所以这里并不会阻塞
                exec.submit(task);
            }
        }
 
        long result = 0;
        for (FutureTask<Long> task : tasks) {
        	//futureTask.get() 得到我们想要的结果 
            //该方法有一个重载get(long timeout, TimeUnit unit) 第一个参数为最大等待时间，第二个为时间的单位
        	long partRst = task.get();
            result += partRst;
        }
        exec.shutdown();
        long end1 = System.currentTimeMillis();
        System.out.println("并行计算耗时：" + (end1 - start1));
        System.out.println("并行计算的结果：" + result);
         
        long start2 = System.currentTimeMillis();
        long result2 = getSum(array);
        long end2 = System.currentTimeMillis();
         
        System.out.println("单独计算耗时：" + (end2 - start2));
        System.out.println("单独计算的结果：" + result2);
 
    }
 
}
