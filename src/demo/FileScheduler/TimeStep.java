package demo.FileScheduler;

import java.util.Calendar;
import java.util.Date;

public class TimeStep {
	private Calendar calendar = Calendar.getInstance();  
	private int field = Calendar.SECOND;  
	private int amount = 10;  
	      
	public int getField() {  
		return field;  
	}  
	public void setField(int field) {  
		this.field = field;  
	}  
	public int getAmount() {  
		return amount;  
	}  
	public void setAmount(int amount) {  
		this.amount = amount;  
	}  
	/** 
	* 获取时间 
	* @exception IllegalArgumentException if field is unknown 
	* @return 
	*/  
	public Date next(){  
		calendar.add(field, amount);  
		return calendar.getTime();  
	}  
}
