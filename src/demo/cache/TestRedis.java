package demo.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRedis {
	public static void main(String[] args) throws InterruptedException {
        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //这里已经配置好,属于一个redis的服务接口
        RedisService redisService = (RedisService) app.getBean("redisService");
        String ping = redisService.ping();//测试是否连接成功,连接成功输出PONG
        System.out.println(ping);
        test2(redisService);
        //清空reids所有数据
        //redisService.flushDB();
    }
	
	/**  Hash 操作   
	 * 
	 * @param redisService
	 */
	public static void test2(RedisService redisService){
	   //HMSET key field value [field value ...] 同时将多个field - value(域-值)对设置到哈希表key中。
	   /*Map<String, String> m1 = new HashMap<String, String>();
	   m1.put("age", "11");
	   m1.put("sf", "湖北");
       redisService.setMap("hash", m1);*/
       
       //HSET key field value将哈希表key中的域field的值设为value。   
       redisService.getJedis().hset("website", "google", "www.google.cn");   
       redisService.getJedis().hset("website", "baidu", "www.baidu.com");   
       redisService.getJedis().hset("website", "sina", "www.sina.com");  
       //dbsize
       long dbSizeEnd = redisService.dbSize();
       System.out.println(dbSizeEnd);
       
       //HGET key field返回哈希表key中给定域field的值。   
       System.out.println(redisService.getJedis().hget("hash", "age"));   
          
       //HMGET key field [field ...]返回哈希表key中，一个或多个给定域的值。   
       List list = redisService.getJedis().hmget("website","google","baidu","sina");   
       for(int i=0;i<list.size();i++){   
           System.out.println(list.get(i));   
       }   
          
       //HGETALL key返回哈希表key中，所有的域和值。   
       Map<String,String> map = redisService.getJedis().hgetAll("hash");   
       for(Map.Entry entry: map.entrySet()) {   
            System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");   
       }   
	}
	
	public static void test3(RedisService redisService){
		 List<String> rsmap = redisService.getJedis().hmget("mxu", "age", "sf");
		 System.out.println(rsmap); 
	}
	/** 
	* jedis操作List 
	* 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
	*/  
	public static void test4(RedisService redisService){
		for(int i=0;i<200;i++){
			 redisService.getJedis().lpush("java framework","spring"+i);  
		}
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
		System.out.println(redisService.getJedis().lrange("java framework",0,-1));  
	}
	/** 
	* jedis操作List 
	* 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
	*/  
	public static void test5(RedisService redisService){
		/*for(int i=0;i<200;i++){
			 redisService.getJedis().sadd("javaset","set"+i);  
		}*/
		System.out.println(redisService.getJedis().smembers("javaset"));//获取所有加入的value  
	}
	
	/** 
	* jedis操作List 
	* 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
	* lpush 左侧
	* rpush 右侧
	*/  
	public static void test6(RedisService redisService){
		for(int i=5;i<7;i++){
			 redisService.getJedis().rpush("java framework","springRight"+i);  
		}
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
		System.out.println(redisService.getJedis().lrange("java framework",0,-1));  
	}
	
	/** 
	* jedis操作List 
	* 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
	*/  
	// 修改列表中指定下标的值
	public static void test7(RedisService redisService){
		redisService.getJedis().lset("java framework", 2, "xiugaispringRight2");
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
		System.out.println(redisService.getJedis().lrange("java framework",0,5));  
		// 获取列表指定下标的值 
        System.out.println("获取下标为2的元素："+redisService.getJedis().lindex("java framework", 2)+"\n");
	}
	
	
	public static void test1(RedisService redisService){
		 //首先,我们看下redis服务里是否有数据
        long dbSizeStart = redisService.dbSize();
        System.out.println(dbSizeStart);

        redisService.set("username", "oyhk");//设值(查看了源代码,默认存活时间30分钟)
        String username = redisService.get("username");//取值 
        System.out.println(username);
        
        
        redisService.set("username1", "oyhk1", 1);//设值,并且设置数据的存活时间(这里以秒为单位)
        String username1 = redisService.get("username1");
        System.out.println(username1);
        
        
        //Thread.sleep(2000);//我睡眠一会,再去取,这个时间超过了,他的存活时间
        String liveUsername1 = redisService.get("username1");
        System.out.println("liveUsername1:"+liveUsername1);//输出null

        //是否存在
        boolean exist = redisService.exists("username");
        System.out.println(exist);

        //查看keys
        Set<String> keys = redisService.keys("*");//这里查看所有的keys
        System.out.println("keys="+keys);//只有username username1(已经清空了)

        //删除
        redisService.set("username2", "oyhk2");
        String username2 = redisService.get("username2");
        System.out.println("username2="+username2);
        
        redisService.del("username2");
        String username2_2 = redisService.get("username2");
        System.out.println("username2_2="+username2_2);//如果为null,那么就是删除数据了

        //dbsize
        long dbSizeEnd = redisService.dbSize();
        System.out.println(dbSizeEnd);
	}

	
}
