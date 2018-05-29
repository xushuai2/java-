package demo.common;

import java.io.File;

public class FileDemoRename {
	public static void main(String[] args) {
	      
	      File f = null;
	      File f1 = null;
	      boolean bool = false;
	      
	      try{      
	         
	         f = new File("C:/testshuai.txt");
	         f1 = new File("D:/test.xls");
	         // rename file
	         bool = f.renameTo(f1);
	         
	         System.out.print("File renamed? "+bool);
	         
	      }catch(Exception e){
	         e.printStackTrace();
	      }
	   }
}
