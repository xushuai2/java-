package demo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.Entity.Car;

public class AnnoIoCTest {
	public static void main(String[] args) {     
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
		Car hs = (Car)ctx.getBean("car");
		hs.start();   
    }    
}
