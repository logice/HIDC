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


/**
 * 查询移动用户通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_BJ_APIGetMessage {


	private String mobile = "13701346824";
	private String password = "989977";
	private String servicePassword = "920408";	
	private String billMonth = "2016.04";
	
	private String JSESSIONID = "0000NSUsmKqpI6GbYhcQVOUoGyX:16vf1jlcr";
	
	private String rnum = "W5ESK";	//验证码
	private String smsNum = "783837";	//手机验证码
	
	public static void main(String[] args) {
		YD_BJ_APIGetMessage yd = new YD_BJ_APIGetMessage();
		
//		yd.validateRnum();
//		yd.ssoLogin_Password();
		
		yd.ssoLogin_TempPwd();
		yd.getToken();
		
		yd.loginSecondPre1();
		yd.loginSecondPre2();
		yd.loginSecondPre3();
		yd.checkServicePassword();
		yd.getDetailBill("2016.04");

	}
	
	public String Webtrends = "124.42.101.174.1462763452300407";

	/**
	 * 获取10086的JSESSIONID
	 * 
	 */
	public void getJSESSIONID() {
		String url = "https://bj.ac.10086.cn/ac/cmsso/iloginnew.jsp";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=100|100; CmProvid=bj; Webtrends="+ Webtrends;
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
	
	//验证IP，貌似非必须
	public void validateIp(){
		String url = "https://bj.ac.10086.cn/ac/ValidateIp";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=100|100; CmProvid=bj; Webtrends=" + Webtrends + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+"WT_FPC=id=292841a006ff8dab69b1462719950643:lv=1462719968343:ss=1462719950643"
				+ "login_mobile=" + mobile + "; c_mobile=" + mobile + "";
		
		postMethod.setHeader("Cookie",cookie);
		postMethod.setHeader("Origin","https://bj.ac.10086.cn");
		postMethod.setHeader("Referer","https://bj.ac.10086.cn/ac/cmsso/iloginnew.jsp");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ceshi", "false"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CommonHttpMethod.doPost(postMethod);
	}
	
	/**
	 * 检查验证码
	 */
	public void validateRnum(){
		String url = "https://bj.ac.10086.cn/ac/ValidateRnum";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=100|100; CmProvid=bj; Webtrends=" + Webtrends + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+"WT_FPC=id=292841a006ff8dab69b1462719950643:lv=1462719968343:ss=1462719950643"
				+ "login_mobile=" + mobile + "; c_mobile=" + mobile + "";
		
		postMethod.setHeader("Cookie",cookie);
		postMethod.setHeader("Origin","https://bj.ac.10086.cn");
		postMethod.setHeader("Referer","https://bj.ac.10086.cn/ac/cmsso/iloginnew.jsp");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("rnum", rnum));
		params.add(new BasicNameValuePair("user", mobile));
		params.add(new BasicNameValuePair("phone", mobile));
		params.add(new BasicNameValuePair("service", "www.bj.10086.cn"));
		params.add(new BasicNameValuePair("loginMethod", "1"));
		params.add(new BasicNameValuePair("loginMode", "1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
	
	private String ssoRedirect = "";
	
	/**
	 * 单点登录,用网站密码
	 */
	public void ssoLogin_Password(){
		String url = "https://bj.ac.10086.cn/ac/CmSsoLogin";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "Webtrends=" + Webtrends + "; JSESSIONID=" + JSESSIONID + "; "
				+ "login_mobile=" + mobile + "; c_mobile=" + mobile + ";";
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", mobile));
		params.add(new BasicNameValuePair("phone", mobile));
		params.add(new BasicNameValuePair("backurl", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("continue", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("style", "BIZ_LOGINBOX"));
		params.add(new BasicNameValuePair("service", "www.bj.10086.cn"));
		params.add(new BasicNameValuePair("box", ""));
		params.add(new BasicNameValuePair("target", "_parent"));
		params.add(new BasicNameValuePair("ssoLogin", "yes"));
		params.add(new BasicNameValuePair("loginMode", "1"));
		params.add(new BasicNameValuePair("loginMethod", "1"));
		params.add(new BasicNameValuePair("loginName", mobile));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("smsNum", "%CB%E6%BB%FA%C2%EB"));		//?
		params.add(new BasicNameValuePair("rnum", rnum));			//?
		params.add(new BasicNameValuePair("ckCookie", "on"));	
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		ssoRedirect = res.getLocation();
		if(ssoRedirect == null || ssoRedirect.equals("")){
			ssoLoginAgain();
		}
	}
	
	/**
	 * 单点登录,用手机验证码
	 */
	public void ssoLogin_TempPwd(){
		String url = "https://bj.ac.10086.cn/ac/CmSsoLogin";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "Webtrends=" + Webtrends + "; JSESSIONID=" + JSESSIONID + "; "
				+ "login_mobile=" + mobile + "; c_mobile=" + mobile + ";";
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", mobile));
		params.add(new BasicNameValuePair("phone", mobile));
		params.add(new BasicNameValuePair("backurl", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("continue", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("style", "BIZ_LOGINBOX"));
		params.add(new BasicNameValuePair("service", "www.bj.10086.cn"));
		params.add(new BasicNameValuePair("box", ""));
		params.add(new BasicNameValuePair("target", "_parent"));
		params.add(new BasicNameValuePair("ssoLogin", "yes"));
		params.add(new BasicNameValuePair("loginMode", "2"));
		params.add(new BasicNameValuePair("loginMethod", "1"));
		params.add(new BasicNameValuePair("loginName", mobile));
		params.add(new BasicNameValuePair("password", ""));
		params.add(new BasicNameValuePair("smsNum", smsNum));
		params.add(new BasicNameValuePair("rnum", ""));
		params.add(new BasicNameValuePair("ckCookie", "on"));	
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		ssoRedirect = res.getLocation();
		if(ssoRedirect == null || ssoRedirect.equals("")){
			ssoLoginAgain();
		}
	}
	
	/**
	 * 如果已经登录了，踢出再次登录
	 */
	public void ssoLoginAgain(){
		String url = "https://bj.ac.10086.cn/ac/loginAgain";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "Webtrends=" + Webtrends + "; JSESSIONID=" + JSESSIONID + "; "
				+ "login_mobile=" + mobile + "; c_mobile=" + mobile + "; CmLocation=100|100; CmProvid=bj";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "https://bj.ac.10086.cn/ac/CmSsoLogin");
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("backurl", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("continue", "http%3A%2F%2Fwww.bj.10086.cn%2Fmy"));
		params.add(new BasicNameValuePair("style", "BIZ_LOGINBOX"));
		params.add(new BasicNameValuePair("service", "www.bj.10086.cn"));
		params.add(new BasicNameValuePair("box", ""));
		params.add(new BasicNameValuePair("target", "_self"));
		params.add(new BasicNameValuePair("loginMode", "1"));
		params.add(new BasicNameValuePair("loginMethod", "1"));	
		params.add(new BasicNameValuePair("hostId", "4"));
		params.add(new BasicNameValuePair("submit", ""));	
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		ssoRedirect = res.getLocation();
		System.out.println("Force Login Again!");
	}
	
	private String cmtokenid = "";
	private String CmWebtokenid = ""; 
	private String  charset = "";
	private String  SSOTime = "";
	private String mobileNo1 = "";
	private String ssoSessionID = "2c9d82fa547a732401549571b0747565";
	
	/**
	 * 拿取登录成果后的各种token
	 * 
	 */
	public void getToken(){
		if(!ssoRedirect.equals("")){
			HttpGet getMethod = new HttpGet(ssoRedirect);
			
			String cookie = "Webtrends=" + Webtrends + "; JSESSIONID=" + JSESSIONID + "; "
					+ "login_mobile=" + mobile + "; c_mobile=" + mobile + ";";
			
			getMethod.addHeader("Cookie", cookie);
			
			ResponseValue res = CommonHttpMethod.doGet(getMethod);		
			
			for(Cookie c : res.getCookies()){
				if(c.getName().equals("cmtokenid")){
					cmtokenid = c.getValue();
					ssoSessionID = c.getValue().split("@")[0];
					System.out.println("cmtokenid: "+cmtokenid);
					System.out.println("ssoSessionID: "+ssoSessionID);
				}
				
				if(c.getName().equals("CmWebtokenid")){
					CmWebtokenid = c.getValue();
					System.out.println("CmWebtokenid: "+ CmWebtokenid);
				}
				
				if(c.getName().equals("mobileNo1")){
					mobileNo1 = c.getValue();
					System.out.println("mobileNo1: "+mobileNo1);
				}
				
				if(c.getName().equals("SSOTime")){
					SSOTime = c.getValue();
					System.out.println("SSOTime: "+SSOTime);
				}
				
				if(c.getName().equals("charset")){
					charset = c.getValue();
					System.out.println("charset: "+charset);
				}
			}
		}
		
	}
	
	private String SAMLRequest = "";
	private String cmodsvr1_JSESSIONID = "";
	
	/**
	 * 用10086的JSESSIONID，换取cmodsvr1.bj.chinamobile.com的JSESSIONID
	 * 
	 */
	public void loginSecondPre1(){
		HttpGet getMethod = new HttpGet("https://cmodsvr1.bj.chinamobile.com/PortalCMOD/InnerInterFaceCiisHisBill");
		
		String cookie = "JSESSIONID=" + JSESSIONID;		
		getMethod.addHeader("Cookie", cookie); 
		getMethod.addHeader("Host", "cmodsvr1.bj.chinamobile.com");
		getMethod.addHeader("Referer", "http://www.bj.10086.cn/service/fee/zdcx/");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		//预处理
		res.setResponse(res.getResponse().replaceAll("=post", "=\"post\""));
		res.setResponse("<html>"+res.getResponse().trim()+"</html>");
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLRequest = YD_XPath.readSAMLRequest_BJ(inputResult);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				cmodsvr1_JSESSIONID = c.getValue();
			}
		}
		
		System.out.println("SAMLRequest: "+SAMLRequest);
		
	}
	
	private String SAMLart = "";
	
	/**
	 * 获取SAMLart
	 * 
	 */
	public void loginSecondPre2(){
		HttpPost postMethod = new HttpPost("https://bj.ac.10086.cn/ac/SamlCmAuthnResponse");
		
		String cookie = "CmLocation=100|100; CmProvid=bj; mobileNo1=" + mobileNo1 + "; charset=" + charset + "; "
				+ "realname=true; cmtokenid=" + cmtokenid + "; CmWebtokenid=" + CmWebtokenid + "; "
				+ "SSOTime=" + SSOTime + "; continue=http://www.bj.10086.cn/service/fee/zdcx/; "
				+ "continuelogout=http://www.bj.10086.cn/service/fee/zdcx/; "
				+ "Webtrends=" + Webtrends + "; c_mobile=" + mobile + "; "
				+ "JSESSIONID=" + JSESSIONID + "; login_mobile="+ mobile;		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Host", "bj.ac.10086.cn");
		postMethod.addHeader("Referer", "https://cmodsvr1.bj.chinamobile.com/PortalCMOD/InnerInterFaceCiisHisBill");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLRequest", SAMLRequest));
		params.add(new BasicNameValuePair("RelayState", "InnerInterFaceCiisHisBill"));
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
		
		//预处理
		res.setResponse(res.getResponse().replaceAll("<title>", "</meta><title>"));
		
		res.setResponse(res.getResponse().trim());
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLart = YD_XPath.readSAMLart(inputResult);
		
		System.out.println("SAMLart: "+SAMLart);
		
	}
			
	/**
	 * 验证SAMLart
	 * 
	 */
	public void loginSecondPre3(){
		HttpPost postMethod = new HttpPost("https://cmodsvr1.bj.chinamobile.com/PortalCMOD/Login_Success.jsp?timemilllis="+System.currentTimeMillis());
		
		String cookie = "JSESSIONID=" + cmodsvr1_JSESSIONID;		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Host", "cmodsvr1.bj.chinamobile.com");
		postMethod.addHeader("Referer", "https://bj.ac.10086.cn/ac/SamlCmAuthnResponse");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("RelayState", "InnerInterFaceCiisHisBill"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
	}		
	
	/**
	 * 业务查询登录验证
	 * 
	 */
	public void checkServicePassword(){
		HttpPost postMethod = new HttpPost("https://cmodsvr1.bj.chinamobile.com/PortalCMOD/LoginSecondCheck?ssoSessionID="+ssoSessionID);
		
		String cookie = "JSESSIONID=" + cmodsvr1_JSESSIONID;		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Host", "cmodsvr1.bj.chinamobile.com");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("searchType", "HisDetail"));
		params.add(new BasicNameValuePair("detailType", "GSM"));
		params.add(new BasicNameValuePair("password", servicePassword));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);		
	}
	
	/**
	 * 获取通话详单
	 * 
	 */
	public void getDetailBill(String billMonth){
		HttpGet getMethod = new HttpGet("https://cmodsvr1.bj.chinamobile.com/PortalCMOD"
				+ "/detail/userdetailall.do?ssoSessionID=" + ssoSessionID + "&Month=" + billMonth + "&detailType=GSM");
		//Referer: https://cmodsvr1.bj.chinamobile.com/PortalCMOD/detail/detail_all.jsp?checkMonth=2016.04&detailType=RC&ssoSessionID=2c9d82fa547a732401549425e0f669dd&sMobileType=3
		String cookie = "JSESSIONID=" + cmodsvr1_JSESSIONID;		
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		
		
		System.out.println(res.getResponse());
		
	}
	
}
