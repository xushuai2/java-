package demo.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import demo.Entity.FunctionEntity;
import demo.FileScheduler.FileScheduler;
import demo.common.Constant;
import demo.common.FunctionUtils;
import demo.service.FunctionService;


public class InitListener implements ServletContextListener {
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(InitListener.class);

	@SuppressWarnings("unused")
	private FileScheduler monitor = null;
	
    public void contextInitialized(ServletContextEvent sce) {
		//设置点击量
		sce.getServletContext().setAttribute("increaseCountMap",new ConcurrentHashMap<Integer,AtomicInteger>());
		
		ServletContext application = sce.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
		//将所有菜单获取并放入全局变量内
		FunctionService functionService = (FunctionService)wac.getBean("functionService");
		List<FunctionEntity> listFunctions = null;
		try {
			listFunctions = FunctionUtils.functionLevel(functionService.getAll(),"0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//这里查出来的是所有的function 
		application.setAttribute(Constant.functions,listFunctions);
		//把所有的url放入全局变量
		HashMap<String,String> mapUrlKey = FunctionUtils.getUrlKeyMap(listFunctions);
		application.setAttribute(Constant.actions,mapUrlKey);
		
		
		
		/*一、文件路径监控*/
		/*FileSchedulerTask task = new FileSchedulerTask("E:\\resourceXu");  
        monitor = new FileScheduler();  
        monitor.schedule(task, new TimeStep());*/  
		
		/*二、定时任务执行*/
		/*try {         
	        // 1、创建一个JobDetail实例，指定Quartz  
	        JobDetail jobDetail = JobBuilder.newJob(JobTask.class)   // 任务执行类      
	        .withIdentity("job1_1", "jGroup1")// 任务名，任务组              
	        .build();     
	        //2、创建Trigger  
	         SimpleScheduleBuilder builder=SimpleScheduleBuilder.simpleSchedule()  
	        .withIntervalInSeconds(5)       //设置间隔执行时间        
	        .repeatSecondlyForTotalCount(5);//设置执行次数  
	        Trigger trigger=TriggerBuilder.newTrigger().withIdentity(  
	                "trigger1_1","tGroup1").startNow().withSchedule(builder).build();  
	    	Trigger trigger=TriggerBuilder.newTrigger().withIdentity(  
	    	                "trigger1_1","tGroup1").startNow().withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();  
	    	//3、创建Scheduler  
	        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();    
	        //4、调度执行  
	        scheduler.scheduleJob(jobDetail, trigger);    
	       // scheduler.start();    
	    } catch (SchedulerException e) {  
	        e.printStackTrace();  
	    }  
*/
    }
    
    
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext对象销毁");
      /*  WebApplicationContext  webApplicationContext= ContextLoader.getCurrentWebApplicationContext();*/
        
        
        //获取业务层service Bean
       /* UserServiceImpl userService = (UserServiceImpl)webApplicationContext.getBean("userService");
        userService.updateRateUtilization();//更新点击量
		sce.getServletContext().removeAttribute("increaseCountMap");//移除全局变量--点击量
*/		
		//monitor.cancle();
		//sce.getServletContext().log("定时器销毁");  
    }
}
