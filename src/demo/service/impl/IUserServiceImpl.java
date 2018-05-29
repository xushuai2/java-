package demo.service.impl;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import demo.Entity.AppStatistics;
import demo.Entity.Dcyjmessage;
import demo.Entity.TUser;
import demo.dao.AppStatisticsMapper;
import demo.dao.TUserMapper;
import demo.exception.DAOException;
import demo.mongodb.MongodbCRUD;
import demo.service.IUserService;
@Service
public class IUserServiceImpl implements IUserService {
	@Resource
	private TUserMapper userclientDao;
	
	@Resource
	private AppStatisticsMapper appstatisticsmapper;
	
	
	
	protected static Logger logger = LogManager.getLogger(IUserServiceImpl.class); 
	
	private ThreadLocal<String> contentTL = new ThreadLocal<String>();
	
	private String content;
	
    public void saveUser(TUser user2){  
    	try {
			MongodbCRUD.startMongoDBConn();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	int id = MongodbCRUD.getSequence("movie");
    	user2.setId(id);
    	MongodbCRUD.insertColData(user2);
    }  
    
    public TUser findUser(TUser tuser){
    	tuser = userclientDao.selectTuser(tuser);
		return tuser;  
    } 
	
	
	@Cacheable(value = "serviceCache")  
	public TUser getUserById(int id) {
		System.out.println("not cache--------------------------");
		String a = this.contentTL.get();
		a+="A";
		this.contentTL.set(a);  //不会累加   区分了线程
		System.out.println("结束contentTL："+a);
		content+="H";
		System.out.println("content:"+content);
		return this.userclientDao.selectByPrimaryKey(id);
	}
	
	
	
	/* rollbackFor
	 * 该属性用于设置需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，则进行事务回滚。例如：
	指定单一异常类：@Transactional(rollbackFor=RuntimeException.class)
	指定多个异常类：@Transactional(rollbackFor={RuntimeException.class, Exception.class})
	*
	* @Transactional(readOnly=true)该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。
	*
	*/
	
	@CacheEvict(value="serviceCache",allEntries=true)  
	@Transactional(rollbackFor={Exception.class})  
	public void gettestUserById(int id){
		try {          
	        // 记录info级别的信息  
	        logger.info("info This is info message.");  
	        logger.error("error This is error message."); 
	        logger.debug("This is debug message.");
		    userclientDao.update();     
	        userclientDao.insert();
	     } catch (Exception e) {    
	    	logger.info("异常，异常信息："+e);
	        throw new RuntimeException();         
	     }       
	}  
	
	
/*	updateCourseClickRate()对应的sql：
	update t_course set click_rate
	 = #{clickRate} + IFNULL(click_rate,0) where id = #{courseId}
	最后使用定时任务，每10分钟一次，更新点击量
	CourseServiceImpl.java-->updateRateUtilization()*/
	
	
	
	public void updateRateUtilization(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ConcurrentHashMap<Integer,AtomicInteger>  countMap =(ConcurrentHashMap<Integer,AtomicInteger>)webApplicationContext .getServletContext().getAttribute("increaseCountMap");
		Iterator it =countMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Integer,AtomicInteger> me = (Map.Entry<Integer,AtomicInteger>)it.next();
			//更新数据库
			//userclientDao.updateCourseClickRate(me.getKey(),me.getValue().intValue());
		}
		webApplicationContext.getServletContext().setAttribute("increaseCountMap",
		new ConcurrentHashMap<Integer,AtomicInteger>());
	}

	@Override
	public int saveDcyjmessage(Dcyjmessage dc){
		int num = 0;
		num = userclientDao.insertDC(dc);
		return num;
	}

	@Override
	public int savetuser(TUser tuser){
		int num = 0;
		num = userclientDao.insertTUser(tuser);
		return num;
	}

	@Override
	public List<AppStatistics> selectAPP(String type) {
		List<AppStatistics> a = appstatisticsmapper.getBarChart(type);
		return a;
	}

	@Override
	public int updatedxc(int id) {
		AppStatistics sd = new AppStatistics();
		sd.setId(id);
		int num = 0;
		try {
			num = appstatisticsmapper.updateByDuoXC(sd);
		} catch (DAOException e) {
			logger.info("异常，异常信息");
			e.printStackTrace();
		}
		return num;
	}

}
