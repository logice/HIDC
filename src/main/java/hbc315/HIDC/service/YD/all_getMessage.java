package hbc315.HIDC.service.YD;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class all_getMessage {
	
	private static String smsCode = "845742";	
	
	public static void main(String[] args) {
		all_getMessage yd = new all_getMessage();

		yd.smsCheck(smsCode);
		yd.getDetail();
		yd.out();

	}
	
	private void out(){
		//System.out.println("JSESSIONID: "+JSESSIONID);
	}
	
	private String JSESSIONID = "c2a003c2-b68f-4955-8ff0-b7244d9e8364";
	private static String UID = "92a60b9ceae1484dace9a5b29bd5987b";
	
	/**
	 * 手机验证码
	 */
	public void smsCheck(String smsCode){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/tempIdentCode/getTmpIdentCode";
		HttpPost postMethod = new HttpPost(url);
		
		String data = "{\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"0\",\"reqBody\":{\"businessCode\":\"01\",\"cellNum\":\"AcoGLhkpAUGSWZrGJx5KozcB0c/XbgJ/BRbTiywrBXksuXsYOIjkB5aJ80jl4e9FpjiuxIz1CLq5rAkqkCd3HAU+eWBApzQ9QpdXLhxOX9DfrMLYs9yOw3OS8MABEQWMKeisQSUP57A8dHYnnn5TEMCvm6UujrchgrGoYUAuVd8=\",\"passwd\":\"aRyI5NOAw46YlKySrfR5JIMhA1elPJ3cNhgLVaKAievhRqaHTqwzbujVd49QmWNwcJwTPcGG/I7xS4cVj5957/FjAPR4g8XSQi30fGdangcVeGLQJ2KVltQK+l3kgNIiCk9Fd+lNZIAdaArlLk48UKxyu+BgfjzKsNARZD3GpKA=\",\"smsPasswd\":\"" + smsCode + "\"},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"962b5407ae093cad36206ace578a2504\"}";
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = CommonHttpMethod.doPostSSL(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: "+JSESSIONID);
			}
			if(c.getName().equals("UID")){
				UID = c.getValue();
				System.out.println("UID: "+UID);
			}
		}
		System.out.println(res.getResponse());
	}

	/**
	 * 查询
	 */
	public void getDetail(){
		String url = "https://clientaccess.10086.cn/biz-orange/BN/queryDetail/getDetail";
		HttpPost postMethod = new HttpPost(url);
		
		String data = "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"0\",\"reqBody\":{\"billMonth\":\"2016-07\",\"cellNum\":\"13701346824\",\"page\":1,\"tmemType\":\"02\",\"unit\":200},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"e517313049ef8e12b82bf3e30d574362\"}";	   
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = CommonHttpMethod.doPostSSL(postMethod);
		
		System.out.println(res.getResponse());
	}
}
