package Java多线程实现异步;


public class Host {
	/*顾客定蛋糕后，蛋糕店做了什么：*/
	public Data request(final int count, final char c) {  
	    System.out.println("request(" + count + ", " + c + ") BEGIN");  
	    // (1) 建立FutureData的实体  
	    final FutureData future = new FutureData();  
	    // (2) 为了建立RealData的实体，启动新的线程  
	    new Thread() {                     
	      public void run() {  
	       //在匿名内部类中使用count、future、c。            
	        RealData realdata = new RealData(count, c);  
	        future.setRealData(realdata);  
	      }                         
	    }.start();                       
	    System.out.println("request(" + count + ", " + c + ") END");  
	    // (3) 取回FutureData实体，作为传回值  
	    return (Data) future;  
	  }  
}
