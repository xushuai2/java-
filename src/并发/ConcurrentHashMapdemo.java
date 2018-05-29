package 并发;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapdemo {
	static ConcurrentHashMap<Long, String> conMap = new ConcurrentHashMap<Long, String>();

    public static void main(String[] args) throws InterruptedException {
        for (long i = 0; i < 20; i++) {
            conMap.put(i, i + "");
        }
        
        for (Entry<Long, String> entry : conMap.entrySet()) {
            long key = entry.getKey();
            if (key < 10) {
                conMap.remove(key);
            }
        }
        /*HashMap或者ArrayList边遍历边删除数据会报java.util.ConcurrentModificationException异常
                  对ConcurrentHashMap边遍历边删除或者增加操作不会产生异常(可以不用迭代方式删除元素)，因为其内部已经做了维护，遍历的时候都能获得最新的值。
                  即便是多个线程一起删除、添加元素也没问题。*/
        Thread thread = new Thread(new Runnable() {
            public void run() {
                conMap.put((long) 88, "100");
                System.out.println("***************************ADD:" + 100);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        //一个线程对ConcurrentHashMap增加数据，另外一个线程在遍历时就能获得。
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
            	Iterator<Entry<Long, String>> iterator = conMap.entrySet().iterator();
                for (; iterator.hasNext();) {
                    Entry<Long, String> entry = iterator.next();
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        thread2.start();

        Thread.sleep(3000);
        System.out.println("--------");
        for (Entry<Long, String> entry : conMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
}
