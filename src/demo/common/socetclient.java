package demo.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class socetclient {
	 public static void main(String[] args) throws Exception {  
		 	InputStream in; 
		 	OutputStream out;
	        Socket socket = new Socket("127.0.0.1", 10000);  
	        out = socket.getOutputStream(); 
	        in = socket.getInputStream(); 
	        byte[] b ; 
	        Boolean boo=true;
	        StringBuffer sbuf = new StringBuffer();
	        while (boo) { 
	        	byte[] c = new byte[5];// 读取5个
				int lenTemp = 0;
				
				while(boo){
					lenTemp = in.read(c);
					if(lenTemp==-1){
						break;
					}		
					if(lenTemp>0){
						sbuf.append(new String(c, 0, lenTemp).trim());
						int sbuflen=sbuf.toString().getBytes().length;
						c=new byte[5-sbuflen];
					}
					if(sbuf.toString().getBytes().length==5){
						boo=false;
					}			
				}
	        } 
	        
	        
	        int lenTemp = 0;
	        int nLen = Integer.valueOf(sbuf.toString());
	        System.out.println(nLen);
			byte[] Msg = new byte[nLen];// 读取nLen个字节
			lenTemp = 0;
			boo = true;
			StringBuffer sb = new StringBuffer();
			while(boo){
				lenTemp = in.read(Msg);
				if(lenTemp==-1){
					break;
				}		
				if(lenTemp>0){
					sb.append(new String(subBytes(Msg,0,lenTemp),"GBK").trim());
					int sblen=sb.toString().getBytes("GBK").length - 5;
					Msg=new byte[nLen-sblen];
				}
				if(sb.toString().getBytes("GBK").length - 5 ==nLen){
					boo=false;
				}			
			}
			
			System.out.println(sb.toString());
			System.out.println(sb.toString().getBytes("GBK").length);
			String c  = subString(sb.toString().getBytes("GBK"),203,16);
			System.out.println(c);
	    }  
	 public static String subString(byte[] array,int offset,int length){
			String result = null;
			if(offset+length<=array.length){
				result = new String(array,offset,length);
			}
			return result;
		}
	 public static byte[] subBytes(byte[] src, int begin, int count) {
	        byte[] bs = new byte[count];
	        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
	        return bs;
	    }
	 
	
}
