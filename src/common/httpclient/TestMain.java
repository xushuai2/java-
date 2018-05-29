package common.httpclient;

import java.util.HashMap;  
import java.util.Map;  
//对接口进行测试  
public class TestMain {  
    private String url = "http://127.0.0.1:8089/";  
    private String charset = "utf-8";  
    private HttpClientUtil httpClientUtil = null;  
      
    public TestMain(){  
        httpClientUtil = new HttpClientUtil();  
    }  
      
    public void test(){  
        String httpOrgCreateTest = url + "jsyj/login";  
        Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("userid","000000");  
        createMap.put("password","111111");  
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
    
    public void test2(){  
        String httpOrgCreateTest = url + "testsimple/user/getTest";  
        Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("userId","1");  
        createMap.put("name","shuai");  
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
      
    public static void main(String[] args){  
        TestMain main = new TestMain();  
        main.test2();  
    }  
}  
