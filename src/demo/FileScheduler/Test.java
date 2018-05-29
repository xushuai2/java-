package demo.FileScheduler;

public class Test {
	public static void main(String[] args) {
		FileSchedulerTask task = new FileSchedulerTask("E:\\resourceXu");  
        FileScheduler monitor = new FileScheduler();  
        monitor.schedule(task, new TimeStep());  
	}

}
