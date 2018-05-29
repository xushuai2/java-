package demo.ScheduledExecutor;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class TestScheduledThreadPoolExecutor {
	private static ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		init();
	}
	
	public static void init(){
		exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
            	long start = System.currentTimeMillis();
            	System.out.println(Thread.currentThread().getName() + " is running...**********************strat="+start);
                try {
					connectDB();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
                long stop = System.currentTimeMillis();
                long s = (stop - start)/1000;
                System.out.println(Thread.currentThread().getName() + " is done...******************************用时：（s）"+s);
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
		
		
	}
	
	
	
	 public synchronized static void connectDB() throws SQLException, ClassNotFoundException{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/erp2";
        String usr = "root";
        String psw = "7857"; 
        Queue<String> queue = new ConcurrentLinkedQueue<String>();
        Connection conn= null;
        Statement st= null;
        ResultSet rs = null;
        try
        {
        	Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, usr, psw);
            st = (Statement) conn.createStatement();
            String sql = "select name from sysuser where status = 2 limit 0,4";
            rs = st.executeQuery(sql);
            System.out.println(Thread.currentThread().getName() + " is querying...");
            while(rs.next())
            {
                String name = rs.getString("name");
                queue.add(name);
            }
            
            boolean flag = true;
            while (flag) {
            	if(queue.isEmpty()){
            		System.out.println("队列已经空");
            		flag = false;
            	}else{
            		String name = queue.poll();
                	System.out.println("队列出列："+name);
                	String sql2 = "update sysuser set status = 3 where name = ?";
                	PreparedStatement pst = conn.prepareStatement(sql2);
                	pst.setString(1,name);
                	pst.executeUpdate();
            	}
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }finally{
        	rs.close();
            st.close();
            conn.close();
        }
	 }
}
