package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_SH_APIGetMessage {


	private static String mobile = "13817251735";
	
	private static String SMSCode = "133146";	//验证码
	private String JSESSIONID = "83f14aaba66ba60fe4a71dc07690";
	private String ANsession_fullName = "ANsession0002262046133746";
	private String ANsession_value = "TYDL+e5c93291_e1e2d5aedcf79fafdd0d2f3d76536b70";
	private String AN_nav1 = "1%3dopen%262%3dbuttons%263%3dright%264%3dclosed%265%3de5c93291%26eoc";
	private String vpn_auto = "true";
	private String cmtokenid = "d9d64742ac6d4e70b5ce6fb8b9859581@sh.ac.10086.cn";
	private String yRJfzS9NAZ = "MDAwM2IyYzg3OTAwMDAwMDAwMzcwbG9OFEIxNDY2MDcxMTcx";
	private String jsessionid_fullName = "jsessionid_144_p3";
	private String jsessionid_value = "83f09d3559455989abb8fdd25a73";
	private String oodZQ7MJgF = "MDAwM2IyYzg3OTAwMDAwMDAwMzgwLyN7AwExNDY2MDcxMTcz";
	private String uid = "";
	
	private String CmWebtokenid = ""; 
	private String CmActokenid = "";
	
	public static void main(String[] args) {
		YD_SH_APIGetMessage yd = new YD_SH_APIGetMessage();

		yd.sendSMSCode3(YD_HE_PwdEncrypt.strEnc(mobile),YD_HE_PwdEncrypt.strEnc(SMSCode));
		yd.loginPost();
		yd.getDetailCallList();
	}	

	
	/**
	 * 发送短信验证码3
	 * 
	 */
	public void sendSMSCode3(String EncryptMobile,String EncryptSMSCode){
		HttpPost postMethod = new HttpPost("https://sh.ac.10086.cn/loginex");
		
		String cookie = "AN_nav1=" + AN_nav1
				+ "vpn_auto=" + vpn_auto + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ ANsession_fullName + "=" + ANsession_value + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmActokenid=" + CmActokenid + "; "
				+ "CmLocation=210|210; CmProvid=sh; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sh.10086.cn");
		postMethod.addHeader("Referer", "http://www.sh.10086.cn/sh/wsyyt/ac/loginbox.jsp?al=1&telno=" + mobile);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("act", "2"));
		params.add(new BasicNameValuePair("source", "wsyytpop"));
		params.add(new BasicNameValuePair("telno", EncryptMobile));
		params.add(new BasicNameValuePair("password", EncryptSMSCode));
		params.add(new BasicNameValuePair("validcode", ""));
		params.add(new BasicNameValuePair("authLevel", "1"));
		params.add(new BasicNameValuePair("decode", "1"));
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+cmtokenid);
				uid = c.getValue().split("@")[0];
				System.out.println("uid: "+uid);
			}
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
			}
			if(c.getName().startsWith("jsessionid")){
				jsessionid_fullName = c.getName();
				jsessionid_value = c.getValue();
				System.out.println(jsessionid_fullName + ": "+ jsessionid_value);
			}
		}
		
		System.out.println(res.getResponse());
		
	}
	
	public void loginPost(){
		HttpPost postMethod = new HttpPost("http://www.sh.10086.cn/sh/wsyyt/ac/loginpost.jsp");
		
		String cookie = "yRJfzS9NAZ=" + yRJfzS9NAZ + "; "
				+ jsessionid_fullName + "=" + jsessionid_value + "; "
				+ "CmLocation=210|210; CmProvid=sh; "
				+ "oodZQ7MJgF=" + oodZQ7MJgF + "; "
				+ "bppZudQS3v=MDAwM2IyOWYwODAwMDAwMDAwNmIwCz5pD14xNDY1OTMxNDY0; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmActokenid=" + CmActokenid + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sh.10086.cn");
		postMethod.addHeader("Referer", "http://www.sh.10086.cn/sh/wsyyt/ac/loginbox.jsp?al=1&telno=" + mobile);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("act", "2"));
		params.add(new BasicNameValuePair("ret", "0"));
		params.add(new BasicNameValuePair("message", ""));
		params.add(new BasicNameValuePair("uid", uid));

		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: " + CmWebtokenid);
			} 
			if(c.getName().equals("CmActokenid")){
				CmActokenid = c.getValue();
				System.out.println("CmActokenid: " + CmActokenid);
			} 
		}
		
		System.out.println(res.getResponse());
		
	}
	
	public void getDetailCallList(){
		HttpPost postMethod = new HttpPost("http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax");
		
		String cookie = "yRJfzS9NAZ=" + yRJfzS9NAZ + "; "
				+ jsessionid_fullName + "=" + jsessionid_value + "; "
				+ "oodZQ7MJgF=" + oodZQ7MJgF + "; "
				+ "CmLocation=210|210; CmProvid=sh; "
				+ "bppZudQS3v=MDAwM2IyOWYwODAwMDAwMDAwNmIwCz5pD14xNDY1OTMxNDY0; "
				+ "CmActokenid=" + CmActokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sh.10086.cn");
		postMethod.addHeader("Referer", "http://www.sh.10086.cn/sh/wsyyt/ac/loginbox.jsp?al=1&telno=" + mobile);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("billType", "NEW_GSM"));
		params.add(new BasicNameValuePair("startDate", "2016-05-01"));
		params.add(new BasicNameValuePair("endDate", "2016-05-31"));
		params.add(new BasicNameValuePair("filterfield", "输入对方号码："));
		params.add(new BasicNameValuePair("filterValue", ""));
		params.add(new BasicNameValuePair("searchStr", "-1"));
		params.add(new BasicNameValuePair("index", "0"));
		params.add(new BasicNameValuePair("r", System.currentTimeMillis()+""));	//1465906744611
		params.add(new BasicNameValuePair("isCardNo", "0"));
		params.add(new BasicNameValuePair("gprsType", ""));
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
