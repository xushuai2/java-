package 实现List排序的默认方法和自定义方法;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
	public static void main(String[] args) {  
        List<String> lists = new ArrayList<String>();  
        List<A> list = new ArrayList<A>();  
        List<B> listB = new ArrayList<B>();  
        lists.add("5");  
        lists.add("2");  
        lists.add("9");  
        //lists中的对象String 本身含有compareTo方法，所以可以直接调用sort方法，按自然顺序排序，即升序排序  
        Collections.sort(lists);  
          
        A aa = new A();  
        aa.setName("aa");  
        aa.setOrder(1);  
        A bb = new A();  
        bb.setName("bb");  
        bb.setOrder(2);  
        list.add(bb);  
        list.add(aa);  
        //list中的对象A实现Comparable接口  
        Collections.sort(list);  
        
        //降序
        //Collections.reverse(list);
        
          
        B ab = new B();  
        ab.setName("ab");  
        ab.setOrder("1");  
        B ba = new B();  
        ba.setName("ba");  
        ba.setOrder("2");  
        listB.add(ba);  
        listB.add(ab);  
        //根据Collections.sort重载方法来实现  
        Collections.sort(listB,new Comparator<B>(){  
            @Override  
            public int compare(B b1, B b2) {  
            	//升排序是从小到大的排 1-2
                // return b1.getOrder().compareTo(b2.getOrder());  
            	//降排序2-1
            	return b2.getOrder().compareTo(b1.getOrder());  
            }  
              
        });  
          
        System.out.println(lists);  
        System.out.println(list);  
        System.out.println(listB);  
          
    }  
}
