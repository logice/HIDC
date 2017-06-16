package hbc315.HIDC.service.YD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_SH_SendMessage {


	private static String mobile = "13817251735";
	private static String servicePassword = "237326";	
	
	private String rnum = "gdmymp";	//验证码
	private String JSESSIONID = "83d236d12841280997314591f369";
	private String ANsession_fullName = "ANsession0002262046133746";
	private String ANsession_value = "TYDL+e5c93291_e1e2d5aedcf79fafdd0d2f3d76536b70";
	private String AN_nav1 = "1%3dopen%262%3dbuttons%263%3dright%264%3dclosed%265%3de5c93291%26eoc";
	private String vpn_auto = "true";
	
	public static void main(String[] args) {
		YD_SH_SendMessage yd = new YD_SH_SendMessage();

		yd.prepare1();
		yd.prepare2();
		yd.act11();
		yd.login(YD_HE_PwdEncrypt.strEnc(mobile),YD_HE_PwdEncrypt.strEnc(servicePassword));
		yd.getToken1();
		yd.act11();
		yd.getToken2();
		yd.sendSMSCode1();
		yd.sendSMSCode2();
		yd.out();
	}
	
	private void out(){
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("ANsession_fullName: " + ANsession_fullName);
		System.out.println("ANsession_value: " + ANsession_value);
		System.out.println("AN_nav1: " + AN_nav1);
		System.out.println("vpn_auto: " + vpn_auto);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("yRJfzS9NAZ: " + yRJfzS9NAZ);
		System.out.println("jsessionid_fullName: " + jsessionid_fullName);
		System.out.println("jsessionid_value: " + jsessionid_value);
		System.out.println("oodZQ7MJgF: " + oodZQ7MJgF);
	}
	
	private String yRJfzS9NAZ = "";
	private String jsessionid_fullName = "";
	private String jsessionid_value = "";
	private String oodZQ7MJgF = "";
	
	public void prepare1(){
		String url = "http://www.sh.10086.cn/sh/service/";
		HttpGet getMethod = new HttpGet(url);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("oodZQ7MJgF")){
				oodZQ7MJgF = c.getValue();
				System.out.println("oodZQ7MJgF: "+oodZQ7MJgF);
			}
		}
	}
			
	public void prepare2(){
		String url = "http://www.sh.10086.cn/dportal/portalwlanhot.do?method=getBuyPhone";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "oodZQ7MJgF=" + oodZQ7MJgF + "; ";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "http://www.sh.10086.cn/sh/service/");
		postMethod.addHeader("Origin", "http://www.sh.10086.cn");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("yRJfzS9NAZ")){
				yRJfzS9NAZ = c.getValue();
				System.out.println("yRJfzS9NAZ: "+yRJfzS9NAZ);
			}
			if(c.getName().startsWith("jsessionid")){
				jsessionid_fullName = c.getName();
				jsessionid_value = c.getValue();
				System.out.println(jsessionid_fullName + ": "+ jsessionid_value);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	private String cmtokenid = "";
	private String uid = "";
	
	/**
	 * 登录
	 */
	public void login(String EncryptMobile,String EncryptPassword){
		String url = "https://sh.ac.10086.cn/loginex?act=2";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = ANsession_fullName + "=" + ANsession_value + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "AN_nav1=" + AN_nav1 + "; "
				+ "vpn_auto=" + vpn_auto + "; ";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "https://sh.ac.10086.cn/login");
		postMethod.addHeader("Origin", "https://sh.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("telno", EncryptMobile));
		params.add(new BasicNameValuePair("password", EncryptPassword));
		params.add(new BasicNameValuePair("authLevel", "2"));
		params.add(new BasicNameValuePair("validcode", rnum));
		params.add(new BasicNameValuePair("ctype", "1"));
		params.add(new BasicNameValuePair("decode", "1"));
		params.add(new BasicNameValuePair("source", "wsyyt"));	
		
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
		}
		
		System.out.println(res.getResponse());
	}
	
	
	/**
	 * 等待
	 */
	public void act11(){
		String url = "https://sh.ac.10086.cn/loginex?act=11";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = ANsession_fullName + "=" + ANsession_value + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "AN_nav1=" + AN_nav1 + "; "
				+ "vpn_auto=" + vpn_auto + "; ";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "https://sh.ac.10086.cn/login");
		postMethod.addHeader("Origin", "https://sh.ac.10086.cn");

		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
	

	private String CmWebtokenid = ""; 
	private String CmActokenid = "";
	 
	/**
	 * 拿取登录成功后的各种token
	 * 
	 */
	public void getToken1(){
		String url = "http://www.sh.10086.cn/sh/wsyyt/ac/forward.jsp?uid=" + uid + "&http%3A%2F%2Fwww.sh.10086.cn%2Fsh%2Fmy%2F";
		HttpGet getMethod = new HttpGet(url);
		//32163
		String cookie = "CmLocation=210|210; CmProvid=sh; WEBTRENDS_ID=40.40.40.215-362036352.30525313; "
				+ "WT_FPC=id=25ae99e677496dc60b41465911966182:lv=" + System.currentTimeMillis() + ":ss=" + (System.currentTimeMillis()-32163);
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("yRJfzS9NAZ")){
				yRJfzS9NAZ = c.getValue();
				System.out.println("yRJfzS9NAZ: "+yRJfzS9NAZ);
			}
			if(c.getName().startsWith("jsessionid")){
				jsessionid_fullName = c.getName();
				jsessionid_value = c.getValue();
				System.out.println(jsessionid_fullName + ": "+ jsessionid_value);
			}
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+CmWebtokenid);
			}
			if(c.getName().equals("CmActokenid")){
				CmActokenid = c.getValue();
				System.out.println("CmActokenid: "+CmActokenid);
			}
		}
		System.out.println(res.getResponse());
		
	}

	
	/**
	 * getToken2
	 * 
	 */
	public void getToken2(){
		HttpGet getMethod = new HttpGet("http://www.sh.10086.cn/sh/my/");
		
		String cookie = "yRJfzS9NAZ=" + yRJfzS9NAZ + "; "
				+ jsessionid_fullName + "=" + jsessionid_value + "; "
				+ "CmProvid=sh; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmActokenid=" + CmActokenid + "; ";
		getMethod.addHeader("Cookie", cookie); 
		getMethod.addHeader("Referer", "http://www.sh.10086.cn/sh/wsyyt/ac/forward.jsp?uid=" + uid + "&tourl=http%3A%2F%2Fwww.sh.10086.cn%2Fsh%2Fmy%2F");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("oodZQ7MJgF")){
				oodZQ7MJgF = c.getValue();
			}
		}
		System.out.println("oodZQ7MJgF: " + oodZQ7MJgF);
		
	}
	
	
	/**
	 * 发送短信验证码1
	 * 
	 */
	public void sendSMSCode1(){
		HttpPost postMethod = new HttpPost("https://sh.ac.10086.cn/loginex");
		
		String cookie = "AN_nav1=" + AN_nav1 + "; "
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
		params.add(new BasicNameValuePair("act", "1"));
		params.add(new BasicNameValuePair("source", "wsyytpop"));
		params.add(new BasicNameValuePair("telno", mobile));
		params.add(new BasicNameValuePair("password", ""));
		params.add(new BasicNameValuePair("validcode", ""));
		params.add(new BasicNameValuePair("authLevel", ""));
		params.add(new BasicNameValuePair("decode", "1"));
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
			}
		}
		
		System.out.println(res.getResponse());
		
	}
	/**
	 * 发送短信验证码2
	 * 
	 */
	public void sendSMSCode2(){
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
		params.add(new BasicNameValuePair("act", "1"));
		params.add(new BasicNameValuePair("ret", "0"));
		params.add(new BasicNameValuePair("message", "动态密码已经发送到您的手机上，有效期为30分钟，请注意查收；如果未收到请稍候再重试。"));
		params.add(new BasicNameValuePair("uid", ""));

		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		
		System.out.println(res.getResponse());
		
	}
	
}
