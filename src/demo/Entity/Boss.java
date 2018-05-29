package demo.Entity;

public class Boss {
	private String name;
	private int num;
	
	public void setName(String name) {
		this.name = name;
		System.out.println(name+">>>>>>>>boss>>>>>>>>>>>set执行了");
		num++;
	}
	
	public void start(){  
        System.out.println(name+"************************"+num);  
    }
	
	public void init(){
		System.out.println("-----bean.xml Boss spring  xml  注册*bean init-method=init********************执行了*");  
	} 
	
}
