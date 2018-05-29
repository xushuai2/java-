package com.xu.Spider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownLoadPic {
	
	public void getDoc() throws IOException{
		//以网易为例子
		File f = new File("E://imgs");
		if(!f.exists()){
			f.mkdirs();
		}
		Document doc = Jsoup.connect("http://www.163.com/").get();
	    //获取后缀为png和jpg的图片的元素集合
	    Elements pngs = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
	    //遍历元素
	    int num = 0;
	    for(Element e : pngs){
	    	num++;
	    	if(num>100){
	    		break;
	    	}
	        String src=e.attr("src");//获取img中的src路径
	        //获取后缀名
	        String imageName = src.substring(src.lastIndexOf("/") + 1,src.length());
	        //连接url
	        URL url = new URL(src);
	        URLConnection uri=url.openConnection();
	        //获取数据流
	        InputStream is=uri.getInputStream();
	        //写入数据流
	        OutputStream os = new FileOutputStream(new File("E://imgs", imageName)); 
	        byte[] buf = new byte[1024];
	        int l=0;
	        while((l=is.read(buf))!=-1){
	        	os.write(buf, 0, l); 
	        }
	    }
	}
	public static void main(String[] args) throws IOException { 
			 DownLoadPic d =new DownLoadPic();
			 d.getDoc();
			 System.out.println("over");
	} 
}
