package hbc315.HIDC.service.YD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;


/**
 * 江苏移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_JS_SendMessage {


	private static String mobile = "13505153015";
	private static String servicePassword = "000888";	
	
	private String rnum = "36e3";	//验证码
	
	private String wt_dl123 = "BmyFA30753IEwlewBCp7GTGFwjF44ZJl";
	private String l_key = "e0f67d8adeab9cd4268db484486a235a";
	
	public static void main(String[] args) {
		YD_JS_SendMessage yd = new YD_JS_SendMessage();
		
		yd.login(mobile, servicePassword);
		yd.getJSESSIONID();
		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("wt_dl123: " + wt_dl123);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("cmjsSSOCookie: " + cmjsSSOCookie);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("AlteonP: " + AlteonP);
		System.out.println("WTCX_MY_QDCX: " + WTCX_MY_QDCX);
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("smsVerifyCodeName: " + smsVerifyCodeName);
		System.out.println("smsVerifyCodeValue: " + smsVerifyCodeValue);
	}
	
	private String cmjsSSOCookie = ""; 
	private String cmtokenid = "";
	private String CmWebtokenid = "";
	
	/**
	 * 密码登录
	 */
	public void login(String mobile ,String servicePassword){
		String url = "https://js.ac.10086.cn/jsauth/popDoorPopLogonServletNewNew";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmProvid=js; wt_dl123=" + wt_dl123;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://js.ac.10086.cn");
		postMethod.addHeader("Referer","https://js.ac.10086.cn/jsauth/dzqd/mh/index.html?v=1");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("password", servicePassword));
		params.add(new BasicNameValuePair("icode", rnum));
		params.add(new BasicNameValuePair("l_key", l_key));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("cmjsSSOCookie")){
				cmjsSSOCookie = c.getValue();
				System.out.println("cmjsSSOCookie: "+ cmjsSSOCookie);
			}
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+ cmtokenid);
			}
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+ CmWebtokenid);
			}
		}
		
		System.out.println(res.getResponse());
	}


	private String AlteonP = "";
	private String JSESSIONID = "";
	
	/**
	 * 获取JSESSIONID
	 */
	public void getJSESSIONID(){
		String url = "http://service.js.10086.cn/market/queryBroadbandMarketForCommon.do";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "wt_dl123=" + wt_dl123 + "; "
				+ "cmjsSSOCookie=" + cmjsSSOCookie + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "so_dl234=\"\";"
				//+ "AlteonP=" + AlteonP + "; "
				//+ "JSESSIONID=" + JSESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmProvid=js";
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","http://service.js.10086.cn");
		postMethod.addHeader("Referer","http://service.js.10086.cn/index.html");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("cityCode", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: "+ JSESSIONID);
			}
			if(c.getName().equals("AlteonP")){
				AlteonP = c.getValue();
				System.out.println("AlteonP: "+ AlteonP);
			}
		}
		
		System.out.println(res.getResponse());
	}

	private String WTCX_MY_QDCX = "";
	private String smsVerifyCodeName = "";
	private String smsVerifyCodeValue = "";
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		WTCX_MY_QDCX = "MY_QDCX+" + System.currentTimeMillis();
		
		String url = "http://service.js.10086.cn/my/sms.do";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "wt_dl123=" + wt_dl123 + "; "
				+ "cmjsSSOCookie=" + cmjsSSOCookie + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "AlteonP=" + AlteonP + "; "
				+ "yjcxFlag=1; onedayonetime=1; CmProvid=js; "
				+ "topUserMobile=" + mobile + "; "
				+ "city=" + "NJDQ" + "; "
				+ "WTCX_MY_QDCX=" + WTCX_MY_QDCX + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid;
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.js.10086.cn");
		postMethod.addHeader("Referer", "http://service.js.10086.cn/my/MY_QDCX.html?t=1466480247224");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("busiNum", "QDCX"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().contains("smsVerifyCode")){
				smsVerifyCodeName = c.getName();
				smsVerifyCodeValue = c.getValue();
			}
		}
		
		System.out.println(res.getResponse());
	}
}
