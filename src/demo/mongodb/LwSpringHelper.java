package demo.mongodb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware; //import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;

/**
 * 
 * @author
 */
public class LwSpringHelper implements ApplicationContextAware {

	private static ApplicationContext ctx;
	private static int currentSlave = 1;

	// private static volatile AbstractApplicationContext appContext;

	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		ctx = appContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	/**
	 * 获取dao对象
	 * 
	 * @param name
	 * @return Object Bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) ctx.getBean(name);
	}

	/**
	 * 获取dao对象
	 * 
	 * @param name
	 * @param readOnly是否是只读
	 * @param 包含uid后四位的字符串
	 * @return Object Bean
	 */
	public static Object getDAO(String name, boolean readOnly) {
		// AbstractApplicationContext ctx = appContext;

		if (!readOnly) {
			return ctx.getBean(name);
		}
		int num = currentSlave;
		if (currentSlave == 1) {
			currentSlave = 1;// 2
		} else {
			currentSlave = 1;
		}

		StringBuffer slave = new StringBuffer(name);
		slave.append("Slave");
		slave.append(num);

		return ctx.getBean(slave.toString());
	}

	/**
	 * 获取dao对象
	 * 
	 * @param name
	 * @param readOnly是否是只读
	 * @param 包含uid后四位的字符串
	 * @return Object Bean
	 */
	public static Object getDAO(String name, boolean readOnly,
			String uid_last_4str) {
		// AbstractApplicationContext ctx = appContext;

		if (!readOnly) {
			return ctx.getBean(name);
		}
		int num = currentSlave;
		if (currentSlave == 1) {
			currentSlave = 1;// 2
		} else {
			currentSlave = 1;
		}

		StringBuffer slave = new StringBuffer(name);
		slave.append("Slave");
		slave.append(num);

		return ctx.getBean(slave.toString());
	}

	public static Properties getProperties(String proName) {
		InputStream inputStream2 = LwSpringHelper.class.getClassLoader().getResourceAsStream(proName);
		Properties p = new Properties();
		try {
			p.load(inputStream2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

}
