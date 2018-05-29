package Generic;

public class GenericTest {
	public static void main(String[] args) {
		  
		         Box<String> name = new Box<String>("corn");
		         Box<Integer> age = new Box<Integer>(712);
		         Box<Number> number = new Box<Number>(314);
		  
		         getData(name);
		         getData(age);
		         getData(number);
		         System.out.println("-----------------------------" );
		         //getUpperNumberData(name); // 1 error
		         getUpperNumberData(age);    // 2  ok
		         getUpperNumberData(number); // 3  ok
		     }
		 
		     public static void getData(Box<?> data) {
		         System.out.println("data :" + data.getData());
		     }
		     
		     public static void getUpperNumberData(Box<? extends Number> data){
		         System.out.println("data :" + data.getData());
		     }
}
