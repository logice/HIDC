package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.RandomNumUtil;

/**
 * 江苏移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_JX_Prepare {
	private String mobile = "15270319628";
	
	public static void main(String[] args) {
		YD_JX_Prepare yd = new YD_JX_Prepare();
			
		yd.getJSESSIONID();
		yd.getJSESSIONID2();
		yd.getJSESSIONID3();
		yd.getRnum();
		yd.show();
	}

	/**
	 * 显示
	 */
	private void show(){
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("g_int_sso_443: " + g_int_sso_443);
		System.out.println("spid: " + spid);
		System.out.println("sid: " + sid);
	}
	
	
	private String JSESSIONID = "";
	private String g_int_sso_443 = "";
	private String sid = "";
	private String spid = "";
	
	/**
	 * getJSESSIONID
	 */
	public void getJSESSIONID(){
		String url = "https://jx.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID")) {
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID " + JSESSIONID);
			}
			if (c.getName().equals("g_int_sso_443")) {
				g_int_sso_443 = c.getValue();
				System.out.println("g_int_sso_443 " + g_int_sso_443);
			}
		}
		
		try {
			spid = res.getResponse().split("spid\" value=\"")[1];
			spid = spid.split("\"")[0];
			sid = res.getResponse().split("sid\" value=\"")[1];
			sid = sid.split("\"")[0];
			System.out.println("spid: " + spid);
			System.out.println("sid: " + sid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * getJSESSIONID2
	 */
	public void getJSESSIONID2(){
		String url = "https://jx.ac.10086.cn/4login/1.html";
		HttpGet getMethod = new HttpGet(url);

		String cookie = "JSESSIONID=" + JSESSIONID + "; g_int_sso_443=" + g_int_sso_443;
		
		getMethod.addHeader("Cookie",cookie);
		getMethod.addHeader("Referer","https://jx.ac.10086.cn/login");
		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("g_int_sso_443")) {
				g_int_sso_443 = c.getValue();
				System.out.println("g_int_sso_443 " + g_int_sso_443);
			}
		}
		System.out.println(res.getResponse());
	}
	
	
	/**
	 * getJSESSIONID3
	 */
	public void getJSESSIONID3(){
		String url = "https://jx.ac.10086.cn/mhextra/pages/Reuserinfo/selAddr.do?callback=jQuery18305792586007155478_" + System.currentTimeMillis() + "&addrnum=" + mobile + "&_=" + System.currentTimeMillis();
		HttpGet getMethod = new HttpGet(url);
		String cookie = "JSESSIONID=" + JSESSIONID + "; CmLocation=791|791; CmProvid=jx; g_int_sso_443=" + g_int_sso_443;
		
		getMethod.addHeader("Cookie",cookie);
		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("g_int_sso_443")) {
				g_int_sso_443 = c.getValue();
				System.out.println("g_int_sso_443 " + g_int_sso_443);
			}
		}
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://jx.ac.10086.cn/common/image.jsp?l=" + RandomNumUtil.getRandomNumber_18();
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmLocation=791|791; CmProvid=jx; JSESSIONID=" + JSESSIONID + "; g_int_sso_443=" + g_int_sso_443;
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "https://jx.ac.10086.cn/login");
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_JX.jpg";
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
