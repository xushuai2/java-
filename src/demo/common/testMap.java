package demo.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
public class testMap {

	public static void main(String[] args) {
		   System.out.println("*************************LinkedHashMap*************");
		   Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		   map.put(6, "apple");
		   map.put(7, "cie");
		   map.put(3, "banana");
		   map.put(2,"pear");
		   
		   for (Iterator it =  map.keySet().iterator();it.hasNext();)
		   {
		    Object key = it.next();
		    System.out.println( key+"="+ map.get(key));
		   }
		   
		   System.out.println("*************************HashMap*************");
		   Map<Integer,String> map1 = new  HashMap<Integer,String>();
		   map1.put(6, "apple");
		   map1.put(7, "cie");
		   map1.put(3, "banana");
		   map1.put(2,"pear");
		   
		   for (Iterator it =  map1.keySet().iterator();it.hasNext();)
		   {
		    Object key = it.next();
		    System.out.println( key+"="+ map1.get(key));
		   }
		   
		   System.out.println("*************************TreeMap*************");
		   Map<Integer,String> map2 = new  TreeMap<Integer,String>();
		   map2.put(6, "apple");
		   map2.put(7, "cie");
		   map2.put(3, "banana");
		   map2.put(2,"pear");
		   
		   //TreeMap能够把它保存的记录根据键排序，默认是按升序排序，也可以指定排序的比较器
		   for (Iterator it =  map2.keySet().iterator();it.hasNext();)
		   {
		    Object key = it.next();
		    System.out.println( key+"="+ map2.get(key));
		   }
		   
		   
	}

}
