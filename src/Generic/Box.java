package Generic;

public class Box<T> {
	private T data;
	public Box(){
		
	}
	public Box(T data){
		setData(data);
	}
	public T getData() {
        return data;
    }
	public void setData(T data) {
		this.data = data;
	}
}
