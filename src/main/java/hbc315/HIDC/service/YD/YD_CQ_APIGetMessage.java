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
import hbc315.HIDC.util.RandomNumUtil;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_CQ_APIGetMessage {


	private static String mobile = "15723030546";
	private static String month = "201606";
	
	private static String SMSCode = "746097";	//验证码
	private String WADE_ID = "D1CDE079F0F14CFEA5270DCFAF5A296F";
	private String CmWebtokenid = "15723030546,cq,BrandGotone";
	private String SESSION_ID = "tFYi7cXDIXCn4tKTZ3dRCiv";
	private String IPAddress = "12442101174wd_user_ip";

	
	public static void main(String[] args) {
		YD_CQ_APIGetMessage yd = new YD_CQ_APIGetMessage();

		yd.ValidateSMSCode();
		yd.getDetailCallList();
	}	
	
	/**
	 * 验证短信验证码
	 * 
	 */
	public void ValidateSMSCode(){
		String url = "http://service.cq.10086.cn/ics?service=ajaxDirect/1/secondValidate"
				+ "/secondValidate/javascript/&pagename=secondValidate&eventname=checkSMSINFO"
				+ "&cond_USER_PASSSMS=" + SMSCode + "&cond_CHECK_TYPE=DETAIL_BILL&cond_loginType=2"
				+ "&ajaxSubmitType=post&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; "
				+ "rememberNum=" + mobile + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "loginName=" + mobile + "; "
				+ IPAddress + "=" + IPAddress + "; "
				+ "SESSION_ID=" + SESSION_ID + "; "
				+ "CmLocation=230|230; CmProvid=cq";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.cq.10086.cn");
		postMethod.addHeader("Referer", "http://service.cq.10086.cn/myMobile/detailBill.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://service.cq.10086.cn/ics?service=ajaxDirect/1/myMobile/"
				+ "myMobile/javascript/&pagename=myMobile&eventname=getDetailBill&"
				+ "cond_DETAIL_TYPE=3&cond_QUERY_TYPE=0&cond_QUERY_MONTH=" + month + "&"
				+ "cond_GOODS_ENAME=XFMX&cond_GOODS_NAME=%E6%B6%88%E8%B4%B9%E6%98%8E%E7%BB%86&cond_TRANS_TYPE=Q&"
				+ "cond_GOODS_ID=2015060500000083&ajaxSubmitType=post&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "WADE_ID=" + WADE_ID + "; "
				+ "rememberNum=" + mobile + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "loginName=" + mobile + "; "
				+ IPAddress + "=" + IPAddress + "; "
				+ "SESSION_ID=" + SESSION_ID + "; "
				+ "CmLocation=230|230; CmProvid=cq";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://service.cq.10086.cn");
		postMethod.addHeader("Referer", "http://service.cq.10086.cn/myMobile/detailBill.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
}
