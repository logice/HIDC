package hbc315.HIDC.service.YD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;

public class YD_TJ_LoginAndSendcode {


	private String mobile = "18822121221";
	private String servicePassword = "871008";	
	private String billMonth = "201605";
	
	private String JSESSIONID = "A800C4611DF67443ABA4772D21923A2C";	
	private String rnum = "iiz9";	//验证码
	
	private String smsCode = "";
	
	public static void main(String[] args) {
		YD_TJ_LoginAndSendcode yd = new YD_TJ_LoginAndSendcode();
		
		yd.loginHandlerV2();
		yd.firstRedirect();
		yd.secondRedirect();
		yd.sendSMSCode();
	}
	
	private String cmtokenid = "";
	private String CmWebtokenid = ""; 
	private String CmSore = "";
	private String CmBrand = "";
	private String firstRedirectURL = "";
	
	/**
	 * 登录,用服务密码
	 */
	public void loginHandlerV2(){
		String url = "https://tj.ac.10086.cn/login/loginHandlerV2.jsp";
		HttpPost postMethod = new HttpPost(url);
		postMethod.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; JSESSIONID=" + JSESSIONID;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://tj.ac.10086.cn");
		postMethod.addHeader("Referer", "http://tj.ac.10086.cn/login/");
		postMethod.addHeader("Connection", "keep-alive");
		postMethod.addHeader("Cache-Control", "max-age=0");
		postMethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("issuer", "http://service.tj.10086.cn"));
		params.add(new BasicNameValuePair("RelayState", "MyHome"));
		params.add(new BasicNameValuePair("type1", "index"));
		params.add(new BasicNameValuePair("loginType", "phoneNo"));
		params.add(new BasicNameValuePair("mp", mobile));
		params.add(new BasicNameValuePair("passwordType", "service"));
		params.add(new BasicNameValuePair("password", servicePassword));
		params.add(new BasicNameValuePair("checkCode", rnum));
		params.add(new BasicNameValuePair("smsPwd", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
			}
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
			}
			if(c.getName().equals("CmSore")){
				CmSore = c.getValue();
			}
			if(c.getName().equals("CmBrand")){
				CmBrand = c.getValue();
			}
		}
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("CmSore: " + CmSore);
		System.out.println("CmBrand: " + CmBrand);
		System.out.println(res.getResponse());
		
		firstRedirectURL = res.getResponse().split("href='")[1];
		firstRedirectURL = firstRedirectURL.split("';")[0];
		System.out.println("firstRedirectURL: " + firstRedirectURL);
	}
	
	private String gFNVKdebnF = "";
	private String secondRedirectURL = "";
	
	/**
	 * 登录第一次跳转 
	 */
	@SuppressWarnings("deprecation")
	public void firstRedirect(){
		String url = firstRedirectURL;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmProvid=tj; CmWebtokenid=\"" + CmWebtokenid + "\"; CmSore=\"" + CmSore + "\"; CmBrand=\"" + CmBrand + "\"";		
		getMethod.addHeader("Cookie", cookie); 
        
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("gFNVKdebnF")){
				gFNVKdebnF = c.getValue();
			}
		}
		System.out.println("gFNVKdebnF: "+ gFNVKdebnF);
		System.out.println(res.getResponse());
		secondRedirectURL = res.getLocation();
	}
	
	private String WADE_ID = "";
	private String loginName= "";
	private String FLAG_4GUsim = "";
	private String is4Fuser = "";
	
	/**
	 * 登录第二次跳转 
	 */
	public void secondRedirect(){
		String url = secondRedirectURL;
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmProvid=tj; CmWebtokenid=\"" + CmWebtokenid + "\"; CmSore=\"" + CmSore + "\";"
				+ " CmBrand=\"" + CmBrand + "\"; gFNVKdebnF=" + gFNVKdebnF;		
		getMethod.addHeader("Cookie", cookie); 
        
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("WADE_ID")){
				WADE_ID = c.getValue();
			}
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
			}
			if(c.getName().equals("loginName")){
				loginName = c.getValue();
			}
			if(c.getName().equals("FLAG_4GUsim")){
				FLAG_4GUsim = c.getValue();
			}
			if(c.getName().equals("is4Fuser")){
				is4Fuser = c.getValue();
			}
		}
		System.out.println("WADE_ID: "+ WADE_ID);
		System.out.println("CmWebtokenid: "+ CmWebtokenid);
		System.out.println("loginName: "+ loginName);
		System.out.println("FLAG_4GUsim: "+ FLAG_4GUsim);
		System.out.println("is4Fuser: "+ is4Fuser);
		
		secondRedirectURL = res.getLocation();
		System.out.println("secondRedirectURL: " + secondRedirectURL);
	}
	
	/**
	 * 发送短信验证码 
	 */
	public void sendSMSCode(){
		String url = "http://service.tj.10086.cn/ics/ics?service=ajaxDirect/1/componant/componant/javascript/&pagename=componant&eventname=sendMessage&GOODSNAME=%E8%AF%A6%E5%8D%95&DOWHAT=QUE&ajaxSubmitType=get&ajax_randomcode=" + RandomNumUtil.getRandomNumber_16();
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmSore=\"" + CmSore + "\"; CmBrand=\"" + CmBrand + "\";"
				+ " gFNVKdebnF=" + gFNVKdebnF + "; WADE_ID=" + WADE_ID + "; rememberNum=" + mobile + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; loginName=" + loginName + "; FLAG_4GUsim=" + FLAG_4GUsim + ";"
				+ " is4Fuser=" + is4Fuser + "; CmLocation=220|220; CmProvid=tj; ";		
		getMethod.addHeader("Cookie", cookie); 
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		res.getResponse();
	}
	
}
