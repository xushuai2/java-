package demo.daemon;
import java.util.Date;  
import java.util.Deque;
public class CleanerTask extends Thread {
	private Deque<Event> deque;  
	public Deque<Event> getDeque() {  
        return deque;  
    }  
  
    public void setDeque(Deque<Event> deque) {  
        this.deque = deque;  
    }  
    public CleanerTask(Deque<Event> deque) {  
        this.deque = deque;  
        setDaemon(true);  //设置为守护进程  
    }  
      
    @Override  
    public void run() {  
        while(true) {  
            Date date = new Date();  
            clean(date);  
        }  
    }  
  
    private void clean(Date date) {  
        long difference = 0;  
        boolean delete = false;  
          
        if(deque.size() == 0) {  
            return;  
        }  
          
        do {  
            Event e = deque.getLast();  
            difference = date.getTime() - e.getDate().getTime();  
            if(difference > 10000) {  
                System.out.println("守护进程---准备清理： " + e.getEvent());  
                deque.removeLast();  
                delete = true;  
            }  
        }while(difference > 10000);  
          
        if(delete) {  
            System.out.println("守护进程- : the size of the deque " + deque.size());  
        }  
    }  
  
    
}
