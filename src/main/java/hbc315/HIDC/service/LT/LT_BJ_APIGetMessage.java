package hbc315.HIDC.service.LT;

import java.io.IOException;
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
import com.alibaba.fastjson.JSONObject;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;

/**
 * 查询联通用户通话详单，需要提供网站登录密码（联通全国查询都通用，程序默认北京）
 * @author zcy
 *
 */
public class LT_BJ_APIGetMessage {


	private String mobile = "18513068661";
	private String password = "090802";	
	
	public JSONObject getCallDetail(){
		checkNeedVerify();
		Login();
		checkLogin();
		return getCallDetail(1,"2016-04-01","2016-04-30");
	}
	
	public void main(String[] args) {		
		checkNeedVerify();
		Login();
		checkLogin();
		getCallDetail(1,"2016-04-01","2016-04-30");
	}
	
	/**
	 * 拿取登录成果后的各种token
	 * 
	 */
	public void checkNeedVerify(){
		HttpGet getMethod = new HttpGet("https://uac.10010.com/portal/Service/CheckNeedVerify?"
				+ "callback=jQuery17206171322869938429_1462934682905&userName=" + mobile + "&pwdType=01&_=" + System.currentTimeMillis());
		
		getMethod.addHeader("Referer", "https://uac.10010.com/portal/homeLogin");
				
		ResponseValue res = CommonHttpMethod.doGet(getMethod);		
		
	}
	
	private String piw = "";
	private String JUT = "";
	private String _uop_id = "";
	
	/**
	 * 网站密码登录
	 * 
	 */
	public void Login(){
		String url = "https://uac.10010.com/portal/Service/MallLogin?"
				+ "callback=jQuery17206171322869938429_1462934682906&req_time=" + System.currentTimeMillis()
				+ "&redirectURL=http%3A%2F%2Fwww.10010.com&userName=" + mobile + "&password=" + password + "&pwdType=01"
				+ "&productType=01&redirectType=01&rememberMe=1&_="+ (System.currentTimeMillis()+3);
		
		HttpGet getMethod = new HttpGet(url);
		
		getMethod.addHeader("Referer", "https://uac.10010.com/portal/homeLogin");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("piw")){
				piw = c.getValue();
			}
			
			if(c.getName().equals("JUT")){
				JUT = c.getValue();
			}
			
			if(c.getName().equals("_uop_id")){
				_uop_id = c.getValue();
			}
		}
		System.out.println("piw: "+ piw);
		System.out.println("JUT: "+ JUT);
		System.out.println("_uop_id: "+ _uop_id);
		System.out.println(res.getResponse());
		System.out.println();
	}
	
	private String route = "";
	private String e3 = "";
	
	/**
	 * 跳转到iservice域名后验证登录是否成功
	 * 
	 */
	public void checkLogin(){
		HttpPost postMethod = new HttpPost("http://iservice.10010.com/e3/static/check/checklogin/?_="+ System.currentTimeMillis());
		
		String cookie =  " JUT=" + JUT + ";";
		
		postMethod.addHeader("Cookie",cookie);
		postMethod.addHeader("Referer","http://iservice.10010.com/e3/query/call_dan.html?menuId=000100030001");
		postMethod.addHeader("Host","iservice.10010.com");
		postMethod.addHeader("x-requested-with","XMLHttpRequest");

		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("route")){
				route = c.getValue();
			}
			
			if(c.getName().equals("e3")){
				e3 = c.getValue();
			}
		}
				
		System.out.println("route: "+ route);
		System.out.println("e3: "+ e3);
		System.out.println();
	}
	
	/**
	 * 获取通话详单
	 * 
	 */
	public JSONObject getCallDetail(int pageNo,String beginDate,String endDate){
		HttpPost postMethod = new HttpPost("http://iservice.10010.com/e3/static/query/callDetail?_=" + System.currentTimeMillis() + "&menuid=000100030001");
		
		String cookie = " piw=" + piw + ";"
				+ " JUT=" + JUT + ";"
				+ " _uop_id=" + _uop_id + "; "
				+ "route=" + route + "; "
				+ "e3="+e3;
		
		postMethod.addHeader("Cookie",cookie);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNo", String.valueOf(pageNo)));
		params.add(new BasicNameValuePair("pageSize", "100"));
		params.add(new BasicNameValuePair("beginDate", beginDate));
		params.add(new BasicNameValuePair("endDate", endDate));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		System.out.println(res.getResponse());
		
		
		JSONObject jsonObj = JSONObject.parseObject(res.getResponse());
		int totalRecord = (Integer)jsonObj.get("totalRecord");
		
		if(pageNo*100 < totalRecord){
			getCallDetail(pageNo+1, beginDate, endDate);
		}
		return jsonObj;
	}
	
}
