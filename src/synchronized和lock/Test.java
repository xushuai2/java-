package synchronized和lock;
public class Test {
/*	这里使用static obj作为锁的对象，
 * 当线程Produce启动时（假如Produce首先获得锁，则Consumer会等待），打印“A”后，会先主动释放锁，然后阻塞自己。
 * 
	Consumer获得对象锁，打印“B”，然后释放锁，阻塞自己，
	那么Produce又会获得锁，然后...一直循环下去，直到count = 0.这样，使用Synchronized和wait()以及notify()就可以达到线程同步的目的。
	
	*一直很喜欢synchronized，因为使用它很方便。比如，需要对一个方法进行同步，那么只需在方法的签名添加一个synchronized关键字。
	*
	除了wait()和notify()协作完成线程同步之外，使用Lock也可以完成同样的目的。
	ReentrantLock 与synchronized有相同的并发性和内存语义，还包含了中断锁等候和定时锁等候，
	意味着线程A如果先获得了对象obj的锁，那么线程B可以在等待指定时间内依然无法获取锁，那么就会自动放弃该锁。
	*/
    public static final Object obj = new Object();
    
    public static void main(String[] args) {
          
           new Thread( new Produce()).start();
           new Thread( new Consumer()).start();
          
    }
}
