package java多线程模拟并行计算框架;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
//用十个线程计算一个数组的和
class TaskWithResult1 implements Callable<Integer> {
    private Integer[] ints;
 
    public TaskWithResult1(Integer[] ints) {
        this.ints = ints;
    }
 
    private int sumFromArray() {
        int result = 0;
        for (int a : this.ints) {
            result += a;
        }
        return result;
    }
 
    @Override
    public Integer call() throws Exception {
        return sumFromArray();
    }
}
 
public class Paiallel2 {
    public static Integer[] segArray(int start, int end, int[] array) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = start; i < end; i++) {
            result.add(array[i]);
        }
 
        Integer[] ary = result.toArray(new Integer[result.size()]);
 
        return ary;
    }
 
    public static int getSum(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result;
    }
 
    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        CompletionService<Integer> cplSvc = new ExecutorCompletionService<Integer>(exec);
 
        int[] array = { 300, 800, 89, 390, 892, 9384, 909, 1, 343, 5839, 939,
                43, 355, 323, 32, 55, 3, 3, 43, 5, 5, 45, 555, 554, 554, 555,
                545, 555, 553, 35, 2322, 332, 3232, 433, 344, 524, 245, 524,
                6565, 526 };
 
        for (int i = 0; i < 10; i++) {
            int incre = array.length / 10;
            int start = i * incre;
            int end = (i + 1) * incre;
            if (end > array.length)
                end = array.length;
            Integer[] prt = segArray(start, end, array);
 
            TaskWithResult1 calbTask = new TaskWithResult1(prt);
            if (!exec.isShutdown()) {
                cplSvc.submit(calbTask);
            }
        }
 
        int result = 0;
        for (int i = 0; i < 10; i++) {
            int partRst = cplSvc.take().get();
            result += partRst;
        }
        exec.shutdown();
        System.out.println("并行计算的结果：" + result);
 
        int result2 = getSum(array);
        System.out.println("单独计算的结果：" + result2);
 
    }
 
}
