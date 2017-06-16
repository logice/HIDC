package hbc315.HIDC.service.YD;

import java.math.BigInteger;   
import java.security.KeyFactory;   
import java.security.PrivateKey;   
import java.security.PublicKey;   
import java.security.spec.RSAPrivateKeySpec;   
import java.security.spec.RSAPublicKeySpec;   
import javax.crypto.Cipher;   
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;   
import com.sun.org.apache.xml.internal.security.utils.Base64;   
  

public class YD_RSA_Encrypt {   
  
	private static String module = "";
    private static String exponentString = "AQAB";   
    private static String delement = "vmaYHEbPAgOJvaEXQl+t8DQKFT1fudEysTy31LTyXjGu6XiltXXHUuZaa2IPyHgBz0Nd7znwsW/S44iql0Fen1kzKioEL3svANui63O3o5xdDeExVM6zOf1wUUh/oldovPweChyoAdMtUzgvCbJk1sYDJf++Nr0FeNW1RB1XG30=";   
//    private static String encryptString = "Vx/dGjS1YWKRubsoDgiShiwLgqyNE2z/eM65U7HZx+RogwaiZimNBxjuOS6acEhKZx66cMYEAd1fc6oewbEvDIfP44GaN9dCjKE/BkkQlwEg6aTO5q+yqy+nEGe1kvLY9EyXS/Kv1LDh3e/2xAk5FNj8Zp6oU2kq4ewL8kK/ai4=";   
    
//    private static byte[] b = {(byte)0xA3,(byte)0x12,(byte)0x30,(byte)0x79,(byte)0xDD,(byte)0x30,(byte)0xEA,(byte)0xF5,(byte)0x4C,(byte)0x8C,(byte)0xDC,(byte)0x16,(byte)0x77,(byte)0x57,(byte)0x6A,(byte)0xBD,(byte)0x8B,(byte)0xEE,(byte)0x25,(byte)0x93,(byte)0x81,(byte)0x16,(byte)0xF6,(byte)0x4F,(byte)0x22,(byte)0xF0,(byte)0xD5,(byte)0xB5,(byte)0xD7,(byte)0x0F,(byte)0x0F,(byte)0xD6,(byte)0xFF,(byte)0x40,(byte)0x89,(byte)0x5D,(byte)0x6C,(byte)0xF5,(byte)0x54,(byte)0xA6,(byte)0x3F,(byte)0x77,(byte)0x07,(byte)0xB6,(byte)0xA5,(byte)0xA6,(byte)0xEA,(byte)0x5C,(byte)0x7B,(byte)0x11,(byte)0x4E,(byte)0x2D,(byte)0x61,(byte)0x1E,(byte)0x2F,(byte)0x67,(byte)0x57,(byte)0x26,(byte)0x96,(byte)0x1D,(byte)0x0E,(byte)0x5C,(byte)0xF2,(byte)0x89,(byte)0xB7,(byte)0x03,(byte)0x31,(byte)0xE3,(byte)0xDD,(byte)0xCA,(byte)0xFC,(byte)0x09,(byte)0x6E,(byte)0x88,(byte)0x8E,(byte)0x68,(byte)0x6E,(byte)0x25,(byte)0x2E,(byte)0x52,(byte)0xDB,(byte)0xE0,(byte)0x1D,(byte)0x3F,(byte)0x39,(byte)0x70,(byte)0xF4,(byte)0xD7,(byte)0xF7,(byte)0xC4,(byte)0x4A,(byte)0xF1,(byte)0xAF,(byte)0x01,(byte)0x93,(byte)0x19,(byte)0x25,(byte)0x79,(byte)0x8C,(byte)0xEE,(byte)0xB1,(byte)0xCC,(byte)0x89,(byte)0x23,(byte)0x9B,(byte)0xA8,(byte)0x88,(byte)0x5D,(byte)0xF3,(byte)0xED,(byte)0x5C,(byte)0x79,(byte)0xDE,(byte)0xF4,(byte)0xD7,(byte)0x1B,(byte)0xC5,(byte)0x78,(byte)0x95,(byte)0x9D,(byte)0xCA,(byte)0xCC,(byte)0x70,(byte)0x53,(byte)0x9A,(byte)0x2C,(byte)0x23,(byte)0x0D};
    private static byte[] c = {(byte)0x93,(byte)0x22,(byte)0x5F,(byte)0xE7,(byte)0x11,(byte)0xC8,(byte)0xCA,(byte)0x91,(byte)0xD2,(byte)0xB0,(byte)0xEE,(byte)0xDA,(byte)0xC9,(byte)0x47,(byte)0x75,(byte)0x9C,(byte)0x5D,(byte)0x13,(byte)0xD4,(byte)0x16,(byte)0x4C,(byte)0x3B,(byte)0xAE,(byte)0x35,(byte)0xB2,(byte)0xAA,(byte)0xA2,(byte)0xF8,(byte)0x67,(byte)0x47,(byte)0x75,(byte)0xD4,(byte)0x86,(byte)0x67,(byte)0xA5,(byte)0x36,(byte)0x34,(byte)0xAA,(byte)0xC9,(byte)0x72,(byte)0x87,(byte)0xE5,(byte)0xC7,(byte)0x0C,(byte)0x1A,(byte)0x79,(byte)0x2E,(byte)0xFD,(byte)0x4E,(byte)0x63,(byte)0x22,(byte)0xA0,(byte)0xF5,(byte)0x22,(byte)0x2F,(byte)0x07,(byte)0xEA,(byte)0x85,(byte)0x12,(byte)0xC8,(byte)0x24,(byte)0x69,(byte)0xE5,(byte)0xB6,(byte)0x4D,(byte)0xDC,(byte)0x0E,(byte)0x7A,(byte)0xE4,(byte)0xBD,(byte)0xE5,(byte)0xDF,(byte)0xB1,(byte)0xE9,(byte)0x79,(byte)0x1D,(byte)0xE6,(byte)0x80,(byte)0x15,(byte)0x7B,(byte)0x35,(byte)0x49,(byte)0xB9,(byte)0x0F,(byte)0x7B,(byte)0xC4,(byte)0x3B,(byte)0x7B,(byte)0xDF,(byte)0xC4,(byte)0x4E,(byte)0x1B,(byte)0xE7,(byte)0x7A,(byte)0x0E,(byte)0x18,(byte)0xCD,(byte)0x8B,(byte)0xC1,(byte)0xED,(byte)0x7E,(byte)0x92,(byte)0x0A,(byte)0xBE,(byte)0xAC,(byte)0x41,(byte)0xD1,(byte)0x46,(byte)0x4B,(byte)0x34,(byte)0x19,(byte)0x82,(byte)0x40,(byte)0x27,(byte)0x66,(byte)0xCE,(byte)0xD3,(byte)0x57,(byte)0x5E,(byte)0x2D,(byte)0xEE,(byte)0x93,(byte)0xD0,(byte)0xB5,(byte)0x87,(byte)0xF1,(byte)0x0A,(byte)0x1D};

