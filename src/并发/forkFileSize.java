package 并发;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
/*一个典型的地方就是work-stealing，它的一个优点是在传统的线程池应用里，我们分配的每个线程执行的任务并不能够保证他们执行时间或者任务量是同样的多，
这样就可能出现有的线程完成的早，有的完成的晚。在这里，一个先完成的线程可以从其他正在执行任务的线程那里拿一些任务过来执行。*/
public class forkFileSize {
	private final static ForkJoinPool forkJoinPool = new ForkJoinPool();  
	  
    private static class FileSizeFinder extends RecursiveTask<Long>  
    {  
        final File file;  
  
        public FileSizeFinder(final File theFile)  
        {  
            file = theFile;  
        }  
    	/*最关键的部分在compute方法里。我们用了一个ArrayList tasks来保存所有出现目录的情形。
    	当遍历出来的元素是文件时，我们直接取文件的长度size += child.length();
    	而当为目录时则tasks.add(new FileSizeFinder(child));
    	这样当我们遍历某个目录的时候，它下面一级的子目录就全部被封装到tasks里了。
    	然后我们再通过invokeAll(tasks)这个方法去并行的执行所有遍历子目录的线程。*/
        
        @Override  
        public Long compute()  
        {  
            long size = 0;  
            if(file.isFile())  
            {  
                size = file.length();  
            }  
            else  
            {  
                final File[] children = file.listFiles();  
                if(children != null)  
                {  
                    List<ForkJoinTask<Long>> tasks =  
                        new ArrayList<ForkJoinTask<Long>>();  
                    for(final File child : children)  
                    {  
                        if(child.isFile())  
                        {  
                            size += child.length();  
                        }  
                        else  
                        {  
                            tasks.add(new FileSizeFinder(child));  
                        }  
                    }  
  
                    for(final ForkJoinTask<Long> task : invokeAll(tasks))  
                    {  
                        size += task.join();  //join方法得到异步方法执行的结果。等待子任务执行完，并得到其结果
                    }  
                }  
            }  
  
            return size;  
        }  
    }  
    
    private static String size2string(long size){  
    	  DecimalFormat df = new DecimalFormat("0.00");  
    	  String mysize = "";  
    	  if( size >= 1024*1024*1024){  
      	    mysize = df.format( size / 1024f / 1024f / 1024f ) +"G";  
      	  }else if( size >= 1024*1024){  
    	    mysize = df.format( size / 1024f / 1024f ) +"M";  
    	  }else if( size >= 1024 ){  
    	    mysize = df.format( size / 1024f ) +"K";  
    	  }else{  
    	    mysize = size + "B";  
    	  }  
    	  return mysize;  
    }  
    
	public static void main(String[] args) {
		/*java中System.nanoTime()返回的是纳秒
		1纳秒=0.000001 毫秒*/ 
		final long start = System.nanoTime();  
        final long total = forkJoinPool.invoke( new FileSizeFinder(new File("E:/myoracle")));  
        final long end = System.nanoTime();  
        System.out.println("Total Size: " + size2string(total));  
        System.out.println("Time taken: 秒" + (end - start)/1.0e9);
	}

}
