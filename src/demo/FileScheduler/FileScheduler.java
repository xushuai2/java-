package demo.FileScheduler;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FileScheduler {
	// 定时器  
    private final Timer timer;  
      
    public FileScheduler(){  
        timer = new Timer();  
    }  
    public FileScheduler(boolean isDaemon){  
        // 是否为守护线程  
        timer = new Timer(isDaemon);  
    }  
    /** 
     * 为定时器分配可执行任务 
     * @param task 
     * @param step 
     */  
    public void schedule(Runnable task,TimeStep step){  
    	//获取下一个执行时间
        Date time = step.next();  
        TimerTask timeTask = new SchedulerTimerTask(task,step);  
        // 安排在指定的时间 time 执行指定的任务 timetask  
        timer.schedule(timeTask, time);  
        /*此处不使用  public void schedule(TimerTask task, Date firstTime, long period)  
                执行定时任务,而是使用 reSchedule 来重复执行任务,是因为首次执行时可能需要做一些额外的初始化,  这样方便以后扩展.  */
    }  
    /** 
     * 重新执行任务 
     * @param task 
     * @param step 
     */  
    private void reSchedule(Runnable task,TimeStep step){  
        Date time = step.next();  
        SchedulerTimerTask timeTask = new SchedulerTimerTask(task,step);  
        // 安排在指定的时间 time 执行指定的任务 timetask  
        timer.schedule(timeTask, time);  
    }  
    /** 
     * 停止当前定时器 
     */  
    public void cancle(){  
        timer.cancel();  
    }  
    /** 
     * 定时任务 
     * @author dycc 
     * 
     */  
    private class SchedulerTimerTask extends TimerTask{  
        private Runnable task;  
        private TimeStep step;  
          
        public SchedulerTimerTask(Runnable task,TimeStep step){  
            this.task = task;  
            this.step = step;  
        }  
        @Override  
        public void run() {  
            // 执行指定任务  
            task.run();  
            // 继续重复执行任务  
            reSchedule(task, step);  
        }  
    }  
}  

