package demo.Entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 7493510437593917193L;
	
	
	/**
	 * 重载实现toString方法 主要用来记录日志
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
