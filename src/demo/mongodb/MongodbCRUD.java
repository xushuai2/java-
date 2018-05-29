package demo.mongodb;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import demo.Entity.TUser; 
public class MongodbCRUD {
	 private static Mongo m = null;  
	    private static DB db = null;  
	    //数据集合名称  
	    private static final String COLLECTION_NAME = "movie";  
	  
	    /* 
	     * 测试java处理mongodb的增、删、改、查操作 
	     */  
	    public static void main(String[] args) throws UnknownHostException {  
	        //获取数据库连接  
	        startMongoDBConn();  
	        //保存数据  
	        //createColData();  
	        //读取数据  
	        //readColData();  
	        //更新数据  
	        //updateColData();  
	        //读取数据  
	        readColData();  
	        //删除数据  
	        //deleteColData();  
	        //读取数据  
	        //readColData();  
	        //删除数据集  
	        //db.getCollection(COLLECTION_NAME).drop();  
	        //关闭数据库连接  
	        //stopMondoDBConn();  
	    }  
	    
	    
	    
	    public static int getSequence(String tableName){
	    	DBCollection dbCol = db.getCollection("sequence");
	        DBObject query = new BasicDBObject();
	        query.put("coll_name", tableName);
	        DBObject newDocument =new BasicDBObject();
	        newDocument.put("$inc", new BasicDBObject().append("cnt", 1));
	        DBObject ret = dbCol.findAndModify(query, newDocument);
	        if (ret == null){
	            return 0;
	        }else{
	            return (Integer)ret.get("cnt") + 1;
	        }
	    }
	    
	    
	    public static void insertColData(TUser u){  
	        DBCollection dbCol = db.getCollection(COLLECTION_NAME);  
	        System.out.println("向数据集中插入数据开始：");  
	        BasicDBObject doc1 = new BasicDBObject();  
	        doc1.put("name", u.getName());  
	        doc1.put("password", u.getPassword());  
	        doc1.put("status", u.getStatus());  
	        doc1.put("id", u.getId());
	        dbCol.save(doc1);
	        System.out.println("向数据集中插入数据完成");  
	    }  
	    
	    
	      
	    /** 
	     * 数据插入 
	     * *测试数据： 
	     * 【name:小李、age:30、address:江苏南京】 
	     * 【name:小张、age:25、address:江苏苏州】 
	     * @return  
	     */  
	    private static void createColData(){  
	        DBCollection dbCol = db.getCollection(COLLECTION_NAME);  
	        System.out.println("向数据集中插入数据开始：");  
	        List<DBObject> dbList = new ArrayList<DBObject>();  
	        
	        BasicDBObject doc1 = new BasicDBObject();  
	        doc1.put("name", "小李333");  
	        doc1.put("age", 30);  
	        doc1.put("address", "江苏南京");  
	        dbList.add(doc1);  
	          
	        BasicDBObject doc2 = new BasicDBObject();  
	        doc2.put("name", "小张3333");  
	        doc2.put("age", 25);  
	        doc2.put("address", "江苏苏州");  
	        dbList.add(doc2);  
	          
	        //dbCol.insert(dbList);  
	        BasicDBObject doc3 = new BasicDBObject();  
	        doc3.put("name", "李阳");  
	        doc3.put("age", 30);  
	        doc3.put("address", "江苏苏州");  
	        doc3.put("学生", dbList );  
	        //dbCol.save(doc3);
	        
	        long start = new Date().getTime();
	        for(int i=0;i<10000;i++){
	        	BasicDBObject t = new BasicDBObject();  
		        t.put("name", "shuai"+i);  
		        t.put("age",i+9);  
		        t.put("address", "江苏苏州");  
		        dbCol.save(t);
	        }
	        long stop = new Date().getTime();
	        
	        long time = stop - start;
	        System.out.println("向数据集中插入数据完成！用时："+time);  
	        System.out.println("------------------------------");  
	    }  
	      
	    /** 
	     * 数据读取 
	     */  
	    private static void readColData(){  
	        DBCollection dbCol = db.getCollection(COLLECTION_NAME);  
	        DBCursor ret = dbCol.find();  
	        System.out.println("从数据集中读取数据：");  
	        while(ret.hasNext()){  
	            BasicDBObject bdbObj = (BasicDBObject) ret.next();  
	            if(bdbObj != null){  
	                System.out.println("name:"+bdbObj.getString("name"));  
	                System.out.println("age:"+bdbObj.getInt("age"));  
	                System.out.println("address:"+bdbObj.getString("address"));  
	            }  
	        }  
	    }  
	      
	    /** 
	     * 数据更新 
	     * update(q, o, upsert, multi) 
	     * update(q, o, upsert, multi, concern) 
	     * update(arg0, arg1, arg2, arg3, arg4, arg5) 
	     * updateMulti(q, o) 
	     */  
	    private static void updateColData(){  
	        System.out.println("------------------------------");  
	        DBCollection dbCol = db.getCollection(COLLECTION_NAME);  
	        DBCursor ret = dbCol.find();  
	        BasicDBObject doc = new BasicDBObject();  
	        BasicDBObject res = new BasicDBObject();  
	        res.put("age", 40);  
	        System.out.println("将数据集中的所有文档的age修改成40！");  
	        doc.put("$set", res);  
	        dbCol.update(new BasicDBObject(),doc,false,true);  
	        System.out.println("更新数据完成！");  
	        System.out.println("------------------------------");  
	    }  
	      
	    /** 
	     * 数据删除 
	     */  
	    private static void deleteColData(){  
	        System.out.println("------------------------------");  
	        DBCollection dbCol = db.getCollection(COLLECTION_NAME);  
	        System.out.println("删除【小李】！");  
	        BasicDBObject doc = new BasicDBObject();  
	        doc.put("name", "小李");  
	        dbCol.remove(doc);  
	        System.out.println("------------------------------");  
	    }  
	      
	    /** 
	     * 关闭mongodb数据库连接 
	     */  
	    private static void stopMondoDBConn(){  
	        if (null != m) {  
	            try  
	            {  
	                m.close();  
	            } catch(Exception e) {  
	                e.printStackTrace();  
	            }  
	            m = null;  
	            db = null;  
	        }  
	    }  
	      
	    /** 
	     * 获取mongodb数据库连接 
	     */  
	    public static void startMongoDBConn() throws UnknownHostException{  
	        try {  
	            //Mongo(p1, p2):p1=>IP地址     p2=>端口  
	            m = new Mongo("127.0.0.1", 27017);  
	            //根据mongodb数据库的名称获取mongodb对象  
	            db = m.getDB("xumongodb");  
	            //校验用户密码是否正确  
	            /*if (!db.authenticate("sdap", "sdap123".toCharArray())){  
	                System.out.println("连接MongoDB数据库,校验失败！");  
	            }else{  
	                System.out.println("连接MongoDB数据库,校验成功！");  
	            } */ 
	        } catch (MongoException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
