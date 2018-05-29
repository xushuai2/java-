package demo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/*[
 [
     {
         data=25,
         appName=HiNews,
         appId=65,
         statisticalDate=WedSep2800: 00: 00CST2016
     }
 ],
 [
     {
         data=130,
         appName=流量监控,
         appId=47,
         statisticalDate=WedSep2800: 00: 00CST2016
     },
     {
         data=45,
         appName=流量监控,
         appId=47,
         statisticalDate=ThuSep2900: 00: 00CST2016
     }
 ]
]*/


public class listToJson {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<List<Map<String, Object>>> resultList = new ArrayList<List<Map<String, Object>>>();
		List<Map<String, Object>>  lMaps = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>>  lMaps2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("data", 90);
		m.put("appid", 90);
		m.put("appName", 90);
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("data", 3333);
		m2.put("appid", "hajsnaskn");
		m2.put("appName", 333390);
		lMaps.add(m);
		lMaps.add(m2);
		lMaps2.add(m2);
		resultList.add(lMaps);
		
		resultList.add(lMaps2);
		resultList.add(lMaps2);
		String jsonMessage = listTojson(resultList);
		System.out.println(jsonMessage);
		
		try {
			JSONObject myJsonObject = new JSONObject(jsonMessage);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String listTojson(List<List<Map<String, Object>>> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        
        if(list != null && list.size() > 0){
        	for(int i=0;i<list.size();i++){
        		List<Map<String, Object>> l = list.get(i);
        		json.append("[");
        		if (l != null && l.size() > 0) {
                    for (Map<String, Object> map : l) {
                        json.append(new JSONObject(map));
                        json.append(",");
                    }
                    json.setCharAt(json.length() - 1, ']');
                } else {
                    json.append("]");
                }
        		if(i<list.size()-1){
        			 json.append(",");
        		}
        	}
        }
        json.append("]");
        return json.toString();
    }

}
