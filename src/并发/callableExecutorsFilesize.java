package 并发;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
//太慢了  速度太慢 
public class callableExecutorsFilesize {
	public static String fileName = "E:\\Youxun";
    private long getTotalSizeOfFilesInDir(final ExecutorService service,final File file) throws InterruptedException, ExecutionException,TimeoutException {
        if (file.isFile())
            return file.length();
        long total = 0;
        final File[] children = file.listFiles();
        if (children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
            for (final File child : children) {
                partialTotalFutures.add(
                		service.submit(
                				new Callable<Long>() {	
		                			public Long call() throws InterruptedException,ExecutionException, TimeoutException {
		                				return getTotalSizeOfFilesInDir(service, child);
		                			}
                				}
                		)
                );
            }
            for (final Future<Long> partialTotalFuture : partialTotalFutures)
            	//取得结果，同时设置超时执行时间为1000秒。同样可以用future.get()，不设置执行超时时间取得结果  
                total += partialTotalFuture.get(1000, TimeUnit.SECONDS);
        }
        return total;
    }
    
    private long getTotalSizeOfFile(final String fileName) throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            return getTotalSizeOfFilesInDir(service, new File(fileName));
        } finally {
            service.shutdown();
        }
    }
    
    public static void main(final String[] args) throws InterruptedException,
            ExecutionException, TimeoutException {
        final long start = System.nanoTime();
        final long total = new callableExecutorsFilesize().getTotalSizeOfFile(fileName);
        final long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start) / 1.0e9);
    }
}
