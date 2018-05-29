package demo.mongodb;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystemContext {

    private static SystemContext instance;
    //private  final String RESOURSE="config.properties";
    private final String MONGODB="mongodb.properties";
    public  Map<String, Object> systemParamMap=new HashMap<String, Object>();
    
    public  Map<String, Object> mongoParamMap=new HashMap<String, Object>();
    private SystemContext() {

    }
    public static SystemContext getInstance(){
        if(instance==null){
            initInstance();
        }
        return instance;
    }
    private static synchronized void  initInstance(){
        if(instance==null){
            instance=new SystemContext();
        }
    }
    
    public  void initSystemConfig(){
        /*Properties properties=LwSpringHelper.getProperties(RESOURSE);
        if(properties!=null){
            for (Entry<Object, Object> entry : properties.entrySet()) {
            	systemParamMap.put((String) entry.getKey(), (String) entry.getValue());
            }
        }*/
        Properties mongo=LwSpringHelper.getProperties(MONGODB);
        if(mongo!=null){
            for (Entry<Object, Object> entry : mongo.entrySet()) {
            	mongoParamMap.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }
   
    
}
