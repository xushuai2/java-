package Java多线程实现异步;

/*并发模型（一）——Future模式
多线程开发可以更好的发挥多核cpu性能，常用的多线程设计模式有：
Future、Master-Worker、Guard Susperionsion、不变、生产者-消费者 模式；
jdk除了定义了若干并发的数据结构，也内置了多线程框架和各种线程池；    
锁（分为内部锁、重入锁、读写锁）、ThreadLocal、信号量等在并发控制中发挥着巨大的作用。

这里重点介绍第一种并发——Future模型。*/
public class FutureData implements Data {  
	  private RealData realdata = null;  
	  private boolean ready = false;  
	  public synchronized void setRealData(RealData realdata) {  
	    if (ready) {              
	      return;   // 防止setRealData被调用两次以上。 
	    }  
	    this.realdata = realdata;  
	    this.ready = true;  
	    notifyAll(); //唤醒全部。 
	  }  
	  public synchronized String getContent() {  
		//这时候如果蛋糕没做好，就只好等了：
	    while (!ready) {  
	      try {  
	        wait();  
	      } catch (InterruptedException e) {  
	      }  
	    }  
	    System.out.println("蛋糕没做好，就只好等了：!!!!----------------------------------------------");
	    //等做好后才能取到   Data：返回数据的接口；FutureData：Future数据，构造很快，但是是一个虚拟的数据，需要装配RealData；
	    return realdata.getContent();  
	  }  
}
