package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;
import hbc315.HIDC.util.YD_CQ_PwdEncrypt;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_CQ_SendMessage {


	private static String mobile = "15723030546";
	private static String servicePassword = "174084";	
	
	private String rnum = "r88n";	//验证码
	
	private String WADE_ID = "D1CDE079F0F14CFEA5270DCFAF5A296F";
	private String SESSION_ID = "tFYi7cXDIXCn4tKTZ3dRCiv";
	private String SESSIONID_S52SATURN01 = "0000tFYi7cXDIXCn4tKTZ3dRCiv:15qn4dpvm";
	private String jsessionid1 = "RHrKXncR7DZlcnV2XJK9k8G1G1fDcJtjmgnpDkrlBjq8KGnhKrQQ!-872977531";
	private String BIGipServertyrz_pool = "1921564426.23323.0000";
	
	private String IPAddress = "12442101174wd_user_ip";
	
	public static void main(String[] args) {
		YD_CQ_SendMessage yd = new YD_CQ_SendMessage();

		yd.login(mobile,YD_CQ_PwdEncrypt.strEnc(servicePassword,mobile.substring(0, 8),mobile.substring(1, 9),mobile.substring(3, 11)));
		yd.SSOLogin(mobile, servicePassword);
		yd.getUID();
		yd.getloginInfo();
		
		yd.initGoodsID();
		yd.prepareToSendSMSCode();
		yd.sendSMSCode();
		yd.out();
	}
	
	private void out(){
		System.out.println("WADE_ID: " + WADE_ID);
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("SESSION_ID: " + SESSION_ID);
	}
	
	/**
	 * 第一次登录
	 */
	public void login(String mobile ,String EncryptPassword){
		String url = "https://service.cq.10086.cn/ics?service=ajaxDirect/1/login/login/"
				+ "javascript/&pagename=login&eventname=SSOlogin&cond_REMEMBER_TAG=false"
				+ "&cond_LOGIN_TYPE=2&cond_SERIAL_NUMBER="+ mobile +"&"
				+ "cond_USER_PASSWD=" + EncryptPassword + "&cond_USER_PASSSMS="
				+ "&cond_VALIDATE_CODE=" + rnum + "&ajaxSubmitType=post&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://service.cq.10086.cn");
		postMethod.addHeader("Referer","https://service.cq.10086.cn/httpsFiles/pageLogin.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		System.out.println(res.getResponse());
	}


	private String CmWebtokenid = ""; 
	private String cmtokenid = "";
	private String SAMLart = "";
	
	/**
	 * 第二次登录ac
	 */
	public void SSOLogin(String mobile, String servicePassword){
		String url = "https://cq.ac.10086.cn/SSO/loginbox";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "SESSION_ID=" + SESSION_ID + "; jsessionid1=" + jsessionid1 + "; "
				+ "BIGipServertyrz-pool=" + BIGipServertyrz_pool;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://service.cq.10086.cn");
		postMethod.addHeader("Referer","https://service.cq.10086.cn/httpsFiles/pageLogin.html");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("service", "CHOQ"));
		params.add(new BasicNameValuePair("failUrl", "http%3A%2F%2Fservice.cq.10086.cn%2FCHOQ%2Fauthentication%2Fauthentication_error.jsp"));
		params.add(new BasicNameValuePair("username", mobile));
		params.add(new BasicNameValuePair("password", servicePassword));
		params.add(new BasicNameValuePair("passwordType", "2"));
		params.add(new BasicNameValuePair("validateCode", rnum));
		params.add(new BasicNameValuePair("smsRandomCode", servicePassword));
		
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
		}
		try {
			SAMLart = YD_XPath.readSAMLart_CQ(res.getResponse());
		} catch (Exception e) {
			System.out.println("获取SAMLart失败");
		}
		System.out.println("SAMLart: " + SAMLart);
		
		System.out.println(res.getResponse());
	}

	private String UID = "";
	private String getUIDURL = "";
	/**
	 * 获取UID
	 * 
	 */
	public void getUID(){
		getUIDURL = "http://service.cq.10086.cn/CHOQ/authentication/authentication_return.jsp?timeStamp=" + System.currentTimeMillis();
		HttpPost postMethod = new HttpPost(getUIDURL);
		
		String cookie = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID + "; CmWebtokenid=" + CmWebtokenid;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://cq.ac.10086.cn");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("RelayState", ""));
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("PasswordType", "2"));
		params.add(new BasicNameValuePair("errorMsg", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		

		UID = YD_XPath.readUID_CQ(res.getResponse());
		System.out.println("UID: " + UID);
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * getloginInfo
	 */
	public void getloginInfo(){
		String url = "http://service.cq.10086.cn/ics?service=page/login&listener=getLoginInfo";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID + "; CmWebtokenid=" + CmWebtokenid;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.cq.10086.cn");
		postMethod.addHeader("Referer", getUIDURL);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SERIAL_NUMBER", mobile));
		params.add(new BasicNameValuePair("flag", "Success"));
		params.add(new BasicNameValuePair("errorInfo", "%E8%8E%B7%E5%8F%96%E5%87%AD%E8%AF%81%E4%BF%A1%E6%81%AF%E6%88%90%E5%8A%9F"));
		params.add(new BasicNameValuePair("UID", UID));
		
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
		}
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * 初始化GoodsID
	 * 
	 */
	public void initGoodsID(){
		String url = "http://service.cq.10086.cn/ics?service=ajaxDirect/1/home/home/javascript/&pagename=home&eventname=initGoodsId&cond_GOODS_ENAME=XFMX&ajaxSubmitType=post&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID + "; CmWebtokenid=" + CmWebtokenid;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin","https://service.cq.10086.cn");
		postMethod.addHeader("Referer","http://service.cq.10086.cn/myMobile/detailBill.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		System.out.println("initGoodsID: " + res.getResponse());
		
	}
	
	private void prepareToSendSMSCode(){
		String url = "http://www.cq.10086.cn/saturn/app?service=page/Home&listener=getCustInfo&"
				+ "CHAN_ID=E003&TELNUM=&?idsite=1&rec=1&url=http%3A%2F%2Fservice.cq.10086.cn%2F"
				+ "myMobile%2FdetailBill.html&res=1366x768&col=24-bit&h=11&m=9&s=31&cookie=1&"
				+ "urlref=http%3A%2F%2Fservice.cq.10086.cn%2F&rand="+RandomNumUtil.getRandomNumber_16()+"&pdf=1&"
				+ "qt=0&realp=0&wma=0&dir=0&fla=1&java=1&gears=0&ag=0&action_name=%2525E9%2525"
				+ "80%25259A%2525E8%2525AF%25259D%2525E8%2525AF%2525A6%2525E5%25258D%252595-%25"
				+ "25E5%2525B0%25258A%2525E4%2525BA%2525AB%2525E6%25259C%25258D%2525E5%25258A%25"
				+ "25A1-%2525E9%252587%25258D%2525E5%2525BA%252586%2525E7%2525A7%2525BB%2525E5%"
				+ "25258A%2525A8";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "SESSIONID_S52SATURN01=" + SESSIONID_S52SATURN01 + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "SESSION_ID=" + SESSION_ID + "; "
				+ "CALLER1=" + SESSION_ID + "; "
				+ "CmLocation=230|230; CmProvid=cq";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("SESSION_ID")) {
				SESSION_ID = c.getValue();
				System.out.println("SESSION_ID: " + SESSION_ID);
			}
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "http://service.cq.10086.cn/ics?service=ajaxDirect/1/secondValidate"
				+ "/secondValidate/javascript/&pagename=secondValidate&eventname=getTwoVerification&"
				+ "GOODSNAME=%E7%94%A8%E6%88%B7%E8%AF%A6%E5%8D%95&DOWHAT=QUE&"
				+ "ajaxSubmitType=post&ajax_randomcode=" + System.currentTimeMillis();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ IPAddress + "=" + IPAddress + "; "
				+ "loginName=" + mobile + "; "
				+ "CmLocation=230|230; CmProvid=cq ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin","https://service.cq.10086.cn");
		postMethod.addHeader("Referer","http://service.cq.10086.cn/myMobile/detailBill.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		System.out.println(res.getResponse());
	}
}
