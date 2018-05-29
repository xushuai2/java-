package com.yanqiong.spider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*我这里给出的是Java版本的爬虫程序。
 * 为了节省时间和空间，我把程序限制在只扫描本博客地址下的网页
 * http://johnhany.net/
 * （也就是http://johnhan.net/但不包括http://johnhany.net/wp-content/下的内容），
 * 并从网址中统计出所用的所有标签。
 * 只要稍作修改，去掉代码里
 * 的限制条件就能作为扫描整个网络的程序使用。
 * 或者对输出格式稍作修改，
 * 可以作为生成博客sitemap的工具。*/
public class crawler {
	public static void main(String[] args)  throws Exception {
		//String frontpage = "http://item.jd.com/1593512.html";
		String frontpage = "http://johnhany.net/";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl = "jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(dburl, "root", "7857");
			System.out.println("***********************connection built");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = null;
		String url = frontpage;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		if(conn != null) {
			try {
				sql = "CREATE DATABASE IF NOT EXISTS crawler";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				
				sql = "USE crawler";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				
				sql = "create table if not exists record (recordID int(5) not null auto_increment, URL text not null, crawled tinyint(1) not null, primary key (recordID)) engine=InnoDB DEFAULT CHARSET=utf8";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				
				sql = "create table if not exists tags (tagnum int(4) not null auto_increment, tagname text not null, primary key (tagnum)) engine=InnoDB DEFAULT CHARSET=utf8";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			while(true) {
				httpGet.getByString(url,conn);
				count++;
				sql = "UPDATE record SET crawled = 1 WHERE URL = '" + url + "'";
				stmt = conn.createStatement();
				if(stmt.executeUpdate(sql) > 0) {
					sql = "SELECT * FROM record WHERE crawled = 0";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					if(rs.next()) {
						url = rs.getString(2);
					}else {
						break;
					}
					if(count > 30 || url == null) {
						break;
					}
				}
			}
			conn.close();
			conn = null;
			System.out.println("---------------------Done.---count="+count);
		}
	}
}
