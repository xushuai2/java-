package demo.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

public class test {
	private Mongo mg = null;
    private DB db;
    private DBCollection coll ;
	
	
    @Before
    public void init() throws UnknownHostException {
    	System.out.println("-----------------init");
        try {
            mg = new Mongo();
            //mg = new Mongo("localhost", 27017);
        } catch (MongoException e) {
            e.printStackTrace();
        }
        //获取temp DB；如果默认没有创建，mongodb会自动创建
        db = mg.getDB("test");
        //获取coll  DBCollection；如果默认没有创建，mongodb会自动创建
        coll  = db.getCollection("person");
    }
    
    @After
    public void destory() {
    	System.out.println("-----------------After");
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
        coll  = null;
        System.gc();
    }
    
    public void print(Object o) {
        System.out.println(o);
    }
    
    
    
    /**
     * <b>function:</b> 查询所有数据
     * @author hoojo
     * @createDate 2011-6-2 下午03:22:40
     */
    private void queryAll() {
        print("--------查询coll 的所有数据：");
        //db游标
        DBCursor cur = coll.find();
        while (cur.hasNext()) {
            print(cur.next());
        }
        print("---------------------------------------------------");
    }
    
    private void qyeryFenye(int startnum,int num){
    	DBCursor cur = coll.find().skip(startnum).limit(num);
    	while (cur.hasNext()) {
            print(cur.next());
        }
    }
    
    @Test
    public void query2() {
    	queryAll();
    	qyeryFenye(2,3);
    }
    
     
    @Test
    public void add() {
        //先查询所有数据  coll.save(user)保存，getN()获取影响行数
        queryAll();
        DBObject user = new BasicDBObject();
        user.put("name", "吴用");
        user.put("age", 240);
        //扩展字段，随意添加字段，不影响现有数据
        user.put("sex", "男");
        //coll.save(user);
        
        //添加List集合
        List<DBObject> list = new ArrayList<DBObject>();
        list.add(user);
        BasicDBObject doc2 = new BasicDBObject();  
        doc2.put("name", "小张");  
        doc2.put("age", 25);  
        doc2.put("address", "江苏苏州");  
        list.add(doc2);
        BasicDBObject doc1 = new BasicDBObject();  
        doc1.put("name", "小李");  
        doc1.put("age", 30);  
        doc1.put("address", "江苏南京");  
        list.add(doc1);
        //添加List集合
        coll.insert(list);
        
        //查询下数据，看看是否添加成功
        print("count: " + coll.count());
        queryAll();
    }
    
    @Test
    public void remove() {
        queryAll();
        print("删除id = 4de73f7acd812d61b4626a77：" + coll .remove(new BasicDBObject("_id", new ObjectId("4de73f7acd812d61b4626a77"))).getN());
        print("remove age >= 24: " + coll .remove(new BasicDBObject("age", new BasicDBObject("$gte", 24))).getN());
    }
    
    
    @Test
    public void modify() {
        print("修改：" + coll .update(new BasicDBObject("_id", new ObjectId("4dde25d06be7c53ffbd70906")), new BasicDBObject("age", 99)).getN());
        print("修改：" + coll .update(
                new BasicDBObject("_id", new ObjectId("4dde2b06feb038463ff09042")), 
                new BasicDBObject("age", 121),
                true,//如果数据库不存在，是否添加
                false//多条修改
                ).getN());
        print("修改：" + coll .update(
                new BasicDBObject("name", "haha"), 
                new BasicDBObject("name", "dingding"),
                true,//如果数据库不存在，是否添加
                true//false只修改第一天，true如果有多条就不修改
                ).getN());
        
        //当数据库不存在就不修改、不添加数据，当多条数据就不修改
        //print("修改多条：" + coll.updateMulti(new BasicDBObject("_id", new ObjectId("4dde23616be7c19df07db42c")), new BasicDBObject("name", "199")));
    }
	
    
    
