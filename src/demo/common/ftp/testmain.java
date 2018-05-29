package demo.common.ftp;

public class testmain {

	public static void main(String[] args) {
		try {
			//ftphelp.downFile("127.0.0.1",21,"shuai","123","E:\\FTP\\Message\\message.txt","message.txt","E:\\");
			ftphelp.downFile("11.0.169.59",21,"bapftp","bapftp","file\\mxfile\\A0020009_0602094155.txt","message0000.txt","E:\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
