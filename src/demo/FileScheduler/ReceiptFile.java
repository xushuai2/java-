package demo.FileScheduler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

public class ReceiptFile {
	private static final Log logger = LogFactory.getLog(ReceiptFile.class);
	public static boolean analytical(String filePath,String backupPath) throws IOException{
		logger.info("开始解析文件："+filePath);
		boolean flag = true;
		
		File file = new File(filePath);
		String filename = file.getName();
		InputStreamReader ips = null;
		BufferedReader file1 = null;
		
		int num=0,effNum = 0;
		String fileType = "";
		boolean FlagRe = false;
		try {
			//FileInputStream创建字节输入流 
			//InputStreamReader  将字节流转换为字符流。是字节流通向字符流的桥梁。如果不指定字符集编码，该解码过程将使用平台默认的字符编码，如：GBK。
			ips =new InputStreamReader(new FileInputStream(filePath), "GBK");
			//BufferedReader 从字符输入流中读取文本
			file1=new BufferedReader(ips);
			
			String lineText = null;
			int size = 0;
			logger.info("------------>readLine开始取数据");
			while((lineText = file1.readLine()) != null){
				num ++;
				System.out.println("文件内容读取："+lineText);
			}
		} catch (Exception e) {
			logger.info("解析回单文件出错"+e);
			e.printStackTrace();
		}finally{
			if(file1!=null){
				file1.close();
			}
			String separator = File.separator;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			backupPath = backupPath+separator+sdf.format(new Date())+separator;
			File f = new File(backupPath);
			logger.info("---移动到备份文件夹------backupPath="+backupPath);
			if(!f.exists()  && !f.isDirectory()){
				f.mkdir();
			}
			num = 1;
			if(file.renameTo(new File(backupPath+file.getName()))){
				logger.info("文件"+file.getName()+"已移动到备份文件夹");
			}else{
				logger.info("将文件"+file.getName()+"移动到备份文件夹失败");
			}
		}
		logger.info("解析文件"+file.getName()+"导入数据方法执行结束");
		return flag;
	}
	
}
