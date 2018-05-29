package demo.HttpClient;
import java.util.HashMap;
import java.util.Map;
public class Test {
	public static void qd(){
		System.out.println("***************************");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merId", "8000000017");
		map.put("busType", "000001");
		map.put("passwd", "111111");
		map.put("mobile", "13810716063");
		String result = HttpClientUtils.doPost("http://127.0.0.1:8089/RedisWebSpringmvcMybatis/user/detail?",map);
		System.out.println(result);
	}
	
	
	public static void main(String[] args) {
		System.out.println("-----------------");
	}
		
}
