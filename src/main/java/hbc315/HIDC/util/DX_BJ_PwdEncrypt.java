package hbc315.HIDC.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class DX_BJ_PwdEncrypt {    
    
	private static String ivString = "1234567812345678";
	
	//login.189.cn的md5加密
	private static String salt = "33b21adee1b8620a7ba81aea1a80c724";
	
    // 加密    
    public static String encrypt(String password) {    

		try {
			byte[] raw = salt.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"    
	        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度    
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);    
	        byte[] encrypted = cipher.doFinal(password.getBytes());    
	        
	        return Base64.encodeBase64String(encrypted);//此处使用BAES64做转码功能
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";     
    }    
    
    // 解密    
    public static String decrypt(String password) {    
        try {    
            byte[] raw = salt.getBytes("UTF-8");    
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());    
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);    
            byte[] encrypted1 = Base64.decodeBase64(password);//先用bAES64解密    
            try {    
                byte[] original = cipher.doFinal(encrypted1);    
                String originalString = new String(original);    
                return originalString;    
            } catch (Exception e) {    
                System.out.println(e.toString());    
                return "";    
            }    
        } catch (Exception ex) {    
            System.out.println(ex.toString());    
            return "";    
        }    
    }    
    
    public static final String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	DX_BJ_PwdEncrypt aes = new DX_BJ_PwdEncrypt();
    	String encrypt = aes.encrypt("989977");
    	System.out.println(encrypt);
    	
    	String decrpyt = aes.decrypt(encrypt);
    	System.out.println(decrpyt);
	}
  
} 
	


