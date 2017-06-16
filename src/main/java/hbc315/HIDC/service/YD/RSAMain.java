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
  
  
/**   
 * @author cnchenhl   
 * Jul 8, 2011  
 */   
public class RSAMain {   
  
    private static String module = "oxIwed0w6vVMjNwWd1dqvYvuJZOBFvZPIvDVtdcPD9b/QIldbPVUpj93B7alpupcexFOLWEeL2dXJpYdDlzyibcDMePdyvwJboiOaG4lLlLb4B0/OXD01/fESvGvAZMZJXmM7rHMiSObqIhd8+1ced701xvFeJWdysxwU5osIw0=";   
    private static String exponentString = "AQAB";   
    private static String delement = "vmaYHEbPAgOJvaEXQl+t8DQKFT1fudEysTy31LTyXjGu6XiltXXHUuZaa2IPyHgBz0Nd7znwsW/S44iql0Fen1kzKioEL3svANui63O3o5xdDeExVM6zOf1wUUh/oldovPweChyoAdMtUzgvCbJk1sYDJf++Nr0FeNW1RB1XG30=";   
    private static String encryptString = "Vx/dGjS1YWKRubsoDgiShiwLgqyNE2z/eM65U7HZx+RogwaiZimNBxjuOS6acEhKZx66cMYEAd1fc6oewbEvDIfP44GaN9dCjKE/BkkQlwEg6aTO5q+yqy+nEGe1kvLY9EyXS/Kv1LDh3e/2xAk5FNj8Zp6oU2kq4ewL8kK/ai4=";   
    /**   
     * @param args   
     */   
    public static void main(String[] args) {   
        byte[] en = encrypt();   
        System.out.println(Base64.encode(en));   
        byte[] enTest = null;   
        try {   
            enTest = Base64.decode(encryptString);   
        } catch (Base64DecodingException e) {   
            e.printStackTrace();   
        }   
        System.out.println(enTest.length);   
        System.out.println(en.length);   
        System.out.println(new String(Dencrypt(en)));   
        System.out.println(new String(Dencrypt(enTest)));   
    }   
  
    public static byte[] encrypt() {   
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
  
            byte[] cipherData = cipher.doFinal(new String("920408").getBytes());   
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