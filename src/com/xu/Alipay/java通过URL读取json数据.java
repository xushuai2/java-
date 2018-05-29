package com.xu.Alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class java通过URL读取json数据 {

	public static void main(String[] args) {
		String url = "http://api.map.baidu.com/telematics/v3/weather?location=%E6%88%90%E9%83%BD&output=json&ak=rnm8udmHdWaHFWZTO2tuTiG8";  
        //String url = "http://www.kuaidi100.com/query?type=yunda&postid=1201386764793";  
        String json = loadJson(url);  
        System.out.println(json);  

	}
	
	public static String loadJson (String url) {  
        StringBuilder json = new StringBuilder();  
        try {  
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection();  
            /*然后BufferedReader 就是比InputStreamReader 更高级的 更粗点的管道 可以嵌套在InputStreamReader的外面 从而实现缓冲功能 
                            并且可以用外层管道的readLine()方法读取一行数据
            new BufferedReader(new InputStreamReader());
                           构造一个字符流的缓存，里面存放在控制台输入的字节转换后成的字符。
            new InputStreamReader(System.in)这个对象是字节流通向字符流的桥梁
            */
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));  
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            in.close();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return json.toString();  
    }  

}
