package 算法题;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class textJiexi1and2to3 {
	public static void main(String[] args) {
		//test1();
		test2("hello world hello hi");
	}
	
	public static void test2(String ss){
		//给定String,求此字符串的单词数量。字符串不包括标点，大写字母。例如 String str="hello world hello hi";单词数量为3，分别是：hello world hi。
		String[] s = ss.split(" ") ;
		HashMap<String,String> m=new HashMap<String,String>();
		int num = 0;
		for(String s1:s){
			if(!m.containsKey(s1)){
				m.put(s1, "true");
				num++;
				System.out.print(s1 +"  ");
			}
		}
		System.out.println();
		System.out.println(num);
	}
	
	public static void test1(){
		//将text1.txt文件中的单词与text2.txt文件中的单词交替合并到text3.txt文件中。
		//text1.txt文件中的单词用回车符分隔，text2.txt文件中用回车或空格进行分隔。
		FileWriter c = null;
		try {
			String[] a = getArrayByFile("D://text1.txt",new char[]{'\n'});
			String[] b = getArrayByFile("D://text2.txt",new char[]{'\n',' '});
			c = new FileWriter("D://text3.txt");
			int aindex = 0;
			int bindex = 0;
			while(aindex<a.length){
				c.write(a[aindex++]);
				if(bindex<b.length){
					c.write(b[bindex++]);
				}
			}
			while(bindex<b.length){
				c.write(b[bindex++]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String[] getArrayByFile(String filename,char[] seperators) throws Exception{  
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));     
        StringBuffer  lines = new StringBuffer();
        String line = "";
        while ((line =  br.readLine())!=null) {
        	lines.append(line +"\n");
		}
        System.out.println(lines.toString());
        String regex = null;  
        if(seperators.length >1 ){  
            regex = "" + seperators[0] + "|" + seperators[1];  
        }else{  
            regex = "" + seperators[0];  
        }  
        return  lines.toString().split(regex);  
    }  
}
