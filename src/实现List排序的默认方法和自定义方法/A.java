package 实现List排序的默认方法和自定义方法;

public class A implements Comparable<A>{
	private String name;  
    private Integer order;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public int compareTo(A o) {
		return this.order.compareTo(o.getOrder());  
	}  
	
	@Override  
    public String toString() {  
        return "name is "+name+" order is "+order;  
    }  
    
}
