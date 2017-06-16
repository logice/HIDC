package hbc315.HIDC.service.YD;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


/**
 * 广东移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_GD_SendMessage {


	private static String mobile = "13670046183";
	private static String servicePassword = "199325";	
	
	private String rnum = "rsdp";	//验证码
	
	private String JUCSSESSIONID = "0000IzVBCo7jGWyFjvHJBPbmwKm:17qh2ju7k";
	private String e = "10001";
	private String maxdigits = "67";
	private String n = "86c15cd72a9bbeab9fceb0f79dd05e314b52d13a9c3607748068d491ee7ae5adec191d6e0a14927eb0e14dcd99d80cb8bae1fd3d3f82dca9e39b1d9350297d8d";
	
	public static void main(String[] args) {
		YD_GD_SendMessage yd = new YD_GD_SendMessage();
	
		yd.login(mobile, yd.passwordEncrypt(servicePassword));
		yd.getECOPPJSESSIONID();
		yd.login1();
		yd.Signup1();
		yd.getJSESSIONID();
		yd.login2();
		yd.Signup2();
		yd.sendSMSCode();
		yd.show();
	}
	
	private void show(){
		System.out.println("CmWebtokenid: " + CmWebtokenid);
		System.out.println("cmtokenid: " + cmtokenid);
		System.out.println("_a_m_b_b: " + _a_m_b_b);
		System.out.println("_st: " + _st);
		System.out.println("JUCSSESSIONID: " + JUCSSESSIONID);
		System.out.println("_n_r_n_r: " + _n_r_n_r);
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("n: " + n);
		System.out.println("_t_y_t_b_ip: " + _t_y_t_b_ip);
		System.out.println("_a_h_b_c: " + _a_h_b_c);
		System.out.println("ECOPPJSESSIONID: " + ECOPPJSESSIONID);
	}
	

	private String ECOPPJSESSIONID = "";
	/**
	 * getECOPPJSESSIONID
	 */
	private void getECOPPJSESSIONID(){
		String url = "http://gd.10086.cn/commodity/servicio/myService/queryBrand.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmLocation=200|200; CmProvid=gd; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "http://gd.10086.cn");
		postMethod.addHeader("Referer", "http://gd.10086.cn/my/ACCOUNTS_BALANCE_SEARCH.shtml?dt=1389283200000");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("ECOPPJSESSIONID")){
				ECOPPJSESSIONID = c.getValue();
				System.out.println("ECOPPJSESSIONID: "+ ECOPPJSESSIONID);
			}
		}
		
		System.out.println(res.getResponse());
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
	
	
	private String CmWebtokenid = ""; 
	private String cmtokenid = "";
	private String _st = "";
	private String _a_m_b_b = "";
	private String _n_r_n_r = "";
	
	/**
	 * 密码登录
	 */
	public void login(String mobile ,String passwordEncrypt){
		String url = "https://gd.ac.10086.cn/ucs/login/register.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_mobile=" + mobile + "; _loginType=2; JUCSSESSIONID=" + JUCSSESSIONID;
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Origin","https://gd.ac.10086.cn");
		postMethod.addHeader("Referer","https://gd.ac.10086.cn/ucs/login/signup.jsps");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginType", "2"));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("password", passwordEncrypt));
		params.add(new BasicNameValuePair("imagCaptcha", rnum));
		params.add(new BasicNameValuePair("cookieMobile", "on"));
		params.add(new BasicNameValuePair("bizagreeable", "true"));
		params.add(new BasicNameValuePair("exp", ""));
		params.add(new BasicNameValuePair("cid", ""));
		params.add(new BasicNameValuePair("area", ""));
		params.add(new BasicNameValuePair("resource", ""));
		params.add(new BasicNameValuePair("channel", "0"));
		params.add(new BasicNameValuePair("reqType", "0"));
		params.add(new BasicNameValuePair("backURL", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("CmWebtokenid")){
				CmWebtokenid = c.getValue();
				System.out.println("CmWebtokenid: "+CmWebtokenid);
			}
			if(c.getName().equals("cmtokenid")){
				cmtokenid = c.getValue();
				System.out.println("cmtokenid: "+ cmtokenid);
			}
			if(c.getName().equals("_st")){
				_st = c.getValue();
				System.out.println("_st: "+ _st);
			}
			if(c.getName().equals("_a_m_b_b")){
				_a_m_b_b = c.getValue();
				System.out.println("_a_m_b_b: "+ _a_m_b_b);
			}
			if(c.getName().equals("_n_r_n_r")){
				_n_r_n_r = c.getValue();
				System.out.println("_n_r_n_r: "+ _n_r_n_r);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 第一次授权
	 */
	private void login1() {
		String url = "https://gd.ac.10086.cn/ucs/login/loading.jsps?reqType=0&channel=0&cid=10003&area=%2Fcommodity&resource=%2Fcommodity%2Fservicio%2FmyService%2FqueryBrand.jsps&loginType=2&optional=true&exp=&backURL=http%3A%2F%2Fgd.10086.cn%2Fmy%2FACCOUNTS_BALANCE_SEARCH.shtml%3Fdt%3D1389283200000";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "_mobile=" + mobile + "; "
				+ "_loginType=2; "
				+ "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "CmLocation=200|200; CmProvid=gd";
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://gd.10086.cn/my/ACCOUNTS_BALANCE_SEARCH.shtml?dt=1389283200000");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		System.out.println(res.getResponse());
	}
	
	private String redirectURL = "";
	/**
	 * Signup1
	 */
	public void Signup1(){
		String url = "https://gd.ac.10086.cn/ucs/login/signup.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_mobile=" + mobile + "; "
				+ "_loginType=2; "
				+ "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "CmLocation=200|200; CmProvid=gd";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://gd.ac.10086.cn");
		postMethod.addHeader("Referer", "https://gd.ac.10086.cn/ucs/login/loading.jsps?reqType=0&channel=0&cid=10003&area=%2Fcommodity&resource=%2Fcommodity%2Fservicio%2FmyService%2FqueryBrand.jsps&loginType=2&optional=true&exp=&backURL=http%3A%2F%2Fgd.10086.cn%2Fmy%2FACCOUNTS_BALANCE_SEARCH.shtml%3Fdt%3D1389283200000");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("backURL", "http%3A%2F%2Fgd.10086.cn%2Fmy%2FACCOUNTS_BALANCE_SEARCH.shtml%3Fdt%3D1389283200000"));
		params.add(new BasicNameValuePair("reqType", "0"));
		params.add(new BasicNameValuePair("channel", "0"));
		params.add(new BasicNameValuePair("cid", "10003"));
		params.add(new BasicNameValuePair("area", "%2Fcommodity"));
		params.add(new BasicNameValuePair("resource", "%2Fcommodity%2Fservicio%2FmyService%2FqueryBrand.jsps"));
		params.add(new BasicNameValuePair("loginType", "2"));
		params.add(new BasicNameValuePair("optional", "on"));
		params.add(new BasicNameValuePair("exp", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);

		try {
			redirectURL = res.getResponse().split("content\":\"")[1];
			redirectURL = redirectURL.split("\",")[0];
			System.out.println("redirectURL: " + redirectURL);
			
			redirect();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(res.getResponse());
	}
	
	private String _a_h_b_c = "";
	/**
	 * 跳转
	 */
	private void redirect() {
		HttpGet getMethod = new HttpGet(redirectURL);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "CmLocation=200|200; CmProvid=gd; _a_h_b_c=GD; CmLocationB=200%7C200; ";
		getMethod.addHeader("Cookie", cookie);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("_a_h_b_c")) {
				_a_h_b_c = c.getValue();
				System.out.println("_a_h_b_c: " + _a_h_b_c);
			}
			if (c.getName().equals("ECOPPJSESSIONID")) {
				ECOPPJSESSIONID = c.getValue();
				System.out.println("ECOPPJSESSIONID: " + ECOPPJSESSIONID);
			}
		}
		
	}
	
	private String JSESSIONID = "";
	private String _t_y_t_b_ip = "";
	/**
	 * 获取JSESSIONID
	 * 
	 */
	public void getJSESSIONID(){
		String url = "http://gd.10086.cn/common/include/public/isOnline.jsp?_=" + System.currentTimeMillis();
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "_a_m_b_b=" + _a_m_b_b;
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
		
		try {
			JSONObject jsonObj = JSONObject.parseObject(res.getResponse().trim());
			_t_y_t_b_ip = (String) jsonObj.get("ip");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 第二次授权
	 */
	private void login2() {
		String url = "https://gd.ac.10086.cn/ucs/second/loading.jsps?reqType=0&channel=0&cid=10003&backURL=http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml&type=2";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "CmLocation=200|200; CmProvid=gd";
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * Signup2
	 */
	public void Signup2(){
		String url = "https://gd.ac.10086.cn/ucs/second/index.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_mobile=" + mobile + "; "
				+ "_loginType=2; "
				+ "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; "
				+ "CmLocation=200|200; CmProvid=gd";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://gd.ac.10086.cn");
		postMethod.addHeader("Referer", "https://gd.ac.10086.cn/ucs/second/loading.jsps?reqType=0&channel=0&cid=10003&backURL=http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml&type=2");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("cid", "10003"));
		params.add(new BasicNameValuePair("channel", "0"));
		params.add(new BasicNameValuePair("reqType", "0"));
		params.add(new BasicNameValuePair("backURL", "http%3A%2F%2Fgd.10086.cn%2Fmy%2FREALTIME_LIST_SEARCH.shtml"));
		params.add(new BasicNameValuePair("type", "2"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
	
	
	/**
	 * 发送手机验证码
	 */
	private void sendSMSCode(){
		String url = "https://gd.ac.10086.cn/ucs/captcha/dpwd/send.jsps";
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
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("dt", "61"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		System.out.println(res.getResponse());
	}
	

}
