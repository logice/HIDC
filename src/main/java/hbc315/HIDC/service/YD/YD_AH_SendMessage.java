package hbc315.HIDC.service.YD;

import java.io.IOException;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_AH_SendMessage {


	private static String mobile = "18715090626";
	private static String servicePassword = "989977";	
	
	private String rnum = "nqcx";	//验证码

	private String AHSSOSESSIONID = "sso1_58!!_BW0bkIaDSy29OUaD5EjkFKxZeHMdDDccH6tCCVtJEpGbiUJcYaH!667143007";
	
	public static void main(String[] args) {
		YD_AH_SendMessage yd = new YD_AH_SendMessage();


		yd.login(mobile,servicePassword);
		yd.sendSMSCode();
		yd.out();

	}
	
	private void out(){
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("SSO_NEW_SESSIONID: " + SSO_NEW_SESSIONID);
		System.out.println("SSO_APP_SESSIONID: " + SSO_APP_SESSIONID);
		System.out.println("U_C_VS: " + U_C_VS);
		System.out.println("ArrayWT: " + ArrayWT);
		System.out.println("jsession_id_4_miso: " + jsession_id_4_miso);
	}
	
	private String CmWebtokenid = "";
	private String cmtokenid = "";
	private String SSO_NEW_SESSIONID = "";
	private String SSO_APP_SESSIONID = "";
	private String U_C_VS = "";
	private String sendRom = "";
	private String ssoLoginURL = "";
	
	/**
	 * 登录
	 */
	public void login(String EncryptMobile,String EncryptPassword){
		String url = "https://ah.ac.10086.cn/Login";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "AHSSOSESSIONID=" + AHSSOSESSIONID + "; "
				+ "CmLocation=551|551; CmProvid=ah; "
				+ "SSO_SID=";
//				+ "sendRom=" + vpn_auto + "; ";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://ah.ac.10086.cn");
		postMethod.addHeader("Referer", "https://ah.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", "B"));
		params.add(new BasicNameValuePair("formertype", "A"));
		params.add(new BasicNameValuePair("backurl", "http%3A%2F%2Fservice.ah.10086.cn%2FLoginSso"));
		params.add(new BasicNameValuePair("errorurl", "https%3A%2F%2Fah.ac.10086.cn%2F4login%2FerrorPage.jsp"));
		params.add(new BasicNameValuePair("spid", "8a18cbc43f0dd7bd013f181c060f0001"));
		params.add(new BasicNameValuePair("RelayState", "type%3DA%3Bbackurl%3Dhttp%3A%2F%2Fservice.ah.10086.cn%2FLoginSso%3Bnl%3D3%3BloginFrom%3Dhttp%3A%2F%2Fservice.ah.10086.cn%2FLoginSso"));
		params.add(new BasicNameValuePair("mobileNum", mobile));
		params.add(new BasicNameValuePair("login_type_ah", ""));
		params.add(new BasicNameValuePair("login_pwd_type", "2"));
		params.add(new BasicNameValuePair("loginBackurl", ""));
		params.add(new BasicNameValuePair("timestamp", String .valueOf(System.currentTimeMillis())));
		params.add(new BasicNameValuePair("validCode_state", "true"));
		params.add(new BasicNameValuePair("loginType", "0"));
		params.add(new BasicNameValuePair("servicePassword", servicePassword));
		params.add(new BasicNameValuePair("servicePassword_1", ""));
		params.add(new BasicNameValuePair("smsValidCode", ""));
		params.add(new BasicNameValuePair("validCode", rnum));
		
		
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
			}
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+CmWebtokenid);
			}
			if(c.getName().equals("SSO_NEW_SESSIONID")){
				SSO_NEW_SESSIONID = c.getValue();
				System.out.println("SSO_NEW_SESSIONID: "+SSO_NEW_SESSIONID);
			}
			if(c.getName().equals("SSO_APP_SESSIONID")){
				SSO_APP_SESSIONID = c.getValue();
				System.out.println("SSO_APP_SESSIONID: "+SSO_APP_SESSIONID);
			}
			if(c.getName().equals("U_C_VS")){
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+U_C_VS);
			}
			if(c.getName().equals("sendRom")){
				sendRom = c.getValue();
				System.out.println("sendRom: "+sendRom);
			}
		}
		
		try {
			ssoLoginURL = res.getResponse().split("window.top.location='")[1];
			ssoLoginURL = ssoLoginURL.split("'\"")[0];
			System.out.println("ssoLoginURL: " + ssoLoginURL);
			getToken1();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(res.getResponse());
	}
	
	private String jsession_id_4_miso = "";
	private String ArrayWT = "";
	/**
	 * 拿取登录成功后的各种token
	 * 
	 */
	public void getToken1(){
		if(!ssoLoginURL.equals("")){
			HttpGet getMethod = new HttpGet(ssoLoginURL);
			
			// "jsession_id_4_miso=" + jsession_id_4_miso + "; "
			String cookie ="CmLocation=551|551; CmProvid=ah; "
					+ "SSO_NEW_SESSIONID=" + SSO_NEW_SESSIONID + "; "
					+ "SSO_APP_SESSIONID=" + SSO_APP_SESSIONID + "; "
					+ "CmWebtokenid=" + CmWebtokenid + "; "
					+ "U_C_VS=" + U_C_VS;
			getMethod.addHeader("Cookie", cookie);
			
			ResponseValue res = CommonHttpMethod.doGet(getMethod);		
			
			for(Cookie c : res.getCookies()){
				if(c.getName().equals("jsession_id_4_miso")){
					jsession_id_4_miso = c.getValue();
					System.out.println("jsession_id_4_miso: "+jsession_id_4_miso);
				}
				if(c.getName().equals("ArrayWT")){
					ArrayWT = c.getValue();
					System.out.println("ArrayWT: "+ArrayWT);
				}
			}
			System.out.println(res.getResponse());
		}
	}
	
	/**
	 * 发送短信验证码
	 * 
	 */
	public void sendSMSCode(){
		String url = "http://service.ah.10086.cn/pub/sendSmPass?opCode=5868&phone_No=&_=" + System.currentTimeMillis();
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "ArrayWT=" + ArrayWT + "; "
				+ "SSO_NEW_SESSIONID=" + SSO_NEW_SESSIONID + "; "
				+ "SSO_APP_SESSIONID=" + SSO_APP_SESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; "
				+ "jsession_id_4_miso=" + jsession_id_4_miso + "; "
				+ "CmLocation=551|551; CmProvid=ah";
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://service.ah.10086.cn/pub-page/qry/qryDetail/billDetailIndex.html?kind=200011522&f=200011538&area=cd");
		
		ResponseValue res = CommonHttpMethod.doGetWithNoParams(getMethod);		
		
		System.out.println(res.getResponse());
		
	}
}
