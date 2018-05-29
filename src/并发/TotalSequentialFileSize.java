package 并发;
import java.io.File;
//单线程递归方式
/*Total Size: 2285307344
Time taken: 4.372820584*/
public class TotalSequentialFileSize {
	 public static String fileName = "E:/myoracle";
	    // 递归方式 计算文件的大小
	    private long getTotalSizeOfFilesInDir(final File file) {
	        if (file.isFile())
	            return file.length();
	        final File[] children = file.listFiles();
	        long total = 0;
	        if (children != null)
	            for (final File child : children)
	                total += getTotalSizeOfFilesInDir(child);
	        return total;
	    }
	    public static void main(final String[] args) {
	        final long start = System.nanoTime();
	        final long total = new TotalSequentialFileSize().getTotalSizeOfFilesInDir(new File(fileName));
	        final long end = System.nanoTime();
	        System.out.println("Total Size: " + total);
	        System.out.println("Time taken: " + (end - start) / 1.0e9);
	    }
}
