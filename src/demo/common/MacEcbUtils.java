package demo.common;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
public class MacEcbUtils {
	private final static String DES = "DES";
	private final static String CIPHER_ALGORITHM = "DES/ECB/NoPadding";
	/**
	* 加密
	* @param src
	*            数据源
	* @param key
	*            密钥，长度必须是8的倍数
	* @return 返回加密后的数据
	* @throws DesException
	*/
	public byte[] encrypt(byte[] src, byte[] key) {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		try {
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作,NoPadding为填充方式 默认为PKCS5Padding
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			// LOG.error("算数错误", e);
		} catch (InvalidKeyException e) {
			// LOG.error("无效key错误", e);
		} catch (InvalidKeySpecException e) {
			// LOG.error("无效key戳无", e);
		} catch (NoSuchPaddingException e) {
			// LOG.error("填充错误", e);
		} catch (IllegalBlockSizeException e) {
			// LOG.error("非法数据块", e);
		} catch (BadPaddingException e) {
			// LOG.error("错误的填充", e);
		}
		return null;
	}


	/**
	* mac计算,数据不为8的倍数，需要补0，将数据8个字节进行异或，再将异或的结果与下一个8个字节异或，
	* 一直到最后，将异或后的数据进行DES计算
	* 
	* @param key
	* @param Input
	* @return
	*/
	public byte[] clacMac(byte[] key, byte[] data) {
		byte[] IV = new byte[8];
		for (int i = 0; i < data.length;) {
			IV[i & 7] ^= data[i];
			++i;
			if (((i % 8) == 0) || (i == data.length)) {
				IV = encrypt(IV, key);
			}
		}
		return IV;
	}


	public String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
			sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}


	public String calcMacStr(String macKey, String macStr) {
		byte[] key = macKey.getBytes();
		byte[] data = macStr.getBytes();
		byte[] macValue = clacMac(key, data);
		String macValueStr = bytesToHexString(macValue).substring(0, 8);
		return macValueStr;
	}

	public static void main(String[] args) {
		//String key = "0x000x000x000x000x000x000x000x00";
		String key = "D6D5C8322A6B7507E0FD67A4E5EFCE0B";
		//String key = "00000000";
		String context = "0000000000000000";
		System.out.println(new MacEcbUtils().calcMacStr(key, context));
		/*byte[] keys = key.getBytes();
		for(byte b:keys){
			System.out.print(b+"-");
		}*/
	}
}
