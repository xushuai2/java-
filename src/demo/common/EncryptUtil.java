package demo.common;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class EncryptUtil {
	public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt){
		 // Encrypt the clear-text password using the same salt that was used to
		 // encrypt the original password
		 String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

		 // Authentication succeeds if encrypted password that the user entered
		 // is equal to the stored hash
		 return encryptedAttemptedPassword.compareTo(encryptedPassword)==0?true:false;
	}
	public static String getEncryptedPassword(String password, String salt){
		// PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
		// specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
		String algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes, so that's what makes sense here
		int derivedKeyLength = 160;
		// Pick an iteration count that works for you. The NIST recommends at
		// least 1,000 iterations:
		// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
		// iOS 4.x reportedly uses 10,000:
		// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
		int iterations = 20000;
		byte[] saltBytes = hexStringToBytes(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes, iterations, derivedKeyLength);

		SecretKeyFactory f;
		try {
			f = SecretKeyFactory.getInstance(algorithm);
			return bytesToHexString(f.generateSecret(spec).getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

  
    /** 
     * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 
     * 盐长度必须为8字节  
     *  
     * @return byte[] 盐值 
     * */  
    public static String getSalt(){  
        //实例化安全随机数  
        SecureRandom random = new SecureRandom();  
        //产出盐  
        return bytesToHexString(random.generateSeed(8));  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     * @param src 字节数组 
     * @return 
     */  
    public static String bytesToHexString(byte[] src) {  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
  
    /** 
     * 将十六进制字符串转换为字节数组 
     *  
     * @param hexString 十六进制字符串 
     * @return 
     */  
    public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }  
  
    private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }  
    
    
    public static String MD5(String value){
    	//确定计算方法
        MessageDigest md5;
        String result=new String();
		try {
			md5 = MessageDigest.getInstance("MD5");
			result = bytesToHexString(md5.digest(value.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }
}
