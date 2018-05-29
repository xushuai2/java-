package demo.common.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	public static String  getKey(String key){
		String path = PropertiesUtil.class.getClassLoader().getResource("xu.properties").getPath();
		System.out.println("-----------"+path);
		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
