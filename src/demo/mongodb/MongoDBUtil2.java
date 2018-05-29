package demo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class MongoDBUtil2 {

	private static MongoClient client=null;

	private static MongoDBUtil2 instance;
	static String url = null;
	static String dbname = null;
	static String userName =null;
	static String password = null;
	static String connectionsPerHost = null;
	static String threadsAllowedToBlockForConnectionMultiplier = null;
	static String connectTimeout=null;
	static String maxWaitTime=null;
	static String autoConnectRetry=null;
	static String socketKeepAlive=null;
	static String socketTimeout=null;

	public  static MongoDBUtil2 getInstance(){
		if(instance==null){
			initMongoDB();
		}
	   return instance;
	}
	
	public static void init(){
		if(instance==null){
			initMongoDB();
		}
	}
	
	private synchronized static void initMongoDB() {
		if (instance == null) {
			url = SystemContext.getInstance().mongoParamMap.get("mongo.url").toString();
			dbname = SystemContext.getInstance().mongoParamMap.get("mongo.dbname").toString();
			userName = SystemContext.getInstance().mongoParamMap.get("mongo.username").toString();
			password = SystemContext.getInstance().mongoParamMap.get("mongo.password").toString();
			connectionsPerHost = SystemContext.getInstance().mongoParamMap.get("mongo.connectionsPerHost").toString();
			threadsAllowedToBlockForConnectionMultiplier = SystemContext.getInstance().mongoParamMap.get("mongo.threadsAllowedToBlockForConnectionMultiplier").toString();
			connectTimeout= SystemContext.getInstance().mongoParamMap.get("mongo.connectTimeout").toString();
			maxWaitTime= SystemContext.getInstance().mongoParamMap.get("mongo.maxWaitTime").toString();
			autoConnectRetry=SystemContext.getInstance().mongoParamMap.get("mongo.autoConnectRetry").toString();
		    socketKeepAlive=SystemContext.getInstance().mongoParamMap.get("mongo.socketKeepAlive").toString();
			socketTimeout=SystemContext.getInstance().mongoParamMap.get("mongo.socketTimeout").toString();
			Builder options = new MongoClientOptions.Builder();
			options.connectTimeout(Integer.valueOf(connectTimeout));
			options.connectionsPerHost(Integer.valueOf(connectionsPerHost));
			options.socketTimeout(Integer.valueOf(socketTimeout));
			options.maxWaitTime(Integer.valueOf(maxWaitTime));
			options.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(threadsAllowedToBlockForConnectionMultiplier));
			try {
				MongoClientURI uri = new MongoClientURI(url,options);
				client=new MongoClient(uri);
				instance=new MongoDBUtil2();
			} catch (Exception e) {
			}
		}
	}

	private MongoDBUtil2(){
		
	}

	/**
     * 获取DB实例 - 指定DB
     * 
     * @param dbName
     * @return
     */
	public MongoDatabase getDB(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = client.getDatabase(dbName);
			return database;
		}
		return null;
	}
	
	public MongoDatabase getDB() {
		if (dbname != null && !"".equals(dbname)) {
			MongoDatabase database = client.getDatabase(dbname);
			return database;
		}
		return null;
	}

	/**
     * 获取collection对象 - 指定Collection
     * 
     * @param collName
     * @return
     */
	public MongoCollection<Document> getCollection(String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbname || "".equals(dbname)) {
			return null;
		}
		MongoCollection<Document> collection = client.getDatabase(dbname).getCollection(collName);
		return collection;
	}

	/**
     * 获取collection对象 - 指定Collection
     * 
     * @param collName
     * @return
     */
	public MongoCollection<Document> getCollection(String dbName, String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		MongoCollection<Document> collection = client.getDatabase(dbName).getCollection(collName);
		return collection;
	}
	

    /**
     * 查询DB下的所有表名
     */
    public List<String> getAllCollections(String dbName) {
        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }
    
    /**
     * 获取所有数据库名称列表
     * 
     * @return
     */
    public MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = client.listDatabaseNames();
        return s;
    }

    /**
     * 删除一个数据库
     */
    public void dropDB(String dbName) {
        getDB(dbName).drop();
    }
    
    /**
     * 查找对象 - 根据主键_id
     * 
     * @param collection
     * @param id
     * @return
     */
    public Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Document doc = coll.find(Filters.eq("_id", _idobj)).first();
        return doc;
    }
    
    public Document findByOne(MongoCollection<Document> coll,Bson filter) {
        Document doc = coll.find(filter).first();
        return doc;
    }

    /** 统计数 */
    public int getCount(MongoCollection<Document> coll) {
        int count = (int) coll.count();
        return count;
    }
    
    /** 统计数 */
    public int getCount(MongoCollection<Document> coll,Bson filter) {
        int count = (int) coll.count(filter);
        return count;
    }

    /** 条件查询 */
    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }
    
    /** 分页查询 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int currentPage, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((currentPage - 1) * pageSize).limit(pageSize).iterator();
    }
    
    /**
     * 通过ID删除
     * 
     * @param coll
     * @param id
     * @return
     */
    public int deleteById(MongoCollection<Document> coll, String id) {
        int count = 0;
        ObjectId _id = null;
        try {
            _id = new ObjectId(id);
        } catch (Exception e) {
            return 0;
        }
        Bson filter = Filters.eq("_id", _id);
        DeleteResult deleteResult = coll.deleteOne(filter);
        count = (int) deleteResult.getDeletedCount();
        return count;
    }
    
    /**
     * FIXME
     * 
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Bson filter = Filters.eq("_id", _idobj);
        // coll.replaceOne(filter, newdoc); // 完全替代
        coll.updateOne(filter, new Document("$set", newdoc));
        return newdoc;
    }

    public void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }
    
    /**
     * 关闭Mongodb
     */
    public void close() {
        if (client != null) {
            client.close();
            client = null;
        }
    }


}
