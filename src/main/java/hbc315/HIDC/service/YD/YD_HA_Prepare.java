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
 * 河南移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_HA_Prepare {
	
	public static void main(String[] args) {
		YD_HA_Prepare yd = new YD_HA_Prepare();
			
		//yd.getAlteonP();
		yd.getJSESSIONID();
		yd.getRnum();
		yd.ssosms();
		yd.mark1();
		yd.mark2();
		yd.mark3();
		yd.mark4();
		yd.show();
	}

	private void show(){
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("JSESSIONID2: " + JSESSIONID2);
		System.out.println("CmValidateCode: " + CmValidateCode);
		System.out.println("tokenid: " + tokenid);
		System.out.println("rnum: " + rnum);
	}
	
	private String rnum = "";
	private String JSESSIONID = "";
	private String CmValidateCode = "";
	private String tokenid = "";
	private String AlteonP = "";
	private void getAlteonP() {
		String url = "http://service.ha.10086.cn/service/index.action";
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
			if (c.getName().equals("AlteonP")) {
				AlteonP = c.getValue();
				System.out.println("AlteonP: " + AlteonP);
			}
		}
		
		try {
			tokenid = res.getResponse().split("tokenid\" value=\"")[1];
			tokenid = tokenid.split("\"")[0];
			System.out.println("tokenid: " + tokenid);
		} catch (Exception e) {
			
		}
	}

	private void getJSESSIONID() {
		String url = "https://ha.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);
		getMethod.setHeader("Referer", "http://service.ha.10086.cn/service/index.action");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
			if (c.getName().equals("CmValidateCode")) {
				CmValidateCode = c.getValue();
				System.out.println("CmValidateCode: " + CmValidateCode);
			}
		}
		
		try {
			tokenid = res.getResponse().split("tokenid\" value=\"")[1];
			tokenid = tokenid.split("\"")[0];
			System.out.println("tokenid: " + tokenid);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://ha.ac.10086.cn/checkImage";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=371|371; CmProvid=ha; WT_FPC=id=2b4f8aff75838127bab1466998908403:lv=1466998952090:ss=1466998908403; CmValidateCode="+ CmValidateCode +"; JSESSIONID="+ JSESSIONID;
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "https://ha.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_HA.jpg";
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
	
	private String JSESSIONID2 = "";
	/**
	 * ssosms
	 */
	public void ssosms(){
		String url = "https://ha.ac.10086.cn/ssosms.jsp";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmLocation=371|371; CmProvid=ha; WT_FPC=id=2b4f8aff75838127bab1466998908403:lv=1466998952090:ss=1466998908403; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID;
		getMethod.addHeader("Cookie",cookie);
		getMethod.addHeader("Referer","https://ha.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID2 = c.getValue();
				System.out.println("JSESSIONID2: " + JSESSIONID2);
			}
		}
		System.out.println(res.getResponse());
	}
	
	private String mark = "";
	/**
	 * mark1
	 */
	public void mark1(){
		String url = "https://ha.ac.10086.cn/markQrCode";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=371|371; CmProvid=ha; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://ha.ac.10086.cn");
		postMethod.addHeader("Referer","https://ha.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("timed", String.valueOf(System.currentTimeMillis()))); 
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
	
		mark = res.getResponse();
		System.out.println(res.getResponse());
	}
	
	/**
	 * mark2
	 */
	public void mark2(){
		String url = "https://ha.ac.10086.cn/qrCode?mark=" + mark;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmLocation=371|371; CmProvid=ha; WT_FPC=id=2b4f8aff75838127bab1466998908403:lv=1466998952090:ss=1466998908403; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID2;
		getMethod.addHeader("Cookie",cookie);
		getMethod.addHeader("Origin","https://ha.ac.10086.cn");
		getMethod.addHeader("Referer","https://ha.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID2 = c.getValue();
				System.out.println("JSESSIONID2: " + JSESSIONID2);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * mark3
	 */
	public void mark3(){
		String url = "https://ha.ac.10086.cn/picverCode";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WT_FPC=id=2b4f8aff75838127bab1466998908403:lv=1466998952090:ss=1466998908403; CmLocation=371|371; CmProvid=ha; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID2;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://ha.ac.10086.cn");
		postMethod.addHeader("Referer","https://ha.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("timed", String.valueOf(System.currentTimeMillis()))); 
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
	
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID2 = c.getValue();
				System.out.println("JSESSIONID2: "+JSESSIONID2);
			}
		}
		
		rnum = res.getResponse().trim();
		System.out.println(res.getResponse());
	}
	
	/**
	 * mark4
	 */
	public void mark4(){
		String url = "https://ha.ac.10086.cn/favicon.ico";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmLocation=371|371; CmProvid=ha; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID2;
		getMethod.addHeader("Cookie",cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID2 = c.getValue();
				System.out.println("JSESSIONID2: "+JSESSIONID2);
			}
		}
		
		System.out.println(res.getResponse());
	}
}
