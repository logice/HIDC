package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;

public class YD_AH_Prepare {
	
	public static void main(String[] args) {
		YD_AH_Prepare yd = new YD_AH_Prepare();

		yd.getJSESSIONID();
		yd.getRnum();
		yd.syso();
	}

	public void syso(){
		System.out.println("AHSSOSESSIONID: " + AHSSOSESSIONID);
	}
	
	private String AHSSOSESSIONID = "";
	
	/**
	 * 获取JSESSIONID
	 */
	private void getJSESSIONID() {
		String url = "https://ah.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("AHSSOSESSIONID")){
				AHSSOSESSIONID = c.getValue();
				System.out.println("AHSSOSESSIONID: "+ AHSSOSESSIONID);
			}
		}
		
		System.out.println(res.getResponse());
	}
	

	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://ah.ac.10086.cn/common/image.jsp?t=" + (System.currentTimeMillis() + 2000);
		HttpGet getMethod = new HttpGet(url);

		String cookies = "AHSSOSESSIONID=" + AHSSOSESSIONID + "; "
				+ "CmLocation=551|551; CmProvid=ah; "
				+ "SSO_SID=";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
			
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_AH.jpg";
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