    //mongoDB不支持联合查询、子查询，这需要我们自己在程序中完成。将查询的结果集在Java查询中进行需要的过滤即可。
    @Test
    public void query() {
    	queryAll();
    	
        //查询id = 4de73f7acd812d61b4626a77
        print("find id = 579ac9c7d72d93033e3991b6: " + coll .find(new BasicDBObject("_id", new ObjectId("579ac9c7d72d93033e3991b6"))).toArray());
        
        //查询age = 24
        print("find age = 100: " + coll.find(new BasicDBObject("age", 100)).toArray());
        
        //查询age >= 24
        print("find age >= 25: " + coll.find(new BasicDBObject("age", new BasicDBObject("$gte", 25))).toArray());
        print("find age <= 20: " + coll.find(new BasicDBObject("age", new BasicDBObject("$lte", 20))).toArray());
        print("find age <= 25 && age >= 20: " + coll.find(new BasicDBObject("age", new BasicDBObject("$lte", 25).append("$gte", 20))).toArray());
        
        print("查询age!=25：" + coll.find(new BasicDBObject("age", new BasicDBObject("$ne", 25))).toArray());
        print("查询age in 24/25/100：" + coll.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.IN, new int[] { 24, 25, 100 }))).toArray());
        print("查询age not in 25/26/27：" + coll.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.NIN, new int[] { 25, 26, 27 }))).toArray());
        print("查询age exists 排序：" + coll.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.EXISTS, true))).toArray());
        
        print("只查询age属性：" + coll.find(null, new BasicDBObject("age", true)).toArray());
        print("只查属性：" + coll.find(null, new BasicDBObject("age", true), 0, 2).toArray());
        print("只查属性：" + coll.find(null, new BasicDBObject("age", true), 0, 2, Bytes.QUERYOPTION_NOTIMEOUT).toArray());
        
        //只查询一条数据，多条去第一条
        print("findOne: " + coll.findOne());//查询第一条记录
        print("findOne: " + coll.findOne(new BasicDBObject("age", 24)));
        print("findOne: " + coll.findOne(new BasicDBObject("age", 24), new BasicDBObject("name", true)));
        
        //查询修改、删除
        //print("findAndRemove 查询age=25的数据，并且删除: " + coll.findAndRemove(new BasicDBObject("age", 25)));
        
        //查询age=25的数据，并且修改name的值为Abc
        print("findAndModify: " + coll.findAndModify(new BasicDBObject("age", 25), new BasicDBObject("name", "Abc")));
       /* print("findAndModify: " + coll.findAndModify(
            new BasicDBObject("age", 28), //查询age=28的数据
            new BasicDBObject("name", true), //查询name属性
            new BasicDBObject("age", true), //按照age排序
            false, //是否删除，true表示删除
            new BasicDBObject("name", "Abc"), //修改的值，将name修改成Abc
            true, 
            true));*/
        
        queryAll();
    }
    
    
    
    public void testOthers() {
        DBObject user = new BasicDBObject();
        user.put("name", "hoojo");
        user.put("age", 24);
        
        //JSON 对象转换        
        print("serialize: " + JSON.serialize(user));
        //反序列化
        print("parse: " + JSON.parse("{ \"name\" : \"hoojo\" , \"age\" : 24}"));
        
        print("判断temp Collection是否存在: " + db.collectionExists("temp"));
        
        //如果不存在就创建
        if (!db.collectionExists("temp")) {
            DBObject options = new BasicDBObject();
            options.put("size", 20);
            options.put("capped", 20);
            options.put("max", 20);
            print(db.createCollection("account", options));
        }
        
        //设置db为只读
        //db.setReadOnly(true);
        
        //只读不能写入数据
        db.getCollection("test").save(user);
    }
    
	public static void main(String[] args) throws UnknownHostException {
		Mongo mg = null;
		//这样就创建了一个MongoDB的数据库连接对象，它默认连接到当前机器的localhost地址，端口是27017。
		mg = new Mongo();
		//mg = new Mongo("localhost", 27017);
        //查询所有的Database
        for (String name : mg.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }
        //这样就获得了一个test的数据库，如果mongoDB中没有创建这个数据库也是可以正常运行的。
        DB db = mg.getDB("test");
        //得到了db，下一步我们要获取一个“聚集集合DBCollection”，通过db对象的getCollection方法来完成。
        //查询所有的聚集集合
        for (String name : db.getCollectionNames()) {
            System.out.println("collectionName: " + name);
        }
        //这样就获得了一个DBCollection，它相当于我们数据库的“表”。
        DBCollection coll  = db.getCollection("person");
        
        //查询所有的数据
        //db游标
        DBCursor cur = coll.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
	}

}
