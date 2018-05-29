package demo.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
    private static final String DEFAULT_ENCODING = "UTF8";
    private static final String ALGORITH = "MD5";
    private static final MessageDigest md = getMessageDigest("MD5");

    public MD5Util()
    {
    	
    }

    public static String digest(String srcStr, String encode)
    {
        byte rstBytes[];
        try
        {
            rstBytes = md.digest(srcStr.getBytes(encode));
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
        return toHex(rstBytes, true);
    }

    public static String digest(String srcStr)
    {
        return digest(srcStr, "UTF8");
    }

    private static MessageDigest getMessageDigest(String algorithm)
    {
        try
        {
            return MessageDigest.getInstance(algorithm);
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(byte bytes[], boolean flag)
    {
        return new String(processBytes2Hex(bytes, flag ? DIGITS_LOWER : DIGITS_UPPER));
    }

    private static char[] processBytes2Hex(byte bytes[], char digits[])
    {
        int l = bytes.length << 1;
        char rstChars[] = new char[l];
        int j = 0;
        for(int i = 0; i < bytes.length; i++)
        {
            rstChars[j++] = digits[(240 & bytes[i]) >>> 4];
            rstChars[j++] = digits[15 & bytes[i]];
        }

        return rstChars;
    }

    public static String encode(byte source[])
    {
        String s = null;
        char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'a', 'b', 'c', 'd', 'e', 'f'
        };
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[32];
            int k = 0;
            for(int i = 0; i < 16; i++)
            {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    private static final char DIGITS_LOWER[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static final char DIGITS_UPPER[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static void main(String[] args) {
		System.out.println(digest("12365411111111111111好说歹说111111111111111111111"));
	}

}


