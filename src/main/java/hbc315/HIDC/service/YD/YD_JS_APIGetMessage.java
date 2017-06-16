package hbc315.HIDC.service.YD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;


/**
 * 江苏移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_JS_APIGetMessage {


	private static String mobile = "13505153015";
	private static String month = "201605";
	
	private static String SMSCode = "283430";	//验证码
	private String wt_dl123 = "BmyFA30753IEwlewBCp7GTGFwjF44ZJl";
	private String cmtokenid = "81AF800786A24FE482AAD168B253C4D4@js.ac.10086.cn";
	private String cmjsSSOCookie = "81AF800786A24FE482AAD168B253C4D4@js.ac.10086.cn";
	private String AlteonP = "A4k3RmddqMBGuyQj4S3Pbw$$";
	private String city = "";
	private String WTCX_MY_QDCX = "MY_QDCX+1466493814756";
	private String JSESSIONID = "JVpyXyrVZTdQllvrtYRdrDW0RGg8QZL7tDJ4nG2vR0SL3dd4x8b6!-407780121";
	private String CmWebtokenid = "\"13505153015,js\"";
	private String smsVerifyCodeName = "13505153015_smsVerifyCode";
	private String smsVerifyCodeValue = "9CD7DFFA5537E825BAA06CA729B23275CCD8498DB7ADA5B9";
	
	public static void main(String[] args) {
		YD_JS_APIGetMessage yd = new YD_JS_APIGetMessage();

		yd.getDetailCallList();
	}	
	

	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://service.js.10086.cn/my/actionDispatcher.do";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "wt_dl123=" + wt_dl123 + "; "
				+ "cmjsSSOCookie=" + cmjsSSOCookie + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "AlteonP=" + AlteonP + "; "
				+ "yjcxFlag=1; onedayonetime=1; CmProvid=js; "
				+ "topUserMobile=" + mobile + "; "
				+ "city=" + city + "; "
				+ "WTCX_MY_QDCX=" + WTCX_MY_QDCX + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ smsVerifyCodeName + "=" + smsVerifyCodeValue;
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.js.10086.cn");
		postMethod.addHeader("Referer", "http://service.js.10086.cn/my/MY_QDCX.html?t=1466480247224");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("reqUrl", "MY_QDCXQueryNew"));
		params.add(new BasicNameValuePair("busiNum", "QDCX"));
		params.add(new BasicNameValuePair("queryMonth", month));
		params.add(new BasicNameValuePair("queryItem", "1"));
		params.add(new BasicNameValuePair("qryPages", ""));
		params.add(new BasicNameValuePair("qryNo", "1"));
		params.add(new BasicNameValuePair("operType", "3"));
		params.add(new BasicNameValuePair("queryBeginTime", "2016-05-01"));
		params.add(new BasicNameValuePair("queryEndTime", "2016-05-31"));
		params.add(new BasicNameValuePair("smsNum", SMSCode));
		params.add(new BasicNameValuePair("confirmFlg", "1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
}
