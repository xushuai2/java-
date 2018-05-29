package demo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.service.IwomenService;

public class ApplicationContextTest {

	public static void main(String[] args) {
		//ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml","dao.xml"});
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/spring-*.xml");
		IwomenService iw = (IwomenService) ac.getBean("womenJapanServiceImpl");
		iw.jiehun("testdemo");
	}

}
