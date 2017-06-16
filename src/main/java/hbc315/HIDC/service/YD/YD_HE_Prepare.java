package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;

public class YD_HE_Prepare {
	
	public static void main(String[] args) {
		YD_HE_Prepare yd = new YD_HE_Prepare();

		yd.getJSESSIONID();	
		//服务密码登录，获取验证码
		yd.getRnum();
		
		//短信验证登录
		//yd.sendTempPwd();

	}


	private String JSESSIONID = "";
	private String BIGipServerPOOL_SSO_80 = "";
	
	private void getJSESSIONID() {
		String url = "https://he.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; CmLocation=311|311; CmProvid=he";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
			}
			
			if (c.getName().equals("BIGipServerPOOL-SSO-80")) {
				BIGipServerPOOL_SSO_80 = c.getValue();
			}
		}
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("BIGipServerPOOL_SSO_80: " + BIGipServerPOOL_SSO_80);
	}
	
	
	private void getRnum(){
		String url = "https://he.ac.10086.cn/common/image.jsp";	
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=100|100; CmProvid=he; JSESSIONID=" + JSESSIONID + "; BIGipServerPOOL-SSO-80="+ BIGipServerPOOL_SSO_80;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
			
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_HE.jpg";
			FileOutputStream fos = new FileOutputStream(picName);
			
			byte[] b = new byte[1024];
			while((is.read(b)) != -1){
				fos.write(b);
			}
			
			fos.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
}
