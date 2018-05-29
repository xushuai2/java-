package com.yanqiong.spider;
import java.io.IOException;
import java.sql.Connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class httpGet {
	 public final static void getByString(String url, Connection conn) throws Exception {
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpGet httpget = new HttpGet(url);
	            System.out.println("executing request " + httpget.getURI());
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        return entity != null ? EntityUtils.toString(entity) : null;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }
	            };
	            String responseBody = httpclient.execute(httpget, responseHandler);
	            Page page = new Page();  
	            page.setContent(responseBody);  
	            /*page.setDataUrl(url);  
	            Pattern compile = Pattern.compile("http://item.jd.com/([0-9]+).html");  
	            Matcher matcher = compile.matcher(url);  
	            String goodid = null;  
	            if (matcher.find()) {  
	                goodid = matcher.group(1);  
	                System.out.println("----------------------------------------goodid:"+goodid);
	                page.setGoodId(goodid);  
	            }  */
	            // 获取商品价格  
	            // 得到价格的json格式[{"id":"J_1593512","p":"17988.00","m":"17989.00"}]  
	            /*String pricejson = PageUtil.getContent("http://p.3.cn/prices/get?skuid=J_" + goodid);  
	            JSONArray jsonArray = new JSONArray(pricejson);  
	            JSONObject jsonObject = jsonArray.getJSONObject(0);  
	            String price = jsonObject.getString("p");  
	            page.setPrice(price);  
	            System.out.println(page);*/
	            parsePage.parseFromString(responseBody,conn);
	        } finally {
	            httpclient.close();
	        }
	    }
}
