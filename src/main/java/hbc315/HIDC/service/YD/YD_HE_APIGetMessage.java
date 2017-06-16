package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class YD_HE_APIGetMessage {

	private String mobile = "15039414655";
	private String servicePassword = "989977";	
	private String billMonth = "2016.04";
	
	private String JSESSIONID = "pBDJXf4WhpcgWLLtVzN4DY1dPbgxtLZ8v2GNLL1QkrylwkSJZBkr!-1190264081";
	private String ac_BIGipServerPOOL_SSO_80 = "600406026.36895.0000";
	
	private String rnum = "ktgd";	//图片验证码
	private String smsNum = "";	//手机验证码
	
	public static void main(String[] args) {
		YD_HE_APIGetMessage yd_he = new YD_HE_APIGetMessage();
		
		//yd_he.firstLogin();
		
		yd_he.validateRnum();
		yd_he.login_Password(YD_HE_PwdEncrypt.strEnc("989977"));
		yd_he.getHESession();
		yd_he.getSAMLRequest();
		yd_he.getSAMLart();
		yd_he.firstLogin();
		//=======以上ok
		yd_he.getJSESSIONIDSHOPPING_NEW();
		
		yd_he.getSAMLRequestAgain();
		yd_he.getSAMLart();
		yd_he.initLogin();
		
		yd_he.getQueryServiceb();
		yd_he.SSOCheck();
		yd_he.loginAgainValidate();
		yd_he.sendSMSCode();
		//yd_he.getDetailBill();
	}
	
	
	private String Webtrends = "124.42.101.174.1462763452300407";
	
	public void validateRnum(){
		String url = "https://he.ac.10086.cn/validImageCode?r_0.7878860994242132&imageCode=" + rnum;
		HttpGet getMethod = new HttpGet(url);

		String cookies = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f62860"
				+ "71369dcbfb4f48848d55a2a8841e69c@@1462762611817; CmLocation=100|100; "
				+ "CmProvid=he; JSESSIONID=" + JSESSIONID + "; BIGipServerPOOL-SSO-80 =" + ac_BIGipServerPOOL_SSO_80;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		System.out.println(res.getResponse());
	}
	
	private String sendRom = "";
	private String cmtokenid = "";
	private String CmWebtokenid = "";
	private String U_C_VS = "";
	private String SSO_SID = "";
	
	private String SAMLart = "";
	private String RelayState = "";
	
	private String rediect = "";
	/**
	 * 单点登录,用网站密码
	 */
	public void login_Password(String servicePassword){
		System.out.println("login_Password");
		String url = "https://he.ac.10086.cn/Login";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; BIGipServerPOOL-SSO-80="+ ac_BIGipServerPOOL_SSO_80;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "https://he.ac.10086.cn/login");
		postMethod.addHeader("Origin", "https://he.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("displayPics", "mobile_sms_login:0===sendSMS:0===mobile_servicepasswd_login:0"));
		params.add(new BasicNameValuePair("displayPic", "1"));
		params.add(new BasicNameValuePair("type", "B"));
		params.add(new BasicNameValuePair("formertype", "B"));
		params.add(new BasicNameValuePair("backurl", "https://he.ac.10086.cn/hblogin/backPage.jsp"));
		params.add(new BasicNameValuePair("warnurl", "https://he.ac.10086.cn/hblogin/warnPage.jsp"));
		params.add(new BasicNameValuePair("spid", "8af849a33630f66201363197d42b0006"));
		params.add(new BasicNameValuePair("RelayState", "type=B;backurl=http://www.he.10086.cn/my;nl=3;loginFrom=http://www.he.10086.cn/my"));
		params.add(new BasicNameValuePair("mobileNum", mobile));
		params.add(new BasicNameValuePair("userIdTemp", mobile));
		params.add(new BasicNameValuePair("servicePassword", servicePassword));
		params.add(new BasicNameValuePair("emailPwd", ""));
		params.add(new BasicNameValuePair("smsValidCode", ""));
		params.add(new BasicNameValuePair("login_pwd_type", ""));	
		params.add(new BasicNameValuePair("email", "输入Email邮箱地址"));	
		params.add(new BasicNameValuePair("validCode", rnum));	
		params.add(new BasicNameValuePair("emailPwd", "请输入密码"));	
		params.add(new BasicNameValuePair("servicePassword", "请输入6位数字的服务密码"));	
		params.add(new BasicNameValuePair("smsValidCode", ""));	
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("sendRom")){
				sendRom = c.getValue();
				System.out.println("sendRom: "+sendRom);
			}			
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+ cmtokenid);
			}	
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+ CmWebtokenid);
			}			
			if(c.getName().equals("U_C_VS")){
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+U_C_VS);
			}			
			if(c.getName().equals("SSO_SID")){
				SSO_SID = c.getValue();
				System.out.println("SSO_SID: "+SSO_SID);
			}

		}
		
		if(res.getLocation()!=null){
			redirect(res.getLocation());
		}
		
		System.out.println(res.getResponse());
		
		res.setResponse(res.getResponse().trim());
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		
		SAMLart = YD_XPath.readSAMLart(inputResult);
		System.out.println("SAMLart: " + SAMLart);
		
		try {
			inputResult.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RelayState = YD_XPath.readRelayState_HE(inputResult);
		System.out.println("RelayState: " + RelayState);
		

	}
	
	public void redirect(String url){
		System.out.println("redirect");
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; BIGipServerPOOL-SSO-80="+ ac_BIGipServerPOOL_SSO_80;
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://he.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("displayPics", "mobile_sms_login:0===sendSMS:0===mobile_servicepasswd_login:0"));
		params.add(new BasicNameValuePair("displayPic", "1"));
		params.add(new BasicNameValuePair("type", "B"));
		params.add(new BasicNameValuePair("formertype", "B"));
		params.add(new BasicNameValuePair("backurl", "https://he.ac.10086.cn/hblogin/backPage.jsp"));
		params.add(new BasicNameValuePair("warnurl", "https://he.ac.10086.cn/hblogin/warnPage.jsp"));
		params.add(new BasicNameValuePair("spid", "8af849a33630f66201363197d42b0006"));
		params.add(new BasicNameValuePair("RelayState", "type=B;backurl=http://www.he.10086.cn/my;nl=3;loginFrom=http://www.he.10086.cn/my"));
		params.add(new BasicNameValuePair("mobileNum", mobile));
		params.add(new BasicNameValuePair("userIdTemp", mobile));
		params.add(new BasicNameValuePair("servicePassword", servicePassword));
		params.add(new BasicNameValuePair("emailPwd", ""));
		params.add(new BasicNameValuePair("smsValidCode", ""));
		params.add(new BasicNameValuePair("login_pwd_type", ""));	
		params.add(new BasicNameValuePair("email", "输入Email邮箱地址"));	
		params.add(new BasicNameValuePair("validCode", rnum));	
		params.add(new BasicNameValuePair("emailPwd", "请输入密码"));	
		params.add(new BasicNameValuePair("servicePassword", "请输入6位数字的服务密码"));	
		params.add(new BasicNameValuePair("smsValidCode", ""));	
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("sendRom")){
				sendRom = c.getValue();
				System.out.println("sendRom: "+sendRom);
			}			
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+ cmtokenid);
			}	
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+ CmWebtokenid);
			}			
			if(c.getName().equals("U_C_VS")){
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+U_C_VS);
			}			
			if(c.getName().equals("SSO_SID")){
				SSO_SID = c.getValue();
				System.out.println("SSO_SID: "+SSO_SID);
			}

		}
		
		System.out.println(res.getResponse());
	}
	
	public void getBackPage(){
		
	}
	
	private String mmobileb = "";
	private String BIGipServerPOOL_XWT = "";
	private String cookiesession1 = "";
	/**
	 * 获取www.he.10086.cn的Session
	 */
	public void getHESession(){
		System.out.println("getHESession");
		String url = "http://www.he.10086.cn/my";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=311|311; CmProvid=he; CmWebtokenid=" + CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS;
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("RelayState", "type%3DB%3Bbackurl%3Dhttp%3A%2F%2Fwww.he.10086.cn%2Fmy%3Bnl%3D3%3BloginFrom%3Dhttp%3A%2F%2Fwww.he.10086.cn%2Fmy"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("mmobileb")){
				mmobileb = c.getValue();
				System.out.println("mmobileb: "+mmobileb);
			}
			if(c.getName().equals("BIGipServerPOOL-XWT")){
				BIGipServerPOOL_XWT = c.getValue();
				System.out.println("BIGipServerPOOL-XWT: "+BIGipServerPOOL_XWT);
			}
			if(c.getName().equals("cookiesession1")){
				cookiesession1 = c.getValue();
				System.out.println("cookiesession1: "+cookiesession1);
			}
		}
		
		System.out.println(res.getResponse());
		
	}

	private String JSESSIONIDMMOBILE_NEW = "";
	private String SAMLRequest = "";
	
	/**
	 * 获取SAMLRequest
	 */
	public void getSAMLRequest(){
		System.out.println("getSAMLRequest");
		String url = "http://www.he.10086.cn/my";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=311|311; CmProvid=he; CmWebtokenid="+ CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; mmobileb=" + mmobileb + "; "
				+ "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; cookiesession1=" + cookiesession1;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONIDMMOBILE_NEW")) {
				JSESSIONIDMMOBILE_NEW = c.getValue();
				System.out.println("JSESSIONIDMMOBILE_NEW: "+JSESSIONIDMMOBILE_NEW);
			}
		}
		
		System.out.println(res.getResponse());
		
		res.setResponse(res.getResponse().trim());
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLRequest = YD_XPath.readSAMLRequest_HE(inputResult);
		//返回时SAMLRequest中间有/n，转成了空格，需要去掉
		SAMLRequest = SAMLRequest.replaceAll(" ", "");
		System.out.println("SAMLRequest: " + SAMLRequest);
		
		try {
			inputResult.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RelayState = YD_XPath.readRelayState_HE(inputResult);
		System.out.println("RelayState: " + RelayState);
	}

	/**
	 * 获取SAMLart
	 */
	public void getSAMLart(){
		System.out.println("getSAMLart");
		String url = "http://he.ac.10086.cn/POST";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmLocation=311|311; CmProvid=he; CmWebtokenid=" + CmWebtokenid + "; "
				+ "BIGipServerPOOL-SSO-80=" + ac_BIGipServerPOOL_SSO_80 + "; JSESSIONID=" + 
				JSESSIONID + "; sendRom=" + sendRom + "; U_C_VS=" + U_C_VS + "; SSO_SID=" + SSO_SID;	
		System.out.println(cookie);
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer","http://www.he.10086.cn/my/");
		postMethod.addHeader("Host","he.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLRequest", SAMLRequest));
		params.add(new BasicNameValuePair("RelayState", RelayState));
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("U_C_VS")) {
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+ U_C_VS);
			}
		}
		
		res.setResponse(res.getResponse().trim());
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLart = YD_XPath.readSAMLart(inputResult);
		System.out.println("SAMLart: "+SAMLart);
		System.out.println(res.getResponse());
	}
	
	/**
	 * firstLogin
	 */
	public void firstLogin(){
		System.out.println("firstLogin");
		String url = "http://www.he.10086.cn/my/";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie =  "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "cookiesession1=" + cookiesession1 + "; "
				+ "mmobileb=" + mmobileb + "; CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + ";";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer","http://he.ac.10086.cn/POST");
		postMethod.addHeader("Origin","http://he.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("isEncodePassword", "2"));
		params.add(new BasicNameValuePair("displayPic", "0"));
		params.add(new BasicNameValuePair("RelayState", RelayState));
		params.add(new BasicNameValuePair("displayPics", "mobile_sms_login%3A0%3D%3D%3DsendSMS%3A0%3D%3D%3Dmobile_servicepasswd_login%3A0"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("mmobileb")){
				mmobileb = c.getValue();
				System.out.println("mmobileb: "+mmobileb);
			} 
		}
		
		System.out.println(res.getResponse());
		
	}
	
	private String JSESSIONIDSHOPPING_NEW = "";
	
	/**
	 * getJSESSIONIDSHOPPING_NEW
	 */
	public void getJSESSIONIDSHOPPING_NEW(){
		System.out.println("getJSESSIONIDSHOPPING_NEW");
		String url = "http://www.he.10086.cn/service/servlet/goodNumber";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; cookiesession1=" + cookiesession1 + "; "
				+ "WEBTRENDS_ID=" + Webtrends + "; mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + ";"
				+ "CmLocation=311|311; CmProvid=he";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONIDSHOPPING_NEW")) {
				JSESSIONIDSHOPPING_NEW = c.getValue();
				System.out.println("JSESSIONIDSHOPPING_NEW: "+JSESSIONIDSHOPPING_NEW);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	
	private String serviceb = "";
	
	/**
	 * 再次获取SAMLRequest
	 */
	public void getSAMLRequestAgain(){
		System.out.println("getSAMLRequestAgain");
		String url = "http://www.he.10086.cn/service/fee/qryDetailBill.action?menuid=qryDetailBill&pageId=0.14973586797714233";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; cookiesession1=" + cookiesession1 + "; "
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";"
				+ "WEBTRENDS_ID=" + Webtrends + "; mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + "; emall_roteServer=PRODUCT; "
				+ "CmLocation=311|311; CmProvid=he";
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println(res.getResponse());
		
		res.setResponse(res.getResponse().trim().split("<script>")[0]);
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLRequest = YD_XPath.readSAMLRequest_HE(inputResult);
		//返回时SAMLRequest中间有/n，转成了空格，需要去掉
		SAMLRequest = SAMLRequest.replaceAll(" ", "");
		System.out.println("SAMLRequest: " + SAMLRequest);
		
		try {
			inputResult.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RelayState = YD_XPath.readRelayState_HE(inputResult);
		System.out.println("RelayState: " + RelayState);
	}
	
	/**
	 * 获取SAMLart
	 */
	public void getSAMLartAgain(){
		System.out.println("getSAMLart");
		String url = "http://he.ac.10086.cn/POST";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmLocation=311|311; CmProvid=he; CmWebtokenid=" + CmWebtokenid + "; "
				+ "BIGipServerPOOL-SSO-80=" + ac_BIGipServerPOOL_SSO_80 + "; JSESSIONID=" + 
				JSESSIONID + "; sendRom=" + sendRom + "; U_C_VS=" + U_C_VS + "; SSO_SID=" + SSO_SID;	
		System.out.println(cookie);
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLRequest", SAMLRequest));
		params.add(new BasicNameValuePair("RelayState", RelayState));
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("U_C_VS")) {
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+ U_C_VS);
			}
		}
		
		res.setResponse(res.getResponse().trim());
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		SAMLart = YD_XPath.readSAMLart(inputResult);
		System.out.println("SAMLart: "+SAMLart);
		System.out.println(res.getResponse());
	}
	
	/**
	 * 再次初始化登录
	 */
	public void initLogin(){
		System.out.println("initLogin");
		String url = "http://www.he.10086.cn/service/login!initLogin.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; cookiesession1=" + cookiesession1 + "; "
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";"
				+ "WEBTRENDS_ID=" + Webtrends + "; mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + "; emall_roteServer=PRODUCT; "
				+ "CmLocation=311|311; CmProvid=he; serviceb=" + serviceb;
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("isEncodePassword", "2"));
		params.add(new BasicNameValuePair("displayPic", "0"));
		params.add(new BasicNameValuePair("RelayState", RelayState));
		params.add(new BasicNameValuePair("displayPics", "mobile_sms_login%3A0%3D%3D%3DsendSMS%3A0%3D%3D%3Dmobile_servicepasswd_login%3A0"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println("initLogin: " + res.getResponse());
	}
	
	
	
	/**
	 * 获取query的serviceb
	 */
	public void getQueryServiceb(){
		System.out.println("getQueryServiceb");
		String url = "http://www.he.10086.cn/service/fee/qryDetailBill.action";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=311|311; CmProvid=he; CmWebtokenid="+ CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; mmobileb=" + mmobileb + "; "
				+ "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; cookiesession1=" + cookiesession1
				+ "; emall_roteServer=PRODUCT;mmobile_roteServer=PRODUCT; serviceb=" + serviceb
				+ "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + ";"
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";";

		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println(res.getResponse());
	}
	/**
	 * SSOCheck
	 */
	public void SSOCheck(){
		System.out.println("SSOCheck");
		String url = "http://www.he.10086.cn/service/commonSSO.action?commonSSOUrl=http%3A%2F%2Fwww.he.10086.cn%2Fservice%2Ffee%2FqryDetailBill.action";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=311|311; CmProvid=he; CmWebtokenid="+ CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; mmobileb=" + mmobileb + "; "
				+ "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; cookiesession1=" + cookiesession1
				+ "emall_roteServer=PRODUCT;mmobile_roteServer=PRODUCT; serviceb=" + serviceb
				+ ";JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + ";"
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";";

		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println("SSOCheck: " + res.getResponse());
	}
	
	/**
	 * loginAgainValidate
	 */
	public void loginAgainValidate(){
		System.out.println("loginAgainValidate");
		String url = "http://www.he.10086.cn/service/loginValidate.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; cookiesession1=" + cookiesession1 + "; "
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";"
				+ "WEBTRENDS_ID=" + Webtrends + "; mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + "; emall_roteServer=PRODUCT; "
				+ "CmLocation=311|311; CmProvid=he; serviceb=" + serviceb;
		// serviceb=" + "ba707eaa462976f22aa19471cec619e6|1464751403|1464750099"
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("validateUrl", ""));
		params.add(new BasicNameValuePair("continue_url", "http%3A%2F%2Fwww.he.10086.cn%2Fservice%2Ffee%2FqryDetailBill.action"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 发送查询短信验证码
	 */
	public void sendSMSCode(){
		System.out.println("sendSMSCode");
		String url = "http://www.he.10086.cn/service/fee/fee/qryDetailBill!sendRandomCode.action?r=0.822736574569717";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT
				+ "; JSESSIONIDMMOBILE_NEW=" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; "
				+ "cookiesession1=" + cookiesession1 + "; "
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";"
				+ "WEBTRENDS_ID=" + Webtrends + "; "
				+ "mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; "
				+ "emall_roteServer=PRODUCT; "
				+ "CmLocation=311|311; CmProvid=he";
		// serviceb=" + "ba707eaa462976f22aa19471cec619e6|1464751403|1464750099"
		
		postMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("serviceb")) {
				serviceb = c.getValue();
				System.out.println("serviceb: "+serviceb);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	
	/**
	 * 查询详细通话记录
	 */
	public void getDetailBill(){
		System.out.println("getDetailBill");
		String url = "http://www.he.10086.cn/service/fee/qryDetailBill!qryNewBill.action?smsrandom=&r=0.13042667810805142";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "BIGipServerPOOL-XWT=" + BIGipServerPOOL_XWT + "; JSESSIONIDMMOBILE_NEW" + JSESSIONIDMMOBILE_NEW + "; "
				+ "mmobile_roteServer=PRODUCT; cookiesession1=" + cookiesession1 + "; "
				+ "JSESSIONIDSHOPPING_NEW=" + JSESSIONIDSHOPPING_NEW + ";"
				+ "WEBTRENDS_ID=" + Webtrends + "; mmobileb=" + mmobileb + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; U_C_VS=" + U_C_VS + "; emall_roteServer=PRODUCT; "
				+ "CmLocation=311|311; CmProvid=he";
		// serviceb=" + "ba707eaa462976f22aa19471cec619e6|1464751403|1464750099"
		
		postMethod.addHeader("Cookie", cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectTaken", ""));
		params.add(new BasicNameValuePair("regionstate", "1"));
		params.add(new BasicNameValuePair("onlinetime", "201204"));
		params.add(new BasicNameValuePair("dateLimitHigh", "201606"));
		params.add(new BasicNameValuePair("dateLimitLow", "201601"));
		params.add(new BasicNameValuePair("menuid", ""));
		params.add(new BasicNameValuePair("fieldErrFlag", ""));
		params.add(new BasicNameValuePair("selectncode", ""));
		params.add(new BasicNameValuePair("ncodestatus", ""));
		params.add(new BasicNameValuePair("operatype", ""));
		params.add(new BasicNameValuePair("groupId", ""));
		params.add(new BasicNameValuePair("theMonth", "201605"));
		params.add(new BasicNameValuePair("qryscope", "0"));
		params.add(new BasicNameValuePair("queryType", "NGQryCallBill"));
		params.add(new BasicNameValuePair("qryType", "10"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
}
