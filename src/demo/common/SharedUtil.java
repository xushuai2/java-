package demo.common;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
public class SharedUtil {
	public static void main(String[] args) {
		//System.out.println(generateLoanContractNumber(1));
		//System.out.println(generateTradeBatchNumber(100001));
		String bys = null;
		byte[] by = null;
		try {
			by = "0000hAsansa".getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(byte b:by){
			System.out.print(b+"-");
		}
		//ASCII字符代码表中a对应97
		//104-65-115-97-110-115-97  
		//hAsansa
		String alen = StringUtils.leftPad(String.valueOf(by.length), 4, "0");
		System.out.println();
		System.out.println(alen);
		int len = by.length;
		String slen = StringUtils.leftPad(String.valueOf(len), 4, "0");
		System.out.println("slen="+slen);
		try {
			bys = new String(by,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("by="+bys);
		System.arraycopy(slen.getBytes(), 0, by, 0, 4);
		
		try {
			bys = new String(by,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("bys="+bys);
	}
	
	
	
	
	/**
	 * 获取唯一字符串 当前时间+UUID
	 * */
	public static String getUniqueString() {
		String uuid = UUID.randomUUID().toString();
		String currTime = getCurrentTime();
		StringBuffer uniqueBuffer = new StringBuffer();
		String uniqueString = uniqueBuffer.append(currTime).append(uuid).toString().replaceAll("-", "");
		return uniqueString;
	}

	/**
	 * 获取当前时间字符串yyyyMMddHHmmss
	 * */
	public static String getCurrentTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTimeString = simpleDateFormat.format(new Date());
		return currTimeString;
	}

	/**
	 * 生成交易编号
	 * 
	 * @param flowNumber
	 * @return
	 */
	public static String generateTradeNumber(long flowNumber) {
		String tradeNumber = "P2P-TRADE-" + String.format("%012d", flowNumber);
		return tradeNumber;
	}

	/**
	 * 生成报盘批次号SL-BATCH-12位流水号000000000001
	 * 
	 * @return
	 */
	public static String generateTradeBatchNumber(long flowNumber) {
		String tradeBatchNumber = "P2P-BATCH-" + String.format("%012d", flowNumber);
		return tradeBatchNumber;

	}

	/**
	 * 生成提现编号
	 * 
	 * @return
	 */
	public static String generateWithdrawNumber(long flowNumber) {
		String withdrawNumber = "SL-WITHDRAW-" + String.format("%012d", flowNumber);
		return withdrawNumber;
	}

	/**
	 * 生成借款合同编号, 生成规则:SLB+yyyyMMdd+当天五位序列号,起始值:00001
	 * 
	 * @return
	 */
	public static String generateLoanContractNumber(long flowNumber) {
		String loanContractNumber = "SLB" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%05d", flowNumber);
		return loanContractNumber;
	}

	/**
	 * 生成客户编号, 生成规则:12位,不够位前面加零,起始值：100000000000
	 * 
	 * @return
	 */
	public static String generateCustomerNumber(long flowNumber) {
		String customerNumber = String.valueOf(flowNumber);
		return customerNumber;
	}

	/**
	 * Title: 生成六位短信数字验证码
	 * 
	 * @author caoyi
	 * @param
	 * @return 六位数字验证码
	 */
	public static String getSmsVerificationCode() {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			code += random.nextInt(10);
		}
		return code;
	}



	/**
	 * 数据流转为字符串
	 * **/
	public static String receiveInputStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		if (inputStream != null) {
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
		}
		byte[] responseBytes = outputStream.toByteArray();
		String responseString = new String(responseBytes, "UTF-8");
		return responseString;
	}
	
	
	public static String format2(String str){
		 //String str="86.64466666";  
         BigDecimal bd = new BigDecimal(Double.parseDouble(str));  
         System.out.println(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());  
         System.out.println("=================");  
         
         DecimalFormat df = new DecimalFormat("#.00");   
         System.out.println(df.format(Double.parseDouble(str)));   
         System.out.println("=================");  
         
         System.out.println(String.format("%.2f", Double.parseDouble(str)));  
         System.out.println("=================");  
         
         NumberFormat nf = NumberFormat.getNumberInstance();   
         nf.setMaximumFractionDigits(2);   
         System.out.println(nf.format(Double.parseDouble(str)));   
         
		 return df.format(Double.parseDouble(str));
		
	}
	
	
	

}

