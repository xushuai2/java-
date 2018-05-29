package synchronized和lock;

public class Produce implements Runnable {
    @Override
    public void run() {
           int count = 10;
           while(count > 0) {
                synchronized (Test.obj) {
                    System.out.print("A"+"-");
                    count --;
                    Test.obj.notify();
                    try {
                          Test.obj.wait();
                    } catch (InterruptedException e) {
                          e.printStackTrace();
                    }
               }
               
          }

    }

}
