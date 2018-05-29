package com.yanqiong.spider;
import java.io.IOException;  

import org.apache.http.HttpEntity;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClientBuilder;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.util.EntityUtils;  
public class PageUtil {
	public static String getContent(String url){  
        HttpClientBuilder custom = HttpClients.custom();//创建httpclient  
        //通过构建器构建一个httpclient对象，可以认为是获取到一个浏览器对象  
        CloseableHttpClient build = custom.build();  
        //把url封装到get请求中  
        HttpGet httpGet = new HttpGet(url);  
        String content = null;  
        try {  
            //使用client执行get请求,获取请求的结果，请求的结果被封装到response中  
            CloseableHttpResponse response = build.execute(httpGet);  
            //表示获取返回的内容实体对象  
            HttpEntity entity = response.getEntity();  
            //解析实体中页面的内容，返回字符串形式  
            content = EntityUtils.toString(entity);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return content;  
    }   
}
