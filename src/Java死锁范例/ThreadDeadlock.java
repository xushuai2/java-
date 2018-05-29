package Java死锁范例;

public class ThreadDeadlock {
	 
    public static void main(String[] args) throws InterruptedException {
    	//当我执行上面的程序时，就产生了输出，但是程序却因为死锁无法停止。
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
 
        Thread t1 = new Thread(new SyncThread(obj1, obj2), "t1");
        Thread t2 = new Thread(new SyncThread(obj2, obj3), "t2");
        Thread t3 = new Thread(new SyncThread(obj3, obj1), "t3");
 
        t1.start();
        Thread.sleep(5000);
        t2.start();
        Thread.sleep(5000);
        t3.start();
    }
 
}
 
class SyncThread implements Runnable{
    private Object obj1;
    private Object obj2;
 
    public SyncThread(Object o1, Object o2){
        this.obj1=o1;
        this.obj2=o2;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "  准备锁 "+obj1);
        /*这些线程以向第一个对象获取封锁这种方式运行。
                   但是当它试着向第二个对象获取封锁时，它就会进入等待状态，因为它已经被另一个线程封锁住了。
                   这样，在线程引起死锁的过程中，就形成了一个依赖于资源的循环。*/
        synchronized (obj1) {
	         System.out.println(name + " 锁定 "+obj1);
	         work();
	         System.out.println("---------------------------------------------------------");
	         System.out.println(name + "  准备锁 "+obj2);
	         synchronized (obj2) {
	            System.out.println(name + " 锁定 "+obj2);
	            System.out.println("---------------------------------------------------------");
	            work();//睡眠替代
	         }
	         System.out.println(name + "  释放 锁 "+obj2);
        }
        System.out.println(name + "  释放 锁 "+obj1);
        System.out.println("---------------------------------------------------------");
        System.out.println(name + " finished execution.");
    }
    
    
    private void work() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}