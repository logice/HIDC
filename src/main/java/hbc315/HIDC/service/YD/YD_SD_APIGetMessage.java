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
import hbc315.HIDC.util.QueryDateUtil;
import hbc315.HIDC.util.RandomNumUtil;


/**
 * 山东移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_SD_APIGetMessage {


	private static String mobile = "18268144220";
	private static int monthBefore = 0;
	
	private static String SMSCode = "165620";	//验证码
	private static String rnum = "r45m";			//图片验证码
	private String CmWebtokenid = "15066872340,sd";
	private String cmtokenid = "8apkIaTVUxmfId8mx7kYDkmTq2DtmVSD@sd.ac.10086.cn";
	private String dtCookie = "53EA0D6A40AAA3D45EA078350B6EB749|X2RlZmF1bHR8MQ"; 
	private String ssojsessionid = "wDy4XqyCMhnLJgVJ1RJrvTm19KJx3TGlDt0dD2ZlWtDjxNnTppJv!-1473332467";
	private String JSESSIONID_EMOBILE = "00005-SYOG2GcyK8tyjuDdvBw6L:18s0cjals";
	private String routem1 = "2f16b2591666773afda47596bc867067";
	private String BIGipServerpool_eMobile_5354 = "904925962.20480.0000";
	private String route1 = "081d74c778fd458399da86a1326f70e5";
	private String JSESSIONID = "0000O_y_YnKQZsWA8fhZ9KUFODF:18s0atv1c";
	private String BIGipServerApache_pool_80 = "";
	private String BIGipServerwls_pool_86 = "1240470282.31518.0000";
	private String startDay = "21";
	
	public static void main(String[] args) {
		YD_SD_APIGetMessage yd = new YD_SD_APIGetMessage();

//		yd.ValidateSMSCode();
		yd.getDetailCallList();
	}	
	
	
	private String redirectURL = "";
	/**
	 * 验证短信验证码
	 * 
	 */
	public void ValidateSMSCode(){
		String url = "http://www.sd.10086.cn/eMobile/checkSmsPass_commit.action";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; "
				+ "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "route1=" + route1 + "; "
				+ "NOTLOGIN_DEFAIL_CITY=532; CmLocation=531|532; CmProvid=sd";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sd.10086.cn");
		postMethod.addHeader("Referer", "http://www.sd.10086.cn/eMobile/checkSmsPass.action?menuid=billdetails");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("menuid", "billdetails"));
		params.add(new BasicNameValuePair("fieldErrFlag", ""));
		params.add(new BasicNameValuePair("contextPath", "/eMobile"));
		params.add(new BasicNameValuePair("randomSms", SMSCode));
		params.add(new BasicNameValuePair("confirmCode", rnum));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		try {
			redirectURL = res.getLocation();
			System.out.println("redirectURL: " + redirectURL);
		} catch (Exception e) {
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "";
		try {
			if(QueryDateUtil.getDayNow() >= Integer.valueOf(startDay)){
				url = "http://www.sd.10086.cn/eMobile/queryBillDetail_detailBillAjax.action?dateType=byMonth&"
						+ "startDate=" + QueryDateUtil.getBeforeMonth(monthBefore) + startDay + "&menuid=billdetails&endDate="
						+ QueryDateUtil.getBeforeMonth(monthBefore-1) + (Integer.valueOf(startDay)-1) + "&month="
						+ QueryDateUtil.getBeforeMonth(monthBefore) + ",%20" + QueryDateUtil.getBeforeMonth(monthBefore)
						+ "&cycle=" + QueryDateUtil.getBeforeMonth(monthBefore) + startDay + "&pageid=" + RandomNumUtil.getRandomNumber_16() + "&queryType=2";
			}else{
				url = "http://www.sd.10086.cn/eMobile/queryBillDetail_detailBillAjax.action?"
						+ "dateType=byMonth&startDate=" + QueryDateUtil.getBeforeMonth(monthBefore+1) + startDay + "&menuid=billdetails&endDate="
						+ QueryDateUtil.getBeforeMonth(monthBefore) + (Integer.valueOf(startDay)-1) + "&month="
						+ QueryDateUtil.getBeforeMonth(monthBefore+1) + ",%20" + QueryDateUtil.getBeforeMonth(monthBefore+1)
						+ "&cycle=" + QueryDateUtil.getBeforeMonth(monthBefore+1) + startDay + "&pageid=" + RandomNumUtil.getRandomNumber_16() + "&queryType=2";
			}
		} catch (Exception e) {
			System.out.println("getStartDay error!");
			return ;
		}

		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "eMobile_roteServer=EMOBILE_PRODUCT_CLUSTER; "
				+ "dtCookie=" + dtCookie + "; "
				+ "BIGipServerApache_pool_80=" + BIGipServerApache_pool_80 + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "JSESSIONID_EMOBILE=" + JSESSIONID_EMOBILE + "; "
				+ "routem1=" + routem1 + "; "
				+ "BIGipServerpool_eMobile_5354=" + BIGipServerpool_eMobile_5354 + "; "
				+ "ssojsessionid=" + ssojsessionid + "; "
				+ "BIGipServerwls_pool_86=" + BIGipServerwls_pool_86 + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "route1=" + route1 + "; "
				+ "NOTLOGIN_DEFAIL_CITY=532; CmLocation=531|532; CmProvid=sd";
		
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://www.sd.10086.cn");
//		postMethod.addHeader("Referer", "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
		
	}
}
