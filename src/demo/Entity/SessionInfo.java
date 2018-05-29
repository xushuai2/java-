package demo.Entity;

import java.util.HashMap;
import java.util.Map;


public class SessionInfo extends BaseEntity {
	private static final long serialVersionUID = 1143795143699309891L;
	private Map<String,Integer> permission	= new HashMap<String,Integer>();			//权限列表  
	public SessionInfo(){
		
	}
	public Map<String,Integer> getPermission() {
		return permission;
	}
	public void setPermission(Map<String,Integer> permission) {
		this.permission = permission;
	}
}