    /**   
     * @param args   
     */   
    public static void main(String[] args) {  
    	
//    	for(int i=0; i<c.length; i++){
//    		temp[i] = b[c.length-i-1];
//    	}
//    	
//    	module = Base64.encode(c);
//    	System.out.println("module: " + module);
//    	
//        byte[] en = encrypt("13701346824");   
//        System.out.println(Base64.encode(en));   
//        byte[] enTest = null;   
//        try {   
//            enTest = Base64.decode(encryptString);   
//        } catch (Base64DecodingException e) {   
//            e.printStackTrace();   
//        }   
//        System.out.println(enTest.length);   
//        System.out.println(en.length);   
//        System.out.println(new String(Dencrypt(en)));   
//        System.out.println(new String(Dencrypt(enTest)));   
    }   
    
    public static String getEntrypt(String content){
    	module = Base64.encode(c);
    	//System.out.println("module: " + module);
    	
        byte[] en = encrypt(content);   
        String result = Base64.encode(en);
        result = result.replaceAll("\n", "");
        //System.out.println(result);  
        return result;
    }
  
    public static byte[] encrypt(String content) {   
        try {   
            byte[] modulusBytes = Base64.decode(module);   
            byte[] exponentBytes = Base64.decode(exponentString);   
            BigInteger modulus = new BigInteger(1, modulusBytes);   
            BigInteger exponent = new BigInteger(1, exponentBytes);   
  
            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);   
            KeyFactory fact = KeyFactory.getInstance("RSA");   
            PublicKey pubKey = fact.generatePublic(rsaPubKey);   
  
            Cipher cipher = Cipher.getInstance("RSA");   
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);   
  
            byte[] cipherData = cipher.doFinal(content.getBytes());   
            return cipherData;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
  
    }   
  
    public static byte[] Dencrypt(byte[] encrypted) {   
        try {   
            byte[] expBytes = Base64.decode(delement);   
            byte[] modBytes = Base64.decode(module);   
  
            BigInteger modules = new BigInteger(1, modBytes);   
            BigInteger exponent = new BigInteger(1, expBytes);   
  
            KeyFactory factory = KeyFactory.getInstance("RSA");   
            Cipher cipher = Cipher.getInstance("RSA");   
  
            RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, exponent);   
            PrivateKey privKey = factory.generatePrivate(privSpec);   
            cipher.init(Cipher.DECRYPT_MODE, privKey);   
            byte[] decrypted = cipher.doFinal(encrypted);   
            return decrypted;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
    }   
}  