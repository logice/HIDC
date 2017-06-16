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
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;


/**
 * 查询移动用户通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_TJ_APIGetMessage {


	private String mobile = "18822121221";
	private String billMonth = "201605";
	
	private String smsCode = "739151";
	
	private String CmWebtokenid = "18822121221,tj,G001"; 
	private String CmSore = "5321,tj";
	private String CmBrand = "G001,tj";
	
	private String gFNVKdebnF = "MmNiNjkzMDQ2NzAwMDAwMDAwMjEwbRgQTCkxNDY1ODI4MzYw";
	
	private String WADE_ID = "7945A57E78D945C190C702149A31BC0B";
	private String loginName= mobile;
	private String FLAG_4GUsim = "1";
	private String is4Fuser = "1";
	
	public static void main(String[] args) {
		YD_TJ_APIGetMessage yd = new YD_TJ_APIGetMessage();
		
		yd.validateSMSCode();
		yd.getBillDetail();
	}
	
	/**
	 * 验证短信验证码 
	 */
	public void validateSMSCode(){
		String url = "http://service.tj.10086.cn/ics/ics?service=ajaxDirect/1/componant/componant/javascript/&pagename=componant&eventname=validateSms&smsCode=" + smsCode + "&ajaxSubmitType=get&ajax_randomcode=" + RandomNumUtil.getRandomNumber_16();
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "mobileNo1=5d2ce11ee6515cfb3f01b9dbfc1432bc6f5604cf@@4e0f6286071369dcbfb4f48848d55a2a8841e69c@@1462762611817; "
				+ "CmSore=\"" + CmSore + "\"; CmBrand=\"" + CmBrand + "\";"
				+ " gFNVKdebnF=" + gFNVKdebnF + "; WADE_ID=" + WADE_ID + "; rememberNum=" + mobile + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; loginName=" + loginName + "; FLAG_4GUsim=" + FLAG_4GUsim + ";"
				+ " is4Fuser=" + is4Fuser + "; CmLocation=220|220; CmProvid=tj; ";		
		getMethod.addHeader("Cookie", cookie); 
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);	
		
		res.getResponse();
	}
	
	private String billDetail = "";
	
	/**
	 * 获取通话详单
	 */
	public void getBillDetail(){
		String url = "http://service.tj.10086.cn/ics/ics?service=ajaxDirect/1/myMobile/"
				+ "myMobile/javascript/&pagename=myMobile&eventname=queryDetailRecords&billType=1001&"
				+ "searchMonth=" + billMonth + "&billTypeCode=BAS4038&roamType=&callType=&longType=&"
				+ "ajaxSubmitType=get&ajax_randomcode=0.15874403016641736";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "CmSore=\"" + CmSore + "\"; CmBrand=\"" + CmBrand + "\";"
				+ " gFNVKdebnF=" + gFNVKdebnF + "; WADE_ID=" + WADE_ID + "; rememberNum=" + mobile + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; loginName=" + loginName + "; FLAG_4GUsim=" + FLAG_4GUsim + ";"
				+ " is4Fuser=" + is4Fuser + "; CmLocation=220|220; CmProvid=tj; "
				+ "WT_FPC=id=27cbbf85e76b202726a1465354812380:lv=1465354846520:ss=1465354812380";		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://service.tj.10086.cn/ics/myMobile/myDetailRecords.html");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		System.out.println(res.getResponse());
		
		res.setResponse(res.getResponse().split("<[?]xml version=\"1.0\" encoding=\"UTF-8\"[?]>")[1]);
//		InputStream is = new ByteArrayInputStream(res.getResponse().getBytes());
//		billDetail = YD_XPath.getBillDetail_TJ(is);
		
		System.out.println(billDetail);
	}
	
}
