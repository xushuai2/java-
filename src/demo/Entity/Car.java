package demo.Entity;

public class Car {
	private String name;
	private int num;

	public void setName(String name) {
		this.name = name;
		System.out.println(">>>>>>>>car>>>>>>>>>>>set执行了");
		num++;
	}

	public void start(){  
        System.out.println(name+"  starting car..."+num);  
    } 
}
