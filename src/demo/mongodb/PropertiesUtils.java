package demo.mongodb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtils {
	public static String getPropertyByName2(String path, String name) {  
        String result = "";  
        InputStream in = PropertiesUtils.class.getClassLoader()  
                .getResourceAsStream(path);  
        Properties prop = new Properties();  
        try {  
            prop.load(in);  
            result = prop.getProperty(name).trim();  
            System.out.println("name:" + result);  
        } catch (IOException e) {  
            System.out.println("读取配置文件出错");  
            e.printStackTrace();  
        }  
        return result;  
    }  
	
	
	public static Properties getPropertyByName(String path) {  
        InputStream in = PropertiesUtils.class.getClassLoader()  
                .getResourceAsStream(path);  
        Properties prop = new Properties();  
        try {  
            prop.load(in);  
        } catch (IOException e) {  
            System.out.println("读取配置文件出错");  
            e.printStackTrace();  
        }  
        return prop;  
    }  
	
	public static void main(String[] args) {
		Properties prop = getPropertyByName("mongodb.properties");
		Iterator<Entry<Object, Object>> a= prop.entrySet().iterator();
		while(a.hasNext()){
			Entry<Object, Object> entry = a.next();
			System.out.println("key= " + entry.getKey() + " /  value= " + entry.getValue());
		}
	}
}
