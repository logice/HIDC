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
 * 浙江移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_ZJ_APIGetMessage {


	private static String mobile = "18268144220";
	private static String month = "06-2016";
	
	private static String SMSCode = "595583";	//验证码
	private String CmWebtokenid = "18268144220,zj";
	private String cmtokenid = "bde3f7db951f45299838b55fe3c8092f@zj.ac.10086.cn";
	private String unity_SAMLart = "0eaa221ddc0f4da7af97e3964f13e52f";
	private String citybrand = "6M9An8DFwUPQNWOOhyL9oTrbGuYylaB2";
	private String WTSESSION = "td5w-D_mZPaJXExQAuMkamWjkKiCpd2Qf_iZd6hqlMgoXa3OX3II!-188784637";
	
	public static void main(String[] args) {
		YD_ZJ_APIGetMessage yd = new YD_ZJ_APIGetMessage();

		yd.ValidateSMSCode();
		yd.getDetailCallList();
	}	
	
	/**
	 * 验证短信验证码
	 * 
	 */
	public void ValidateSMSCode(){
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
		params.add(new BasicNameValuePair("validateCode", SMSCode));
		params.add(new BasicNameValuePair("bid", "BC5CC0A69BC10482E044001635842132"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?bid=&menuId=13009&listtype=1&month=" + month;
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
		postMethod.addHeader("Referer", "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
}
