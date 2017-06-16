package hbc315.HIDC.service.YD;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.QueryDateUtil;


/**
 * 广东移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_GD_APIGetMessage {


	private static String mobile = "13670046183";
	private static String month = "201605";
	
	private static String SMSCode = "605367";	//验证码
	private String CmWebtokenid = "13670046183,gd";
	private String cmtokenid = "8a740d35551ab407015597350bac2e66@gd.ac.10086.cn";
	private String _a_m_b_b = "13670046183~SZ~1~2";
	private String _st = "8a740d35551ab407015597350bac2e66";
	private String JUCSSESSIONID = "0000IzVBCo7jGWyFjvHJBPbmwKm:17qh2ju7k";
	private String _n_r_n_r = "1";
	private String JSESSIONID = "0000f1bom8EH_whRK7E-wRRNjIK:15io2ig09";
	private String _t_y_t_b_ip = "124.42.101.174";
	private String _a_h_b_c = "SZ";
	private String ECOPPJSESSIONID = "0000CjfnSQlq8KTg5QMtBjRZkjL:17sjc4rkd";
	private String e = "10001";
	private String maxdigits = "67";
	private String n = "86c15cd72a9bbeab9fceb0f79dd05e314b52d13a9c3607748068d491ee7ae5adec191d6e0a14927eb0e14dcd99d80cb8bae1fd3d3f82dca9e39b1d9350297d8d";
	
	
	public static void main(String[] args) {
		YD_GD_APIGetMessage yd = new YD_GD_APIGetMessage();

//		yd.ValidateSMSCode(yd.passwordEncrypt(SMSCode));
//		yd.queryBrand();
//		yd.queryData();
//		yd.isOnline();
//		yd.isOnline2();
//		yd.getQueryMonth();
		yd.getDetailCallList();
	}	
	
	public String passwordEncrypt(String pwd){
		String result = "";
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			
			String fileName = "src/main/resource/js/YD/GD/Encrypt.js";
			FileReader reader = new FileReader(fileName); // 执行指定脚本
			engine.eval(reader);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数
				result = (String) invoke.invokeFunction("encryptForm", e, n, Integer.valueOf(maxdigits), pwd);
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	private String _sst = "";
	/**
	 * 第二次验证
	 */
	private void ValidateSMSCode(String pwdEncrypt){
		String url = "https://gd.ac.10086.cn/ucs/second/authen.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmLocation=200|200; CmProvid=gd; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
//				+ "_gscu_1502255179=" + _gscu_1502255179 + "; "
//				+ "_gscs_1502255179=" + _gscs_1502255179 + "; "
//				+ "_gscbrs_1502255179=" + _gscbrs_1502255179 + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://gd.ac.10086.cn");
		postMethod.addHeader("Referer", "https://gd.ac.10086.cn/ucs/second/loading.jsps?reqType=0&channel=0&cid=10003&backURL=http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml&type=2");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dpwd", pwdEncrypt));
		params.add(new BasicNameValuePair("type", "2"));
		params.add(new BasicNameValuePair("cid", "10003"));
		params.add(new BasicNameValuePair("channel", "0"));
		params.add(new BasicNameValuePair("reqType", "0"));
		params.add(new BasicNameValuePair("backURL", "http%3A%2F%2Fgd.10086.cn%2Fmy%2FREALTIME_LIST_SEARCH.shtml"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("_sst")){
				_sst = c.getValue();
				System.out.println("_sst: " + _sst);
			}
			if(c.getName().equals("_a_m_b_b")){
				_a_m_b_b = c.getValue();
				System.out.println("_a_m_b_b: " + _a_m_b_b);
			}
		}

		System.out.println(res.getResponse());
	}
	
	private void queryBrand(){
		String url = "http://gd.10086.cn/commodity/servicio/myService/queryBrand.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
				+ "_a_h_b_c=" + _a_h_b_c;
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://gd.10086.cn");
		postMethod.addHeader("Referer", "http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
	
	private void queryData(){
		String url = "http://gd.10086.cn/commodity/servicio/servicioForwarding/queryData.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
				+ "_a_h_b_c=" + _a_h_b_c;
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://gd.10086.cn");
		postMethod.addHeader("Referer", "http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("servCode", "REALTIME_LIST_SEARCH"));
		params.add(new BasicNameValuePair("operaType", "QUERY"));
		params.add(new BasicNameValuePair("Payment_startDate", QueryDateUtil.getToday() + "000000"));
		params.add(new BasicNameValuePair("Payment_endDate", QueryDateUtil.getToday() + "235959"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * isOnline
	 * 
	 */
	public void isOnline(){
		String url = "http://gd.10086.cn/common/include/public/isOnline.jsp?_=" + System.currentTimeMillis();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://gd.10086.cn");
		postMethod.addHeader("Referer", "http://gd.10086.cn/my/ACCOUNTS_BALANCE_SEARCH.shtml?dt=1389283200000");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: " + JSESSIONID);
			}
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * isOnline2
	 * 
	 */
	public void isOnline2(){
		String url = "http://gd.10086.cn/common/include/public/isOnline.jsp?_=" + System.currentTimeMillis();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://gd.10086.cn");
		postMethod.addHeader("Referer", "http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
	
	private String uniqueTag = "20160628213856798";
	/**
	 * 获取查询时间
	 * 
	 */
	public void getQueryMonth(){
		String url = "http://gd.10086.cn/commodity/servicio/nostandardserv/realtimeListSearch/query.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
//				+ "_gscu_1502255179=" + _gscu_1502255179 + "; "
//				+ "_gscs_1502255179=" + _gscs_1502255179 + "; "
//				+ "_gscbrs_1502255179=" + _gscbrs_1502255179 + "; "
				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
				+ "_a_h_b_c=" + _a_h_b_c + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "http://service.zj.10086.cnhttp://gd.10086.cn/my/REALTIME_LIST_SEARCH_GOTONE.shtml?uniqueTag=" + uniqueTag);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("month", month));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		try {
			uniqueTag = res.getResponse().split("value\":\"")[1];
			uniqueTag = uniqueTag.split("\"")[0];
			System.out.println("uniqueTag: " + uniqueTag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://gd.10086.cn/commodity/servicio/nostandardserv/realtimeListSearch/ajaxRealQuery.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
				+ "_a_h_b_c=" + _a_h_b_c + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("startTimeReal", ""));
		params.add(new BasicNameValuePair("endTimeReal", ""));
		params.add(new BasicNameValuePair("uniqueTag", uniqueTag));
		params.add(new BasicNameValuePair("month", ""));
		params.add(new BasicNameValuePair("monthListType", "0"));
		params.add(new BasicNameValuePair("isChange", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
}
