package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;

/**
 * 浙江移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_ZJ_Prepare {
	
	public static void main(String[] args) {
		YD_ZJ_Prepare yd = new YD_ZJ_Prepare();
			
		yd.getJSESSIONID();
		yd.getRnum();
		yd.show();
	}

	private void show(){
		System.out.println("JSESSIONID: " + JSESSIONID);
	}
	
	private String JSESSIONID = "";
	
	private void getJSESSIONID() {
		String url = "https://zj.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
		}
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://zj.ac.10086.cn/ImgDisp";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "JSESSIONID="+ JSESSIONID;
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "https://zj.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_ZJ.jpg";
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
