package 实现List排序的默认方法和自定义方法;

public class B {
    private String name;  
    private String order;  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getOrder() {  
        return order;  
    }  
    public void setOrder(String order) {  
        this.order = order;  
    }  
    @Override  
    public String toString() {  
        return "name is "+name+" order is "+order;  
    }  
}
