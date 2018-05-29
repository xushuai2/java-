package com.mina.common;

public class StringUtils {
	/** 
	 * bytes转换成十六进制字符串 
	 * @param byte[] b byte数组 
	 */  
	public static String byte2HexStr(byte[] b)  
	{  
	    String stmp="";  
	    StringBuilder sb = new StringBuilder("");  
	    for (int n=0;n<b.length;n++)  
	    {  
	        stmp = Integer.toHexString(b[n] & 0xFF);  
	        sb.append((stmp.length()==1)? "0"+stmp : stmp);  
	    }  
	    return sb.toString().toUpperCase().trim();  
	}  
	
	
	/**
	* 十六进制转换字符串
	* @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])
	* @return String 对应的字符串
	*/
	public static String hexStr2Str(String hexStr)
	{
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++)
		{
		n = str.indexOf(hexs[2 * i]) * 16;
		n += str.indexOf(hexs[2 * i + 1]);
		bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
	
	
	/**
	* 字符串转换成十六进制字符串
	* @param String str 待转换的ASCII字符串
	* @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	*/
	public static String str2HexStr(String str)
	{
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++)
		{
		bit = (bs[i] & 0x0f0) >> 4;
		sb.append(chars[bit]);
		bit = bs[i] & 0x0f;
		sb.append(chars[bit]);
		sb.append(' ');
		}
		return sb.toString().trim();
	}
	
	
	public static void main(String[] args) {
		System.out.println(hexStr2Str("0D0A71"));
	}
}
