package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;


/**
 * 山东移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_SD_SendMessage {


	private static String mobile = "15066872340";
	private static String servicePassword = "910604";	
	
	//private String rnum = "qkdnw";	//验证码
	
	public static void main(String[] args) {
		YD_SD_SendMessage yd = new YD_SD_SendMessage();

		yd.getSSOjsessionid();
		yd.getBIGipServerApache_pool_80();
		yd.login();
		yd.getJSESSIONID();
		yd.getBIGipServerwls_pool_86();
		yd.ssoLogin();
		yd.ssoLogin2();
		yd.getRedirect();
		yd.getStartDay();
		yd.getRoute1();
		yd.getRnum();
		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("dtCookie: " + dtCookie);
		System.out.println("ssojsessionid: " + ssojsessionid);
		System.out.println("JSESSIONID_EMOBILE: " + JSESSIONID_EMOBILE);
		System.out.println("routem1: " + routem1);
		System.out.println("BIGipServerpool_eMobile_5354: " + BIGipServerpool_eMobile_5354);
		System.out.println("route1: " + route1);
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("BIGipServerApache_pool_80: " + BIGipServerApache_pool_80);
		System.out.println("BIGipServerwls_pool_86: " + BIGipServerwls_pool_86);		
		System.out.println("startDay: " + startDay);
	}
	
	private String dtCookie = ""; 
	private String ssojsessionid = "";

	/**
	 * getSSOjsessionid
	 */
	public void getSSOjsessionid(){
		String url = "https://sd.ac.10086.cn/portal/mainLogon.do";
		HttpPost postMethod = new HttpPost(url);

		postMethod.addHeader("Origin","https://sd.ac.10086.cn");
		postMethod.addHeader("Referer","https://sd.ac.10086.cn/login/");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("dtCookie")){
				dtCookie = c.getValue();
				System.out.println("dtCookie: "+dtCookie);
			}
			if(c.getName().equals("ssojsessionid")){
				ssojsessionid = c.getValue();
				System.out.println("ssojsessionid: "+ ssojsessionid);
			}
		}
		
	}
	
	private String BIGipServerApache_pool_80 = "";
	/**
	 * getBIGipServerApache_pool_80
	 */
	public void getBIGipServerApache_pool_80(){
		String url = "http://www.sd.10086.cn//portal/images/login/login_bg.png";
	
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "dtCookie=" + dtCookie;
		
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("BIGipServerApache_pool_80")){
				BIGipServerApache_pool_80 = c.getValue();
			}
			if(c.getName().equals("dtCookie")){
				dtCookie = c.getValue();
				System.out.println("dtCookie: " + dtCookie);
			}
		}

		System.out.println("BIGipServerApache_pool_80: " + BIGipServerApache_pool_80);
	}
	
	private String cmtokenid = "";
	private String CmWebtokenid = "";
	
	/**
	 * 密码登录
	 */
	public void login(){
		String url = "https://sd.ac.10086.cn/portal/servlet/LoginServlet";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "dtCookie=" + dtCookie + "; ssojsessionid=" + ssojsessionid ;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://sd.ac.10086.cn");
		postMethod.addHeader("Referer","https://sd.ac.10086.cn/portal/mainLogon.do");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobileNum", Base64.encodeBase64String(mobile.getBytes())));
		params.add(new BasicNameValuePair("servicePWD", Base64.encodeBase64String(servicePassword.getBytes())));
		params.add(new BasicNameValuePair("randCode", "请点击"));
		params.add(new BasicNameValuePair("smsRandomCode", ""));
		params.add(new BasicNameValuePair("submitMode", "2"));
		params.add(new BasicNameValuePair("logonMode", "1"));
		params.add(new BasicNameValuePair("FieldID", "1"));
		params.add(new BasicNameValuePair("ReturnURL", "www.sd.10086.cn/eMobile/jsp/common/prior.jsp"));
		params.add(new BasicNameValuePair("ErrorUrl", "../mainLogon.do"));
		params.add(new BasicNameValuePair("entrance", "IndexBrief"));
		params.add(new BasicNameValuePair("codeFlag", "0"));
		params.add(new BasicNameValuePair("openFlag", "1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+CmWebtokenid);
			}
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+ cmtokenid);
			}
			if(c.getName().equals("ssojsessionid")){
				ssojsessionid = c.getValue();
				System.out.println("ssojsessionid: "+ ssojsessionid);
			}
		}
		
		System.out.println(res.getResponse());
	}


	private String JSESSIONID_EMOBILE = "";
	private String routem1 = "";
	private String BIGipServerpool_eMobile_5354 = "";
	
	/**
	 * getJSESSIONID
	 * 
	 */
	public void getJSESSIONID(){
		String url = "http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "dtCookie=" + dtCookie + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid ;
		
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID_EMOBILE")){
				JSESSIONID_EMOBILE = c.getValue();
				System.out.println("JSESSIONID_EMOBILE: " + JSESSIONID_EMOBILE);
			}
			if(c.getName().equals("routem1")){
				routem1 = c.getValue();
				System.out.println("routem1: " + routem1);
			}
			if(c.getName().equals("BIGipServerpool_eMobile_5354")){
				BIGipServerpool_eMobile_5354 = c.getValue();
				System.out.println("BIGipServerpool_eMobile_5354: " + BIGipServerpool_eMobile_5354);
			}
		}
		
		//System.out.println(res.getResponse());
		
	}
	
	private String BIGipServerwls_pool_86 = "";
	private String param_a = "";
	
	/**
	 * getBIGipServerwls_pool_86
	 * 
	 */
	public void getBIGipServerwls_pool_86(){
		String url = "http://www.sd.10086.cn/portal/servlet/CookieServlet?FieldID=2";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354;
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("ssojsessionid")){
				ssojsessionid = c.getValue();
				System.out.println("ssojsessionid: " + ssojsessionid);
			}
			if(c.getName().equals("BIGipServerwls_pool_86")){
				BIGipServerwls_pool_86 = c.getValue();
				System.out.println("BIGipServerwls_pool_86: " + BIGipServerwls_pool_86);
			}
			if(c.getName().equals("BIGipServerApache_pool_80")){
				BIGipServerApache_pool_80 = c.getValue();
				System.out.println("BIGipServerApache_pool_80: " + BIGipServerApache_pool_80);
			}
		}
		
		param_a = res.getResponse().split("a='")[1];
		param_a = param_a.split("'")[0];
		System.out.println("param_a: " + param_a);
		
		System.out.println(res.getResponse());
	}
	
	private String CmLocation = "";
	private String eMobile_roteServer = "";
	/**
	 * 单点登录
	 */
	public void ssoLogin(){
		String url = "http://www.sd.10086.cn/eMobile/loginSSO.action?Attritd=" + param_a;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86;
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("CmLocation")){
				CmLocation = c.getValue();
				System.out.println("CmLocation: " + CmLocation);
			}
			if(c.getName().equals("eMobile_roteServer")){
				eMobile_roteServer = c.getValue();
				System.out.println("eMobile_roteServer: " + eMobile_roteServer);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 单点登录
	 */
	public void ssoLogin2(){
		String url = "http://www.sd.10086.cn/eMobile/loginSSO.action?Attritd=" + param_a;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "eMobile_roteServer=" + eMobile_roteServer + "; "
				+ "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "CmLocation=" + CmLocation;
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		
		
		System.out.println(res.getResponse());
	}
	
	private String redirect = "";
	
	/**
	 * getRedirect
	 * 
	 */
	public void getRedirect(){
		String url = "http://www.sd.10086.cn/eMobile/";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "CmLocation=531|532";
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		try {
			redirect = res.getLocation();
		} catch (Exception e) {
			System.out.println("redirect error");
		}
		
		System.out.println(res.getLocation());
	}
	
	private String startDay = "";
	/**
	 * getStartDay
	 * 
	 */
	public void getStartDay(){
		if(redirect == null || redirect.equals("")){ return; }
		String url = redirect;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "CmLocation=531|532";
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		try {
			startDay = res.getResponse().split("月结日：")[1];
			startDay = startDay.split("日")[0];
			System.out.println("startDay: " + startDay);
		} catch (Exception e) {
			System.out.println("redirect error");
		}
	}
	
	private String route1 = "";
	private String JSESSIONID = "";
	
	/**
	 * getRoute1
	 * 
	 */
	public void getRoute1(){
		String url = "http://www.sd.10086.cn/emall/eMobile/emobileFooter.action";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "CmLocation=531|532";
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer","http://www.sd.10086.cn/eMobile/jsp/common/prior.jsp");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("route1")){
				route1 = c.getValue();
				System.out.println("route1: " + route1);
			}
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
		}
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * 获取图片验证码
	 */
	private void getRnum(){
		String url = "http://www.sd.10086.cn/eMobile/RandomCodeImage?pageId=" + RandomNumUtil.getRandomNumber_16();
		HttpGet getMethod = new HttpGet(url);

		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; "
				+ "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "route1=" + route1 + "; "
				+ "NOTLOGIN_DEFAIL_CITY=532; CmLocation=531|532; CmProvid=sd; ";
		getMethod.setHeader("Cookie", cookie);
		getMethod.setHeader("Referer", "http://www.sd.10086.cn/eMobile/checkSmsPass.action?menuid=billdetails");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_SD.jpg";
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
	
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "http://www.sd.10086.cn/eMobile/sendSms.action?menuid=billdetails&pageid=" + RandomNumUtil.getRandomNumber_16();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; "
				+ "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "route1=" + route1 + "; "
				+ "NOTLOGIN_DEFAIL_CITY=532; CmLocation=531|532; CmProvid=sd; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sd.10086.cn");
		postMethod.addHeader("Referer", "http://www.sd.10086.cn/eMobile/checkSmsPass.action?menuid=billdetails");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
	

}
