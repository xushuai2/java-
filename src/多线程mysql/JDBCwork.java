package 多线程mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

class JDBCwork extends Thread
{
    private CountDownLatch cnt ;
    private Connection conn;
    private Statement st;
    public JDBCwork(){}
    public JDBCwork(CountDownLatch cnt)
    {
        this.cnt = cnt;
    }
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + " is running...");
        try
        {
            this.connectDB();
             
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is done...");
        cnt.countDown();
    }
    public synchronized void connectDB() throws SQLException
    {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/erp2";
        String usr = "root";
        String psw = "7857"; 
         
        try
        {
            Class.forName(driver);
        } 
        catch (ClassNotFoundException e1) 
        {
            System.out.println("加载驱动失败.");
            e1.printStackTrace();
        }
        System.out.println("MySQL JDBC Driver Registered!");
        try
        {
            conn = (Connection) DriverManager.getConnection(url, usr, psw);
        } 
        catch (SQLException e) 
        {
         
            System.out.println("connection failed .");
            e.printStackTrace();
        }
        System.out.println("connected!");
        try
        {
            st = (Statement) conn.createStatement();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        String sql = "select name from sysuser where id>0 ";
        ResultSet rt = st.executeQuery(sql);
        System.out.println(Thread.currentThread().getName() + " is querying...");
        while(rt.next())
        {
            String name = rt.getString("name");
            System.out.println( "query result: " + name); 
            String sql2 = "update sysuser set status = 2 where name = '" +name+"'";
            int u = st.executeUpdate(sql2);
            System.out.println( "u=" + u); 
        }
        rt.close();
        
        
        
        conn.close();
    }
     
}
