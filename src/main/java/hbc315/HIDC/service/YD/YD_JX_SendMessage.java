package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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


/**
 * 江苏移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_JX_SendMessage {


	private static String mobile = "15270319628";
	private static String servicePassword = "989977";	
	
	private String rnum = "rtf7";	//验证码
	
	private String JSESSIONID = "109A15B2D400AAAC235619F845514533";
	private String g_int_sso_443 = "r_sso_443_4";
	private String spid = "40288b8b3627308901362a56fd1b0002";
	private String sid = "109A15B2D400AAAC235619F845514533";
	
	public static void main(String[] args) {
		YD_JX_SendMessage yd = new YD_JX_SendMessage();
		
		yd.login(mobile, servicePassword);
		yd.getSAMLRequest();
		yd.getSAMLart();
		yd.postRequst();
		yd.getRnum();
		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("JSESSIONID: " + serviceJSESSIONID);
		System.out.println("U_C_VS: " + U_C_VS);
		System.out.println("SSO_SID: " + SSO_SID);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("spid: " + spid);
		System.out.println("sid: " + sid);
		System.out.println("servicea: " + servicea);
		System.out.println("g_wangting_ic: " + g_wangting_ic);
		System.out.println("g_int_sso_443: " + g_int_sso_443);
	}
	
	private String U_C_VS = ""; 
	private String cmtokenid = "";
	private String CmWebtokenid = "";
	private String SSO_SID = "";
	
	/**
	 * 密码登录
	 */
	public void login(String mobile ,String servicePassword){
		String url = "https://jx.ac.10086.cn/Login";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=791|791; CmProvid=jx; JSESSIONID=" + JSESSIONID + "; g_int_sso_443=" + g_int_sso_443;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://jx.ac.10086.cn");
		postMethod.addHeader("Referer","https://jx.ac.10086.cn/login");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("from", "yanhuang"));
		params.add(new BasicNameValuePair("sid", sid));
		params.add(new BasicNameValuePair("type", "B"));
		params.add(new BasicNameValuePair("backurl", "https://jx.ac.10086.cn/4login/backPage.jsp"));
		params.add(new BasicNameValuePair("errorurl", "https://jx.ac.10086.cn/4login/errorPage.jsp"));
		params.add(new BasicNameValuePair("spid", spid));
		params.add(new BasicNameValuePair("RelayState", "type=A;backurl=http://www.jx.10086.cn/my/;nl=3;loginFrom=null;refer=null"));
		params.add(new BasicNameValuePair("mobileNum", mobile));
		params.add(new BasicNameValuePair("servicePassword", servicePassword));
		params.add(new BasicNameValuePair("smsValidCode", ""));
		params.add(new BasicNameValuePair("validCode", rnum));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		postMethod.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
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
				System.out.println("U_C_VS: "+ U_C_VS);
			}
			if(c.getName().equals("SSO_SID")){
				SSO_SID = c.getValue();
				System.out.println("SSO_SID: "+ SSO_SID);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	private String serviceJSESSIONID = "";
	private String g_wangting_ic = "";
	private String servicea = "";
	private String SAMLRequest = "";
	/**
	 * getSAMLRequest
	 */
	private void getSAMLRequest(){
		String url = "http://service.jx.10086.cn/service/showBillDetail!queryShowBillDatailN.action?menuid=000200010003";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=791|791; CmProvid=jx; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; ";
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "http://www.jx.10086.cn/my/");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				serviceJSESSIONID = c.getValue();
				System.out.println("serviceJSESSIONID: "+ serviceJSESSIONID);
			}
			if(c.getName().equals("servicea")){
				servicea = c.getValue();
				System.out.println("servicea: "+ servicea);
			}
			if(c.getName().equals("g_wangting_ic")){
				g_wangting_ic = c.getValue();
				System.out.println("g_wangting_ic: "+ g_wangting_ic);
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
		String url = "https://jx.ac.10086.cn/POST";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=791|791; CmProvid=jx; "
				+ "g_int_sso_443=" + g_int_sso_443 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; "
				+ "SSO_SID=" + SSO_SID + "; ";
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","http://service.jx.10086.cn");
		postMethod.addHeader("Referer","http://service.jx.10086.cn/service/showBillDetail!queryShowBillDatailN.action?menuid=000200010003");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SAMLRequest", SAMLRequest));
		params.add(new BasicNameValuePair("RelayState", "type=A;backurl=http%3A%2F%2Fservice.jx.10086.cn%2Fservice%2FshowBillDetail%21queryShowBillDatailN.action%3Fmenuid%3D000200010003;nl=1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("U_C_VS")){
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+ U_C_VS);
			}
			if(c.getName().equals("SSO_SID")){
				SSO_SID = c.getValue();
				System.out.println("SSO_SID: "+ SSO_SID);
			}
		}
		
		try {
			SAMLart = res.getResponse().split("SAMLart\" value=\"")[1];
			SAMLart = SAMLart.split("\"")[0];
		} catch (Exception e) {
			
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * getSAMLart
	 */
	public void postRequst(){
		String url = "http://service.jx.10086.cn/service/showBillDetail!queryShowBillDatailN.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "CmLocation=791|791; CmProvid=jx; "
				+ "JSESSIONID=" + serviceJSESSIONID + "; "
				+ "servicea=" + servicea + "; "
				+ "g_wangting_ic=" + g_wangting_ic + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; ";
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://jx.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("displayPics", ""));
		params.add(new BasicNameValuePair("displayPic", "0"));
		params.add(new BasicNameValuePair("RelayState", "type=A;backurl=http://service.jx.10086.cn/service/showBillDetail!queryShowBillDatailN.action?menuid=000200010003;nl=1"));
		params.add(new BasicNameValuePair("SAMLart", SAMLart));
		params.add(new BasicNameValuePair("menuid", "000200010003"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("U_C_VS")){
				U_C_VS = c.getValue();
				System.out.println("U_C_VS: "+ U_C_VS);
			}
			if(c.getName().equals("SSO_SID")){
				SSO_SID = c.getValue();
				System.out.println("SSO_SID: "+ SSO_SID);
			}
		}
		
		try {
			SAMLart = res.getResponse().split("SAMLart\" value=\"")[1];
			SAMLart = SAMLart.split("\"")[0];
		} catch (Exception e) {
			
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://jx.ac.10086.cn/common/image.jsp?l=" + RandomNumUtil.getRandomNumber_16();
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=791|791; CmProvid=jx; "
				+ "g_int_sso_443=" + g_int_sso_443 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; "
				+ "SSO_SID=" + SSO_SID + "; ";
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "http://service.jx.10086.cn/service/checkSmsPassN.action?menuid=000200010003");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_JX.jpg";
			FileOutputStream fos = new FileOutputStream(picName);
			
			byte[] b = new byte[1024];
			while((is.read(b)) != -1){
				fos.write(b);
			}
			
			fos.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
	}

	private String WTCX_MY_QDCX = "";
	private String smsVerifyCodeName = "";
	private String smsVerifyCodeValue = "";
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "https://jx.ac.10086.cn/SMSCodeSend?mobileNum=" + mobile + "&errorurl=http://service.jx.10086.cn/service/common/ssoPrompt.jsp";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "cmtokenid=" + cmtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; "
				+ "CmLocation=791|791; CmProvid=jx; "
				+ "g_int_sso_443=" + g_int_sso_443 + "; "
				+ "SSO_SID=" + SSO_SID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid;
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://service.jx.10086.cn/service/checkSmsPassN.action?menuid=000200010003");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		System.out.println(res.getResponse());
	}
}
