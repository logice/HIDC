package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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
 * 河南移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_HA_SendMessage {


	private static String mobile = "15039414655";
	private static String servicePassword = "989977";	
	
	private String rnum = "V6P6";	//验证码
	
	private String JSESSIONID = "0000BFmN4C8H69udV19r96PY3Ow:-1:16so1mai7";
	private String CmValidateCode = "bf1b355905150e9d7d23098dee477e6f"; 
	private String tokenid = "7a3074bcd1c92e96af7b56e7efbfab68";
	
	public static void main(String[] args) {
		YD_HA_SendMessage yd = new YD_HA_SendMessage();
		
		yd.getPublicKey();
		String mob = yd.passwordEncrypt(e, n, maxdigits, mobile);
		String pwd = yd.passwordEncrypt(e, n, maxdigits, servicePassword);
		yd.login(mob, pwd);
		yd.login(mob, pwd);
		yd.getRedirect();
		yd.getSAMLRequest();
		yd.getSAMLart();
		yd.SSOPost();
		yd.getHanetcookid();
//		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("cmtokenid: " + cmtokenid);
		
		System.out.println("e: " + e);
		System.out.println("n: " + n);
		System.out.println("maxdigits: " + maxdigits);
	}
	

	private static String e = "10001";
	private static String n = "8b46200b5a68a279c864704cd0270ab5a498fa395581046ead8e516b9ad4b1a9739481deb48782fe771cbca6cb9b63a1b205490e216903874d8ec2099f27ab11";
	private static String maxdigits = "67";
	/**
	 * 获取公钥
	 */
	private void getPublicKey() {
		String url = "https://ha.ac.10086.cn/rsaKey?broUUid=04558029DA074D5A99F18486222858C2&random=" + RandomNumUtil.getRandomNumber_16() + "&jsoncallback=jQuery0015745552955195308_" + (System.currentTimeMillis()-20000) + "&_=" + System.currentTimeMillis();
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			e = res.getResponse().split("e\":\"")[1];
			e = e.split("\"")[0];
			n = res.getResponse().split("n\":\"")[1];
			n = n.split("\"")[0];
			maxdigits = res.getResponse().split("maxdigits\":\"")[1];
			maxdigits = maxdigits.split("\"")[0];
			System.out.println("e: " + e);
			System.out.println("n: " + n);
			System.out.println("maxdigits: " + maxdigits);
		} catch (Exception e) {
			System.out.println("取公钥错误");
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 加密
	 * @param str
	 */
	public String passwordEncrypt(String key_e, String key_n, String key_maxdigits, String str){
		String result = "";
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			String fileName = "src/main/resource/js/YD/HA/Encrypt.js";
			FileReader reader = new FileReader(fileName); // 执行指定脚本
			engine.eval(reader);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数
				result = (String) invoke.invokeFunction("encryptForm", key_e, key_n, Integer.valueOf(key_maxdigits), str);
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	private String cmtokenid = "";
	private String CmWebtokenid = "";
	private String cmtokenidHeNan = "";
	
	/**
	 * 密码登录
	 */
	public void login(String mobileEncrypt ,String passwordEncrypt){
		String url = "https://ha.ac.10086.cn/login";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = " CmLocation=371|371; CmProvid=ha; WT_FPC=id=2b4f8aff75838127bab1466998908403:lv=1466998952090:ss=1466998908403; CmValidateCode=" + CmValidateCode + "; JSESSIONID=" + JSESSIONID;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://ha.ac.10086.cn");
		postMethod.addHeader("Referer","https://ha.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("IDToken1", mobileEncrypt));
		params.add(new BasicNameValuePair("IDToken2", "2"));
		params.add(new BasicNameValuePair("IDToken3", passwordEncrypt));
		params.add(new BasicNameValuePair("IDToken4", rnum));
		params.add(new BasicNameValuePair("IDToken5", "04558029DA074D5A99F18486222858C2"));
		params.add(new BasicNameValuePair("SPID", "https%3A%2F%2Fha.ac.10086.cn%2Flogin"));
		params.add(new BasicNameValuePair("typeForPower", null));
		params.add(new BasicNameValuePair("goto", ""));
		params.add(new BasicNameValuePair("agreen", "1"));
		params.add(new BasicNameValuePair("tokenid", tokenid));
		
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
			if(c.getName().equals("cmtokenidHeNan")){
				cmtokenidHeNan = c.getValue();
				System.out.println("cmtokenidHeNan: "+ cmtokenidHeNan);
			}
		}
		
		System.out.println(res.getResponse());
	}

	private String redirect = "";
	/**
	 * 获取redirect
	 * 
	 */
	public void getRedirect(){
		String url = "https://service.ha.10086.cn/service/index.action";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmValidateCode=" + CmValidateCode + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "passtype=2; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmProvid=ha; isAgree=yes; "
				+ "cmtokenidHeNan=" + cmtokenidHeNan;	
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "https://ha.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
		}
		redirect = res.getLocation();
		System.out.println(res.getResponse());
	}
	
	private String SAMLRequest = "";
	private String AlteonP = "";
	/**
	 * getSAMLRequest
	 * 
	 */
	public void getSAMLRequest(){
		String url = "https://service.ha.10086.cn/service/index.action";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmValidateCode=" + CmValidateCode + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "passtype=2; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmProvid=ha; isAgree=yes; "
				+ "cmtokenidHeNan=" + cmtokenidHeNan + "; "
				+ "JSESSIONID=" + JSESSIONID;
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
			if(c.getName().equals("AlteonP")){
				AlteonP = c.getValue();
				System.out.println("AlteonP: " + AlteonP);
			}
		}
		
		try {
			SAMLRequest = res.getResponse().split("SAMLRequest\" value=\"")[1];
			SAMLRequest = SAMLRequest.split("\"")[0];
		} catch (Exception e) {
			
		}
		
		System.out.println(res.getResponse());
	}
	
	private String SAMLart = "";
	/**
	 * getSAMLart
	 */
	public void getSAMLart(){
		String url = "https://ha.ac.10086.cn/SSOPOST";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmValidateCode=" + CmValidateCode + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "passtype=2; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmProvid=ha; isAgree=yes; "
				+ "cmtokenidHeNan=" + cmtokenidHeNan;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","http://service.ha.10086.cn");
		postMethod.addHeader("Referer","http://service.ha.10086.cn/samlredirect.jsp?RelayState=http://service.ha.10086.cn/service/index.action");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLRequest", SAMLRequest));
		params.add(new BasicNameValuePair("RelayState", "http%3A%2F%2Fservice.ha.10086.cn%2Fservice%2Findex.action"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		try {
			SAMLart = res.getResponse().split("SAMLart\" VALUE=\"")[1];
			SAMLart = SAMLart.split(";\"")[0];
		} catch (Exception e) {
			
		}
		
		System.out.println(res.getResponse());
	}

	
	private String activateSSOTime = "";
	private String mobilenoecpt = "";
	private String prodCode = "";
	private String RadomTokenKey = "";
	private String hnmcc2 = "";
	private String his_login_id = "";
	private String hawt_uinfo = "";
	private String getHanetcookidURL = "";
	/**
	 * 单点登录sso
	 */
	public void SSOPost(){
		String url = "https://service.ha.10086.cn/acauthen.jsp";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmValidateCode=" + CmValidateCode + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "passtype=2; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmProvid=ha; isAgree=yes; "
				+ "cmtokenidHeNan=" + cmtokenidHeNan + "; "
				+ "AlteonP=" + AlteonP;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://ha.ac.10086.cn");
		postMethod.addHeader("Referer","https://ha.ac.10086.cn/SSOPOST");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("RelayState", "http%3A%2F%2Fservice.ha.10086.cn%2Fservice%2Findex.action"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: "+JSESSIONID);
			}
			if(c.getName().equals("activateSSOTime")){
				activateSSOTime = c.getValue();
				System.out.println("activateSSOTime: "+ activateSSOTime);
			}
			if(c.getName().equals("mobilenoecpt")){
				mobilenoecpt = c.getValue();
				System.out.println("mobilenoecpt: "+ mobilenoecpt);
			}
			if(c.getName().equals("prodCode")){
				prodCode = c.getValue();
				System.out.println("prodCode: "+ prodCode);
			}
			if(c.getName().equals("RadomTokenKey")){
				RadomTokenKey = c.getValue();
				System.out.println("RadomTokenKey: "+ RadomTokenKey);
			}
			if(c.getName().equals("hnmcc2")){
				hnmcc2 = c.getValue();
				System.out.println("hnmcc2: "+ hnmcc2);
			}
			if(c.getName().equals("his_login_id")){
				his_login_id = c.getValue();
				System.out.println("his_login_id: "+ his_login_id);
			}
			if(c.getName().equals("hawt_uinfo")){
				hawt_uinfo = c.getValue();
				System.out.println("hawt_uinfo: "+ hawt_uinfo);
			}
		}
		
		getHanetcookidURL = res.getLocation();
		
		System.out.println(res.getResponse());
	}
	
	private String hanetcookid = "";
	/**
	 * getSAMLRequest
	 * 
	 */
	public void getHanetcookid(){
		if(getHanetcookidURL == null){} else{
			HttpGet getMethod = new HttpGet(getHanetcookidURL);
			
			String cookie = "CmValidateCode=" + CmValidateCode + "; "
					+ "JSESSIONID=" + JSESSIONID + "; "
					+ "cmtokenid=" + cmtokenid + "; "
					+ "passtype=2; "
					+ "CmWebtokenid=" + CmWebtokenid + "; "
					+ "isAgree=yes; isAgree_1=yes; "
					+ "cmtokenidHeNan=" + cmtokenidHeNan
					+ "AlteonP=" + AlteonP + "; "
					+ "pwdType=100; userScore=\"\"; prandCode=G00; city=P; "
					+ "mobileno=" + mobile + "; "
					+ "mobilenoecpt=" + mobilenoecpt + "; "
					+ "prodCode=" + prodCode + "; "
					+ "userstatus=1; "
					+ "RadomTokenKey=" + RadomTokenKey + "; "
					+ "his_login_id=" + his_login_id + "; "
					+ "hawt_uinfo=" + hawt_uinfo + "; "
					+ "hnmcc2=" + hnmcc2 + "; "
					+ "activateSSOTime=" + activateSSOTime + "; "
					+ "CmLocation=371|371; CmProvid=ha; ";
			getMethod.addHeader("Cookie", cookie);
			
			ResponseValue res = CommonHttpMethod.doGet(getMethod);		

			for(Cookie c : res.getCookies()){
				if(c.getName().equals("hanetcookid")){
					hanetcookid = c.getValue();
					System.out.println("hanetcookid: " + hanetcookid);
				}
				if(c.getName().equals("activateSSOTime")){
					activateSSOTime = c.getValue();
					System.out.println("activateSSOTime: " + activateSSOTime);
				}
				if(c.getName().equals("JSESSIONID")){
					JSESSIONID = c.getValue();
					System.out.println("JSESSIONID: " + JSESSIONID);
				}
				if(c.getName().equals("hnmcc2")){
					hnmcc2 = c.getValue();
					System.out.println("hnmcc2: " + hnmcc2);
				}
			}
		}
	}
	
	
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "http://service.ha.10086.cn/verify!XdcxSecondAuthCode.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmValidateCode=" + CmValidateCode + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "passtype=2; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "isAgree=yes; isAgree_1=yes; "
				+ "cmtokenidHeNan=" + cmtokenidHeNan
				+ "AlteonP=" + AlteonP + "; "
				+ "pwdType=100; userScore=\"\"; prandCode=G00; city=P; "
				+ "mobileno=" + mobile + "; "
				+ "mobilenoecpt=" + mobilenoecpt + "; "
				+ "prodCode=" + prodCode + "; "
				+ "userstatus=1; "
				+ "RadomTokenKey=" + RadomTokenKey + "; "
				+ "his_login_id=" + his_login_id + "; "
				+ "hawt_uinfo=" + hawt_uinfo + "; "
				+ "hanetcookid=" + hanetcookid + "; "
				+ "hnmcc2=" + hnmcc2 + "; "
				+ "activateSSOTime=" + activateSSOTime + "; "
				+ "CmLocation=371|371; CmProvid=ha; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.ha.10086.cn");
		postMethod.addHeader("Referer", "http://service.ha.10086.cn/service/self/tel-bill!detail.action?menuCode=1026");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
}
