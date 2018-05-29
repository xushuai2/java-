package demo.common.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil{
	private static final Log logger = LogFactory.getLog(FtpUtil.class);
	private static FTPClient ftpClient = new FTPClient();
	
	public FtpUtil(){
		
	}
	/**
	 * 从FTP下载文件，并写入本地操作
	 * @param ip   Ftp服务器IP
	 * @param port Ftp服务器端口
	 * @param user 用户名
	 * @param password 密码
	 * @param remotefilename 下载的文件路径
	 * @param localfilename  本地文件路径
	 * @param fileType 文件类型（分回单导入文件下载（receipt）和socket返回信息文件下载（socket））
	 */
	public static void ftpDownloadWrite(String ip,Integer port,String user,String password,String remotefilename,String localfilename,String filename) throws Exception{
		logger.info("调用FTP连接的方法，传入的参数ip:"+ip+",port:"+port+",user:"+user+",password:"+password);
		FtpUtil.loginFtp(ip, port, user, password);
		logger.info("FTP连接方法调用结束");
		logger.info("调用isFilename方法，传入的参数remotefilename:"+remotefilename+",localfilename:"+localfilename+",filename:"+filename);
		FtpUtil.isFilename(remotefilename, localfilename , filename);
		logger.info("isFilename方法调用结束");
		logger.info("开始调用关闭FTP的方法");
		FtpUtil.closeFtp();
		logger.info("关闭FTP方法调用结束");
	}
	/**
	 * 连接FTP服务器
	 * @param ip       	//FTP服务器ip地址
	 * @param port	   	//FTP端口
	 * @param user	   	//用户名
	 * @param password	//登录密码
	 */
	public static void loginFtp(String ip, Integer port,String user, String password) throws Exception{
		logger.info("开始调用loginFtp方法");
		int reply;
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		try {
			//连接
			ftpClient.connect(ip, port);
			// 登录
			ftpClient.login(user, password);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.info("连接失败！！！");
				ftpClient.disconnect();
			} else {
				logger.info("连接成功!!!");
			}
		} catch (SocketException e) {
			logger.info("出现异常："+e);
			e.printStackTrace();
            throw new Exception("登陆FTP发生异常",e);
		} catch (IOException e) {
			logger.info("出现异常："+e);
			e.printStackTrace();
            throw new Exception("连接FTP发生异常",e);
		}
		logger.info("loginFtp方法调用结束");
	}
	/**
	 * 判断是不是文件方法
	 * @param remotefilename FTP服务器文件名
	 * @param localfilename	本地生成的文件名
	 */
	public static void isFilename(String remotefilename,String localfilename,String filename) throws Exception {
		logger.info("开始调用isFilename方法");
		try {
			if (ftpClient.changeWorkingDirectory(remotefilename)) {// 判断文件夹是不是存在
				// 如果文件夹存在，获取文件夹里的所有文件，存放在数组里
				FTPFile[] fs = ftpClient.listFiles();
				logger.info(fs.length);
				boolean flag = false;		//用来判断是否找到文件
				for (int i = 0; i < fs.length; i++) {
					if(fs[i] == null)continue;
					if (fs[i].isFile()) {
						if(new String(fs[i].getName().getBytes("iso-8859-1"), "utf-8").equals(filename)){
							downloadFile(remotefilename + fs[i].getName(),localfilename + "/" + new String(fs[i].getName().getBytes("iso-8859-1"), "utf-8"),localfilename);
							logger.info(remotefilename + new String(fs[i].getName().getBytes("iso-8859-1"), "utf-8")+"是目标文件，已下载");
							flag = true;
							break;
						}
						if(new String(fs[i].getName().getBytes("iso-8859-1"), "GBK").equals(filename)){
							downloadFile(remotefilename + fs[i].getName(),localfilename + "/" + new String(fs[i].getName().getBytes("iso-8859-1"), "GBK"),localfilename);
							logger.info(remotefilename + new String(fs[i].getName().getBytes("iso-8859-1"), "GBK")+"是目标文件，已下载");
							flag = true;
							break;
						}
						if(fs[i].getName().equals(filename)){
							downloadFile(remotefilename + fs[i].getName(),localfilename + "/" + fs[i].getName(),localfilename);
							logger.info(remotefilename + fs[i].getName()+"是目标文件，已下载");
							flag = true;
							break;
						}
//						else{
//							logger.info(remotefilename + "/" + new String(fs[i].getName().getBytes("iso-8859-1"), "GBK")+"是文件，但不是目标文件");
//						}
					}
				}
				if(!flag){
					logger.info(remotefilename + filename+"文件不存在");
		            throw new Exception(remotefilename+filename+"文件不存在");
				}
			}else{
				logger.info("FTP服务器上不存在此目录："+remotefilename);
	            throw new Exception("FTP服务器上不存在此目录："+remotefilename);
			}
		} catch (Exception e) {
			logger.info("出现异常："+e);
			e.printStackTrace();
            throw new Exception("查找"+filename+"文件发生异常",e);
		}
		logger.info("isFilename方法调用结束");
	}
	/**
	 * 读取FTP服务器文件，并写入本地操作
	 * @param remotefilename FTP服务器文件名
	 * @param localfilename	本地生成的文件名
	 * @param mkdirName 本地生成的文件夹
	 */
	private static void downloadFile(String remotefilename,String localfilename,String mkdirName) throws Exception {
		logger.info("开始调用downloadFile方法,remotefilename:"+remotefilename+",localfilename:"+localfilename+",mkdirName:"+mkdirName);
		InputStream ins = null;
		File file = null;
		FileOutputStream os = null;
		try {
			ins = ftpClient.retrieveFileStream(remotefilename);
			//创建文件夹
			mkdir(mkdirName);
			file = new File(localfilename);
			os = new FileOutputStream(file);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = ins.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			os.close();
			ins.close();
			// 调用完之后必须调用completePendingCommand释放,否则FTP会断开连接
			ftpClient.completePendingCommand();
			logger.info("数据下载成功！！！");
		} catch (IOException e) {
			logger.info("出现异常："+e);
			e.printStackTrace();
            throw new Exception("数据下载发生异常发生异常",e);
		}
		logger.info("downloadFile方法调用结束");
	}

	/**
	 * 关闭FTP连接
	 */
	public static void closeFtp() throws Exception{
		logger.info("开始调用closeFtp方法");
		if (ftpClient.isConnected()) {
			try {
				// 关闭FTP服务器连接
				ftpClient.disconnect();
				logger.info("关闭连接！！！");
			} catch (IOException ioe) {
				logger.info("出现异常："+ioe);
				ioe.printStackTrace();
	            throw new Exception("关闭FTP服务器连接发生异常",ioe);
			}
		}
		logger.info("closeFtp方法调用结束");
	}

	
	/**
	 * 创建新文件夹
	 * @param mkdirName
	 */
	public static void mkdir(String mkdirName) throws Exception{
		logger.info("开始调用mkdir方法");
		try{
            File dirFile = new File(mkdirName);     //mkdirName为传建文件夹路径
            // 创建文件夹        
            if (!dirFile.exists()) {            
            	dirFile.mkdirs(); 
            	logger.info("文件夹创建成功");
            }       
        }catch(Exception ex){
        	logger.info("文件夹创建发生异常："+ex.getMessage());
            ex.printStackTrace();
            throw new Exception("文件夹创建发生异常",ex);
        }
		logger.info("mkdir方法调用结束");
	}


}
