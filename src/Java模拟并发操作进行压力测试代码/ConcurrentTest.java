package Java模拟并发操作进行压力测试代码;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;   
/*Semaphore的主要方法摘要：
 * 
 * Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。
 * 使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
 * 
 * void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。

　　void release():释放一个许可，将其返回给信号量。

　　int availablePermits():返回此信号量中当前可用的许可数。

　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
 */

public class ConcurrentTest {   
	private static int thread_num = 5;   
	private static int client_num = 100;   
	private static Map keywordMap = new HashMap();   
	/*static {   
		try {   
			InputStreamReader isr = new InputStreamReader(new FileInputStream( new File("clicks.txt")), "GBK");   
			BufferedReader buffer = new BufferedReader(isr);   
			String line = "";   
			while ((line = buffer.readLine()) != null) {   
				keywordMap.put(line.substring(0, line.lastIndexOf(":")), "");   
			}   
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
	}   */
	public static void main(String[] args) {   
		//int size = keywordMap.size();   
		// TODO Auto-generated method stub   
		ExecutorService exec = Executors.newCachedThreadPool();   
		//只能5个线程同时访问
		/*Semaphore  一个计数信号量。
		 * 从概念上讲，信号量维护了一个许可集。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。
		 * 每个 release() 添加一个许可，从而可能释放一个正在阻塞的获取者。
		 * 但是，不使用实际的许可对象，Semaphore 只对可用许可的号码进行计数，并采取相应的行动。
		 * 拿到信号量的线程可以进入代码，否则就等待。
		 * 通过acquire()和release()获取和释放访问许可。*/
		final Semaphore semp = new Semaphore(thread_num);   
		// 模拟100个客户端访问   
		for (int index = 0; index < client_num; index++) {   
			final int NO = index+1;   
			Runnable run = new Runnable() {   
				public void run() {   
					try {   
						//请求获得许可，如果有可获得的许可则继续往下执行，许可数减1。否则进入阻塞状态
						semp.acquire();   
						String host = "http://127.0.0.1:8089/jsyj/app/showBysearch";   
						/*String para = "method=getQueryResult&pageNum=1&pageSize=5&"   
						+ "queryKeyWord="   
						+ getRandomSearchKey(NO)   
						+ "&questionID=-1&questionIdPath=-1&searchType=1"   
						+ "&proLine=&proSeries=&proType=" + NO;   */
						String para = "";
						if(NO>95){
							System.out.println("Thread:" + NO+"--"+host + para);   
						}
						
						URL url = new URL(host);// 此处填写供测试的url   
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();   
						connection.setRequestMethod("POST");   
						connection.setRequestProperty("Proxy-Connection","Keep-Alive");   
						// 发送POST请求必须设置如下两行
						connection.setDoOutput(true);   
						connection.setDoInput(true);  
						
						// 获取HttpURLConnection对象对应的输出流
						PrintWriter out = new PrintWriter(connection.getOutputStream()); 
						// 发送请求参数
						out.print(para);   
						// flush输出流的缓冲
						out.flush();   
						out.close();   
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));   
						String line = "";   
						String result = "";   
						while ((line = in.readLine()) != null) {   
							result += line;   
						}   
						
						
						if(NO>95){
							System.out.println("第：" + NO + " 个线程;返回结果："+result);   
							System.out.println("线程" + Thread.currentThread().getName() + 
									"进入，当前已有" + (thread_num-semp.availablePermits()) + "个并发");
						}
						Thread.sleep((long) (Math.random()) * 1000);   
						// 访问完后，释放 ，如果屏蔽下面的语句，则在控制台只能打印200条记录，之后线程一直阻塞
						semp.release();  
						if(NO>95){
							//下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
							System.out.println("线程" + Thread.currentThread().getName() + 
									"已离开，当前已有" + (thread_num-semp.availablePermits()) + "个并发"); 
						}
					} catch (Exception e) {   
						e.printStackTrace();   
					}   
				}   
			};   
			exec.execute(run);   
		}   
		// 退出线程池   
		exec.shutdown();   
	}   
	
	
	private static String getRandomSearchKey(final int no) {   
		String ret = "";   
		int size = keywordMap.size();   
		int wanna = (int) (Math.random()) * (size - 1);   
		ret = (keywordMap.entrySet().toArray())[no].toString();   
		ret = ret.substring(0, ret.lastIndexOf("="));   
		System.out.println("\t" + ret);   
		return ret;   
	}   
}  
