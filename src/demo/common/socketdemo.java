package demo.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class socketdemo {

	public static void main(String[] args) throws IOException {  
        ServerSocket server = new ServerSocket(10000);  

        while (true) {  
            Socket socket = server.accept();  
            invoke(socket);  
        }  
    }  

    private static void invoke(final Socket client) throws IOException {  
        new Thread(new Runnable() {  
        	DataInputStream inData; 
        	DataOutputStream outData;
            public void run() {  
            	try {	
	            	inData = new DataInputStream(client.getInputStream()); 
	            	outData = new DataOutputStream(client.getOutputStream()); 
	            	
	        		String t = "00238000交易成功                                                                                                                                                                                                5MYM3TGYABBYYGHD2016-09-07 18:48:27";  
	        		System.out.println(t.length());
	        		System.out.println(t.getBytes("GBK").length);
	        		byte[] b = t.getBytes("GBK"); 
	        		
					outData.write(b,0,b.length);
					outData.flush();  
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {  
                    try {  
                    	outData.close();  
                    } catch (Exception e) {}  
                    try {  
                        client.close();  
                    } catch (Exception e) {}  
                }  
            }  
        }).start();  
    }  
}
