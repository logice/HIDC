package hbc315.HIDC.service.YD;

import org.apache.http.client.methods.HttpGet;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_AH_APIGetMessage {

	
	private static String SMSCode = "283944";	//验证码
	private String CmWebtokenid = "18715090626,ah";
	private String SSO_NEW_SESSIONID = "_BW0bkIaDSy29OUaD5EjkFKxZeHMdDDccH6tCCVtJEpGbiUJcYaH!667143007";
	private String SSO_APP_SESSIONID = "_BW0bkIaDSy29OUaD5EjkFKxZeHMdDDccH6tCCVtJEpGbiUJcYaH!667143007";
	private String U_C_VS = "314C8B69AE1E0A97C6200C6C2572D7EF3D67D60D7481290235787F5DFA01617D";
	private String ArrayWT = "r_busihallon_42";
	private String jsession_id_4_miso = "46185F0DDD1684B687EDCB4F7930C3B6";
	
	
	public static void main(String[] args) {
		YD_AH_APIGetMessage yd = new YD_AH_APIGetMessage();

//		yd.checkSMSCode();
//		yd.loginGet();
		yd.getDetailCallList();
	}	

	
	/**
	 * 发送短信验证码
	 * 
	 */
	public void checkSMSCode(){
		String url = "http://service.ah.10086.cn/pub/chkSmPass?smPass=" + SMSCode + "&phone_No=&_=" + System.currentTimeMillis();
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
	
	public void loginGet(){
		String url = "http://service.ah.10086.cn/qry/qrySmsChkFlag?_=" + System.currentTimeMillis();
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
	
	public void getDetailCallList(){
		String url = "http://service.ah.10086.cn/qry/qryBillDetailPage?detailType=205&startDate=20160701&endDate=20160704&nowPage=1&qryType=&_=" + System.currentTimeMillis();
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "ArrayWT=" + ArrayWT + "; "
				+ "SSO_NEW_SESSIONID=" + SSO_NEW_SESSIONID + "; "
				+ "SSO_APP_SESSIONID=" + SSO_APP_SESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "U_C_VS=" + U_C_VS + "; "
				+ "jsession_id_4_miso=" + jsession_id_4_miso + "; "
				+ "CmLocation=551|551; CmProvid=ah";
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://service.ah.10086.cn/pub-page/qry/qryDetail/qryBillDetailInfo.html");
		
		ResponseValue res = CommonHttpMethod.doGetWithNoParams(getMethod);		
		
		System.out.println(res.getResponse());
	}
}
