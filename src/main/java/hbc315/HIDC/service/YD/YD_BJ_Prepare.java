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
import net.sf.json.JSONArray;

public class YD_BJ_Prepare {

	private String mobile = "13701346824";
	
	public static void main(String[] args) {
		YD_BJ_Prepare yd = new YD_BJ_Prepare();
		yd.getWebtrend();
		yd.getJSESSIONID();
		
		//网站密码登录，获取验证码
		//yd.getRnum();
		
		//短信验证登录
		yd.sendTempPwd();

	}

	private String Webtrends = "";

	private void getWebtrend() {
		String url = "https://bj.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=100|100; CmProvid=bj";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("Webtrends")) {
				Webtrends = c.getValue();
				break;
			}
		}
		System.out.println("Webtrends:" + Webtrends);
	}

	private String JSESSIONID = "";
	
	private void getJSESSIONID() {
		String url = "https://bj.ac.10086.cn/ac/cmsso/iloginnew.jsp";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=100|100; CmProvid=tj; Webtrends="+ Webtrends;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
				break;
			}
		}
		System.out.println("JSESSIONID:" + JSESSIONID);
	}
	
	private String rnum = "";
	
	private void getRnum(){
		String url = "https://bj.ac.10086.cn/ac/ValidateNum?smartID=6362968194";	
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=100|100; CmProvid=bj; Webtrends="+ Webtrends + "; JSESSIONID=" + JSESSIONID;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
			
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_BJ.jpg";
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
	
	private void sendTempPwd(){
		HttpPost postMethod = new HttpPost("https://bj.ac.10086.cn/ac/tempPwdSend");
		
		String cookie = "continue=http://www.bj.10086.cn/my; continuelogout=http://www.bj.10086.cn; CmLocation=100|100; CmProvid=bj; JSESSIONID=" + JSESSIONID + "; login_mobile=" + mobile;
		postMethod.addHeader("Cookie",cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobile", mobile));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		System.out.println(res.getResponse());
	}
}
