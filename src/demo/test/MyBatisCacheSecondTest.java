package demo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.Entity.BranchEntity;
import demo.exception.ServiceException;
import demo.service.BranchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-*.xml"})  
public class MyBatisCacheSecondTest
{
    private static final Log logger = LogFactory.getLog(MyBatisCacheSecondTest.class);

    @Autowired
    private  BranchService service;
    /*
     * 二级缓存测试
     */
    @Test
    public void testCache2() {
    	BranchEntity page1;
		try {
			page1 = service.findById(1);
			logger.info("1-------:"+page1.getName());
			/*try{
				Thread.sleep(11000);
			}catch(Exception e){
				System.exit(0);//退出程序
			}*/
			page1.setName("总部4");
			service.update2(page1);
			BranchEntity page2 = service.findById(1);
	        logger.info("2-------:"+page2.getName());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
        
    }   

}
