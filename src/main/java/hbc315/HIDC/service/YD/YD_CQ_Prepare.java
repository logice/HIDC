package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;

public class YD_CQ_Prepare {
	
	private static String mobile = "15723030546";
	
	public static void main(String[] args) {
		YD_CQ_Prepare yd = new YD_CQ_Prepare();
		
		yd.getWADE_ID();
		yd.getSESSION_ID();
		yd.getIpToCookie();
		
		yd.getRnum();
		yd.show();
	}

	private void show(){
		System.out.println("WADE_ID: " + WADE_ID);
		System.out.println("SESSION_ID: " + SESSION_ID);
		System.out.println("SESSIONID_S52SATURN01: " + SESSIONID_S52SATURN01);
		System.out.println("jsessionid1: " + jsessionid1);
		System.out.println("BIGipServertyrz-pool: " + BIGipServertyrz_pool);
		System.out.println("IPAddress: " + IPAddress);
	}
	
	private String WADE_ID = "";
	private String SSOImage= "";
	
	private void getWADE_ID() {
		String url = "https://service.cq.10086.cn/ics?service=ajaxDirect/1/login/login"
				+ "/javascript/&pagename=login&eventname=initLogin&&"
				+ "ajaxSubmitType=post&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpPost postMethod = new HttpPost(url);

		postMethod.addHeader("Origin","https://service.cq.10086.cn");
		postMethod.addHeader("Referer","https://service.cq.10086.cn/httpsFiles/pageLogin.html");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("WADE_ID")) {
				WADE_ID = c.getValue();
				System.out.println("WADE_ID: " + WADE_ID);
			}
		}
		
		SSOImage = res.getResponse().split("imgSrc\":\"")[1];
		SSOImage = SSOImage.split("\"")[0];
		System.out.println("SSOImage: " + SSOImage);
		
		System.out.println(res.getResponse());
	}

	private String SESSIONID_S52SATURN01 = "";
	private String SESSION_ID = "";
	
	private void getSESSION_ID() {
		String url = "http://www.cq.10086.cn/saturn/app?service=page/Home&listener=getCustInfo&"
				+ "CHAN_ID=E003&TELNUM=&?idsite=1&rec=1&url=https%3A%2F%2Fservice.cq.10086.cn%2F"
				+ "httpsFiles%2FpageLogin.html&res=1366x768&col=24-bit&h=16&m=35&s=7&cookie=1&"
				+ "urlref=&rand=0.5231493532191962&pdf=1&qt=0&realp=0&wma=0&dir=0&fla=1&java=1&"
				+ "gears=0&ag=0&action_name=%2525E9%252587%25258D%2525E5%2525BA%252586%2525E7%25"
				+ "25A7%2525BB%2525E5%25258A%2525A8%2525E7%2525BD%252591%2525E4%2525B8%25258A%25"
				+ "25E8%252590%2525A5%2525E4%2525B8%25259A%2525E5%25258E%252585%25257C%2525E5%25"
				+ "2585%252585%2525E5%252580%2525BC%2525EF%2525BC%25258C%2525E7%2525BC%2525B4%25"
				+ "25E8%2525B4%2525B9%2525EF%2525BC%25258C%2525E4%2525B8%25259A%2525E5%25258A%25"
				+ "25A1%2525E5%25258A%25259E%2525E7%252590%252586%2525EF%2525BC%25258C%2525E7%25"
				+ "25BD%252591%2525E4%2525B8%25258A%2525E8%252587%2525AA%2525E5%25258A%2525A9%25"
				+ "25E6%25259C%25258D%2525E5%25258A%2525A1%2525EF%2525BC%25258C%2525E7%2525A7%25"
				+ "25BB%2525E5%25258A%2525A8%2525E6%252594%2525B9%2525E5%25258F%252598%2525E7%25"
				+ "2594%25259F%2525E6%2525B4%2525BB%2525E3%252580%252582";
		HttpGet getMethod = new HttpGet(url);

//		String cookies = "CmLocation=100|100; CmProvid=tj; Webtrends="+ Webtrends;
//		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("SESSION_ID")) {
				SESSION_ID = c.getValue();
				System.out.println("SESSION_ID: " + SESSION_ID);
			}
			if (c.getName().equals("SESSIONID_S52SATURN01")) {
				SESSIONID_S52SATURN01 = c.getValue();
				System.out.println("SESSIONID_S52SATURN01: " + SESSIONID_S52SATURN01);
			}
		}
		System.out.println(res.getResponse());
	}
	
	private String IPAddress = "";
	
	private void getIpToCookie() {
		String url = "https://service.cq.10086.cn/ics?service=ajaxDirect/1/home/"
				+ "home/javascript/&pagename=home&eventname=getIpToCookie&&"
				+ "ajaxSubmitType=get&ajax_randomcode=" + RandomNumUtil.getTimeAndRandom17();
		HttpGet getMethod = new HttpGet(url);

		String cookies = "WADE_ID=" + WADE_ID + "; SESSION_ID=" + SESSION_ID;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		try {
			IPAddress = res.getResponse().split("userip\":\"")[1];
			IPAddress = IPAddress.split("\"")[0];
			IPAddress = IPAddress.replace(".", "") + "wd_user_ip";
		} catch (Exception e) {
			
		}

		System.out.println("IPAddress: " + IPAddress);
		System.out.println(res.getResponse());
	}
	
	private String jsessionid1 = "";
	private String BIGipServertyrz_pool = "";
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		HttpGet getMethod = new HttpGet(SSOImage);

		String cookies = "SESSION_ID="+ SESSION_ID;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
			
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("jsessionid1")){
				jsessionid1 = c.getValue();
			}
			if(c.getName().equals("BIGipServertyrz-pool")){
				BIGipServertyrz_pool = c.getValue();
			}
		}
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_CQ.jpg";
			FileOutputStream fos = new FileOutputStream(picName);
			
			byte[] b = new byte[1024];
			while((is.read(b)) != -1){
				fos.write(b);
			}
			
			fos.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
}
