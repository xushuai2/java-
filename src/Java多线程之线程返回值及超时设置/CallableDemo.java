package Java多线程之线程返回值及超时设置;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException; 

public class CallableDemo {  
    public static void main(String[] args){  
    	int timeout = 5; //秒.
    	
    	//Executor是Java SE6/7中启动任务的优选方法。
        ExecutorService exec = Executors.newCachedThreadPool();  
        //ArrayList<Future<String>>  results = new ArrayList<Future<String>>();  
        for(int i = 0; i < 20; i++){  
        	/*Submit方法会产生Future对象，他用Callable返回结果的特定类型进行了参数化。*/
        	Future<String> fs =  exec.submit(new TaskWithResult(i));  
            try{  
            	String result = "";
            	result = fs.get(timeout*1000, TimeUnit.MILLISECONDS);// 设定在t*1000毫秒的时间内完成   
                System.out.println(result);//可以调用很多方法，包括是否工作等等  
                
            } catch (InterruptedException e) {  
                System.out.println("线程中断出错。");  
                fs.cancel(true);// 中断执行此任务的线程     
                e.printStackTrace();
            } catch (ExecutionException e) {     
                System.out.println("线程服务出错。");  
                fs.cancel(true);// 中断执行此任务的线程     
                e.printStackTrace();
            } catch (TimeoutException e) {// 超时异常     
                fs.cancel(true);// 中断执行此任务的线程     
                System.out.println("超时 取消任务OK");
                e.printStackTrace();
            }finally{  
            	
            }  
        }
        exec.shutdown();  
   }
}  
  
//实现这个接口，调用的是call()方法   有返回值
class TaskWithResult implements Callable<String>{  
    private int id;  
    public TaskWithResult(int id){  
        this.id  = id;  
    }  
      
    public String call(){  
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {	
			 System.out.println("Task is interrupted!");
		}
        return "result of TaskWithResult" + id;  
    }  
}  
  


