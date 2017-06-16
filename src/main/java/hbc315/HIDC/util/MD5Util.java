package hbc315.HIDC.util;

import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
  
/** 
 * 采用MD5加密解密 
 * @author tfq 
 * @datetime 2011-10-13 
 */  
public class MD5Util {  
  
    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
  
    // 测试主函数  
    public static void main(String args[]) {  
  	  	String data = "https://clientaccess.10086.cn/biz-orange/LN/uamrandcode/sendMsgLogin_{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"uOwckxa/tK0uIjYmgHF9mo5D4R+pWBWDJ0QMPutDUkQJq64pewRTNW2i2TCTpDGoHX4ye1v9/eqz/QJqVhCTMERAhe38vOvbh77ChDh/eZd1NG0c8YzbUadnj4ix0tta\",\"ctid\":\"uOwckxa/tK0uIjYmgHF9mo5D4R+pWBWDJ0QMPutDUkQJq64pewRTNW2i2TCTpDGoHX4ye1v9/eqz/QJqVhCTMERAhe38vOvbh77ChDh/eZd1NG0c8YzbUadnj4ix0tta\",\"cv\":\"3.5.1\",\"en\":\"0\",\"reqBody\":{\"cellNum\":\"18211155401\"},\"sn\":\"EVA-AL10\",\"sp\":\"1080x1812\",\"st\":\"1\",\"sv\":\"6.0\",\"t\":\"\",\"xc\":\"A0001\",\"xk\":\"3e7eead938ee438c19236e1b81f1519318534377a910a1e2d31bc263c0370c40e483949f\"}_Leadeon/SecurityOrganization";
        System.out.println("原始：" + data);  
        System.out.println("MD5后：" + string2MD5(data));  
  
    }  
}  