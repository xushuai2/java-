package demo.ScheduledExecutor;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class Scheduledinsert {
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
        Connection conn= null;
        Statement st= null;
        ResultSet rs = null;
        try
        {
        	Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, usr, psw);
            st = (Statement) conn.createStatement();
            for(int i=0;i<3;i++){
            	String sql = "insert into  sysuser (name,password,status) values (?,?,?) ";
            	PreparedStatement pst = conn.prepareStatement(sql);
            	pst.setString(1,"xu"+System.currentTimeMillis());
            	pst.setString(2,"123");
            	pst.setInt(3, 2);;
            	pst.executeUpdate();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }finally{
            st.close();
            conn.close();
        }
	 }
}
