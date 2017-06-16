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

import com.alibaba.fastjson.JSONObject;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;

/**
 * 广东移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_GD_Prepare {
	
	public static void main(String[] args) {
		YD_GD_Prepare yd = new YD_GD_Prepare();
			
		yd.getJSESSIONID();
		yd.getRnum();
		yd.show();
	}

	private void show(){
		System.out.println("JUCSSESSIONID: " + JUCSSESSIONID);
		System.out.println("e: " + e);
		System.out.println("maxdigits: " + maxdigits);
		System.out.println("n: " + n);
	}
	
	private String JUCSSESSIONID = "";
	private String e = "";
	private String maxdigits = "";
	private String n = "";
	
	private void getJSESSIONID() {
		String url = "https://gd.ac.10086.cn/ucs/login/signup.jsps";
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JUCSSESSIONID")) {
				JUCSSESSIONID = c.getValue();
				System.out.println("JUCSSESSIONID: " + JUCSSESSIONID);
			}
		}
		
		try {
			String temp = res.getResponse().split("rsa =")[1];
			temp = temp.split(";")[0].trim();
			JSONObject jsonboj = JSONObject.parseObject(temp);
			e = jsonboj.getString("e");
			maxdigits = jsonboj.getString("maxdigits");
			n = jsonboj.getString("n");
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://gd.ac.10086.cn/ucs/captcha/image/reade.jsps?sds=" + System.currentTimeMillis();
		HttpGet getMethod = new HttpGet(url);

		String cookies = "JUCSSESSIONID="+ JUCSSESSIONID;
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "https://gd.ac.10086.cn/ucs/login/signup.jsps");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_GD.jpg";
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
