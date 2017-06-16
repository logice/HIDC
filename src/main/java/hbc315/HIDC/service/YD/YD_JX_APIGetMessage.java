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
import hbc315.HIDC.util.QueryDateUtil;


/**
 * 江西移动，通话详单，需要提供图片验证码和短信验证码
 * @author zcy
 *
 */
public class YD_JX_APIGetMessage {


	private static String mobile = "15270319628";
	private static String month = "201605";
	
	private String SMSCode = "040455";	//验证码
	private String rnum = "r9p4";	//图片验证码
	private String JSESSIONID = "109A15B2D400AAAC235619F845514533";
	private String serviceJSESSIONID = "DF0D6161DB763A31CD0A9E0B0140C407";
	private String U_C_VS = "AF35EE5FBBCE3FA813578724383CDA2536ED19F8F3BC36A5C64BE2432A5E3CF3";
	private String SSO_SID = "290b1a35a3cc44fd9c953573f811d741";
	private String cmtokenid = "290b1a35a3cc44fd9c953573f811d741@jx.ac.10086.cn";
	private String CmWebtokenid = "15270319628,jx";
	private String spid = "40288b8b3627308901362a56fd1b0002";
	private String sid = "109A15B2D400AAAC235619F845514533";
	private String servicea = "f3c72fd84d824cafa191802a174e6b6e|1467978837|1467978837";
	private String g_wangting_ic = "r_WT_F5_05";
	private String g_int_sso_443 = "r_sso_443_4";
	
	public static void main(String[] args) {
		YD_JX_APIGetMessage yd = new YD_JX_APIGetMessage();

		yd.login();
		yd.ssoLogin();
		yd.getDetailCallList();
	}	

	private String SAMLart = "";

	/**
	 * 密码登录
	 */
	public void login(){
		String url = "https://jx.ac.10086.cn/Login";
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
		postMethod.addHeader("Referer","http://service.jx.10086.cn/service/checkSmsPassN.action?menuid=000200010003");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("smsValidCode", SMSCode));
		params.add(new BasicNameValuePair("validCode", rnum));
		params.add(new BasicNameValuePair("submitBtn", "确定"));
		params.add(new BasicNameValuePair("type", "A"));
		params.add(new BasicNameValuePair("loginStatus", "A"));
		params.add(new BasicNameValuePair("loginFlag", "false"));
		params.add(new BasicNameValuePair("spid", spid));
		params.add(new BasicNameValuePair("backurl", "http://service.jx.10086.cn/service/backAction.action?menuid=000200010003"));
		params.add(new BasicNameValuePair("errorurl", "http://service.jx.10086.cn/service/common/ssoPrompt.jsp"));
		params.add(new BasicNameValuePair("sid", sid));
		params.add(new BasicNameValuePair("mobileNum", mobile));
		params.add(new BasicNameValuePair("ssoImageUrl", "https://jx.ac.10086.cn/common/image.jsp"));
		params.add(new BasicNameValuePair("ssoSmsUrl", "https://jx.ac.10086.cn/SMSCodeSend"));
		params.add(new BasicNameValuePair("menuid", "000200010003"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
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
		
		try {
			SAMLart = res.getResponse().split("SAMLart\" value=\"")[1];
			SAMLart = SAMLart.split("\"")[0];
		} catch (Exception e) {
			
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * 密码登录
	 */
	public void ssoLogin(){
		String url = "http://service.jx.10086.cn/service/backAction.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "mmobile_roteServer=PRODUCT; CmLocation=791|791; CmProvid=jx; "
				+ "servicea=" + servicea + "; "
				+ "JSESSIONID=" + serviceJSESSIONID + "; "
				+ "g_wangting_ic=" + g_wangting_ic + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; ";
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://jx.ac.10086.cn");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("displayPics", ""));
		params.add(new BasicNameValuePair("displayPic", "1"));
		params.add(new BasicNameValuePair("RelayState", "type=B;backurl=https://jx.ac.10086.cn/4login/backPage.jsp;nl=1"));
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
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://service.jx.10086.cn/service/showBillDetail!billQueryCommit.action?otherorder=1467949070076_19533_123&billType=202&startDate=20160707&endDate=20160731&clientDate=" + QueryDateUtil.getTimestamp() + "&menuid=00890201&requestStartTime=";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmLocation=791|791; CmProvid=jx; mmobile_roteServer=PRODUCT; "
				+ "JSESSIONID=" + serviceJSESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + ";"
				+ "U_C_VS=" + U_C_VS + "; "
				+ "servicea=" + servicea + "; "
				+ "g_wangting_ic=" + g_wangting_ic + "; "
				+ "SSO_SID=" + SSO_SID + "; ";
		getMethod.addHeader("Cookie", cookie);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		System.out.println(res.getResponse());
		
	}
}
