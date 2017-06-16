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


/**
 * 浙江移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_ZJ_SendMessage {


	private static String mobile = "18268144220";
	private static String servicePassword = "989977";	
	
	private String rnum = "qkdnw";	//验证码
	
	private String JSESSIONID = "SyB0XyyXbdCsv1j22Pn7bLygGr9mXC1bTvdkXxm5hv9jZTcnvx3W!-1833112313";
	
	public static void main(String[] args) {
		YD_ZJ_SendMessage yd = new YD_ZJ_SendMessage();
		
		yd.loginBox(mobile, servicePassword);
		yd.loginSSO();
		yd.getWTSESSION();
		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("unity_SAMLart: " + unity_SAMLart);
		System.out.println("citybrand: " + citybrand);
		System.out.println("WTSESSION: " + WTSESSION);
	}
	
	private String CmWebtokenid = ""; 
	private String cmtokenid = "";
	private String SAMLart = "";
	
	/**
	 * 密码登录
	 */
	public void loginBox(String mobile ,String servicePassword){
		String url = "https://zj.ac.10086.cn/loginbox";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID ;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://zj.ac.10086.cn");
		postMethod.addHeader("Referer","https://zj.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("service", "my"));
		params.add(new BasicNameValuePair("continue", "%2Fmy%2Flogin%2FloginSuccess.do"));
		params.add(new BasicNameValuePair("failurl", "https%3A%2F%2Fzj.ac.10086.cn%2Flogin"));
		params.add(new BasicNameValuePair("style", "1"));
		params.add(new BasicNameValuePair("pwdType", "2"));
		params.add(new BasicNameValuePair("SMSpwdType", "0"));
		params.add(new BasicNameValuePair("billId", mobile));
		params.add(new BasicNameValuePair("mima", "fuwumima"));
		params.add(new BasicNameValuePair("passwd1", "%CD%FC%BC%C7%C3%DC%C2%EB%A3%BF%BF%C9%D3%C3%B6%CC%D0%C5%D1%E9%D6%A4%C2%EB%B5%C7%C2%BC"));
		params.add(new BasicNameValuePair("passwd", servicePassword));
		params.add(new BasicNameValuePair("validCodeId1", "5%B8%F6%D7%D6%B7%FB"));
		params.add(new BasicNameValuePair("validCode", rnum));
		
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
			SAMLart = res.getResponse().split("SAMLart")[1];
			SAMLart = SAMLart.split("value=\"")[1];
			SAMLart = SAMLart.split("\"")[0];
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("SAMLart: " + SAMLart);
		System.out.println(res.getResponse());
	}


	private String SNSSESSION = "";
	private String unity_SAMLart = "";
	private String citybrand = "";
	
	/**
	 * 单点登录sso
	 */
	public void loginSSO(){
		String url = "http://www.zj.10086.cn/my/sso";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmWebtokenid=" + CmWebtokenid + "; cmtokenid=" + cmtokenid;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://zj.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("RelayState", "/my/login/loginSuccess.do"));
		params.add(new BasicNameValuePair("submit", "提交"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("SNSSESSION")){
				SNSSESSION = c.getValue();
				System.out.println("SNSSESSION: "+SNSSESSION);
			}
			if(c.getName().equals("unity_SAMLart")){
				unity_SAMLart = c.getValue();
				System.out.println("unity_SAMLart: "+ unity_SAMLart);
			}
			if(c.getName().equals("citybrand")){
				citybrand = c.getValue();
				System.out.println("citybrand: "+ citybrand);
			}
		}
		
		try {
			SAMLart = res.getResponse().split("SAMLart")[1];
			SAMLart = SAMLart.split("value=\"")[1];
			SAMLart = SAMLart.split("\"")[0];
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(res.getResponse());
	}

	
	private String WTSESSION = "";
	
	/**
	 * 获取UID
	 * 
	 */
	public void getWTSESSION(){
		String url = "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmWebtokenid=" + CmWebtokenid + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "unity_SAMLart=" + unity_SAMLart + "; "
				+ "citybrand=" + citybrand + "; "
				+ "zjCityCode=571; cityCodeCookie=571; "
				+ "groupId=" + mobile + "^noGroupId; "
				+ "CmLocation=571|571; CmProvid=zj";
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://www.zj.10086.cn/my/index.do");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		

		for(Cookie c : res.getCookies()){
			if(c.getName().equals("WTSESSION")){
				WTSESSION = c.getValue();
			}
		}
		System.out.println("WTSESSION: " + WTSESSION);
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "http://service.zj.10086.cn/yw/detail/secondPassCheck.do";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmWebtokenid=" + CmWebtokenid + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "unity_SAMLart=" + unity_SAMLart + "; "
				+ "citybrand=" + citybrand + "; "
				+ "zjCityCode=571; cityCodeCookie=571; ; "
				+ "groupId=" + mobile + "^noGroupId; "
				+ "WTSESSION=" + WTSESSION + "; "
				+ "CmLocation=571|571; CmProvid=zj; "
				+ "cookieBusiness=%25E8%25AF%25AD%25E9%259F%25B3%25E8%25AF%25A6%25E5%258D%2595%5Ehttp%3A//service.zj.10086.cn/yw/detail/queryHisDetailBill.do%3FmenuId%3D13009%26bid%3DBD399F39E69148CFE044001635842131%7C";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.zj.10086.cn");
		postMethod.addHeader("Referer", "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("validateCode", ""));
		params.add(new BasicNameValuePair("bid", "BC5CC0A69BC10482E044001635842132"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
}
