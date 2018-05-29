package demo.common;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
	/**
	 * sendResult(向用户界面返回相应信息)
	 * @param success 成功与否
	 * @param message 返回信息
	 * @return Map 
	 */
	public static Map<String,Object> sendResult(boolean success,String message){
		Map<String,Object> rst = new HashMap<String,Object>(2);
		if(success){
			rst.put("success", success);
			rst.put("message", message);
		}else{
			rst.put("success", success);
			rst.put("message", message);
		}
		return rst;
	}
}
