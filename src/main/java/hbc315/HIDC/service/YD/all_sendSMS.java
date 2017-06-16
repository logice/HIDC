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
import org.springframework.security.crypto.codec.Base64;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.YD_HE_PwdEncrypt;


/**
 * app，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class all_sendSMS {


//	private static String mobile = "13975806426";
//	private static String servicePassword = "989977";	
	
	private static String mobile = "XrPX7h2XVXL2km4B2+URjOcid/SPBAbzLlkVWXSm6fSLJ/kjeG/h6zaFNtR7emkamIUrpASxxx24/2xnpih6VUrdGQgKg95mPXLnORx3E4mnwxkKT8wyuYvdyI0htSDHvrhog8X+J2Qe31wPbRdU90jxnMLgzgJr2h/OU4eJrTM=";
	private static String servicePassword = "iyoHAFUtUSj/0Y58ZvlKvw0tgSsG1jo3AbbsziTMVzjCOI9YW6MEmxv8Xn2vh9iecwsJCRUhSWylPS0gXNwiGm2ArGt6VZPj3aenBwYmb2h8RE8NAmpHnSX5Ho94lhx+OnBpCyeDdC9+Lj/f/cJvDAGVCKECpwdkeWqIkjfkAKI=";	
	
	static String publicKey = "kyJf5xHIypHSsO7ayUd1nF0T1BZMO641sqqi+GdHddSGZ6U2NKrJcoflxwwaeS79TmMioPUiLwfqhRLIJGnltk3cDnrkveXfsel5HeaAFXs1SbkPe8Q7e9/EThvneg4YzYvB7X6SCr6sQdFGSzQZgkAnZs7TV14t7pPQtYfxCh0=";
	static String publicKey2 = "oxIwed0w6vVMjNwWd1dqvYvuJZOBFvZPIvDVtdcPD9b/QIldbPVUpj93B7alpupcexFOLWEeL2dXJpYdDlzyibcDMePdyvwJboiOaG4lLlLb4B0/OXD01/fESvGvAZMZJXmM7rHMiSObqIhd8+1ced701xvFeJWdysxwU5osIw0=";
	static byte[] b = {(byte)0xA3,(byte)0x12,(byte)0x30,(byte)0x79,(byte)0xDD,(byte)0x30,(byte)0xEA,(byte)0xF5,(byte)0x4C,(byte)0x8C,(byte)0xDC,(byte)0x16,(byte)0x77,(byte)0x57,(byte)0x6A,(byte)0xBD,(byte)0x8B,(byte)0xEE,(byte)0x25,(byte)0x93,(byte)0x81,(byte)0x16,(byte)0xF6,(byte)0x4F,(byte)0x22,(byte)0xF0,(byte)0xD5,(byte)0xB5,(byte)0xD7,(byte)0x0F,(byte)0x0F,(byte)0xD6,(byte)0xFF,(byte)0x40,(byte)0x89,(byte)0x5D,(byte)0x6C,(byte)0xF5,(byte)0x54,(byte)0xA6,(byte)0x3F,(byte)0x77,(byte)0x07,(byte)0xB6,(byte)0xA5,(byte)0xA6,(byte)0xEA,(byte)0x5C,(byte)0x7B,(byte)0x11,(byte)0x4E,(byte)0x2D,(byte)0x61,(byte)0x1E,(byte)0x2F,(byte)0x67,(byte)0x57,(byte)0x26,(byte)0x96,(byte)0x1D,(byte)0x0E,(byte)0x5C,(byte)0xF2,(byte)0x89,(byte)0xB7,(byte)0x03,(byte)0x31,(byte)0xE3,(byte)0xDD,(byte)0xCA,(byte)0xFC,(byte)0x09,(byte)0x6E,(byte)0x88,(byte)0x8E,(byte)0x68,(byte)0x6E,(byte)0x25,(byte)0x2E,(byte)0x52,(byte)0xDB,(byte)0xE0,(byte)0x1D,(byte)0x3F,(byte)0x39,(byte)0x70,(byte)0xF4,(byte)0xD7,(byte)0xF7,(byte)0xC4,(byte)0x4A,(byte)0xF1,(byte)0xAF,(byte)0x01,(byte)0x93,(byte)0x19,(byte)0x25,(byte)0x79,(byte)0x8C,(byte)0xEE,(byte)0xB1,(byte)0xCC,(byte)0x89,(byte)0x23,(byte)0x9B,(byte)0xA8,(byte)0x88,(byte)0x5D,(byte)0xF3,(byte)0xED,(byte)0x5C,(byte)0x79,(byte)0xDE,(byte)0xF4,(byte)0xD7,(byte)0x1B,(byte)0xC5,(byte)0x78,(byte)0x95,(byte)0x9D,(byte)0xCA,(byte)0xCC,(byte)0x70,(byte)0x53,(byte)0x9A,(byte)0x2C,(byte)0x23,(byte)0x0D};
	
	static byte[] temp = {(byte)0x8B,(byte)0x2A,(byte)0x07,(byte)0x00,(byte)0x55,(byte)0x2D,(byte)0x51,(byte)0x28,(byte)0xFF,(byte)0xD1,(byte)0x8E,(byte)0x7C,(byte)0x66,(byte)0xF9,(byte)0x4A,(byte)0xBF,(byte)0x0D,(byte)0x2D,(byte)0x81,(byte)0x2B,(byte)0x06,(byte)0xD6,(byte)0x3A,(byte)0x37,(byte)0x01,(byte)0xB6,(byte)0xEC,(byte)0xCE,(byte)0x24,(byte)0xCC,(byte)0x57,(byte)0x38,(byte)0xC2,(byte)0x38,(byte)0x8F,(byte)0x58,(byte)0x5B,(byte)0xA3,(byte)0x04,(byte)0x9B,(byte)0x1B,(byte)0xFC,(byte)0x5E,(byte)0x7D,(byte)0xAF,(byte)0x87,(byte)0xD8,(byte)0x9E,(byte)0x73,(byte)0x0B,(byte)0x09,(byte)0x09,(byte)0x15,(byte)0x21,(byte)0x49,(byte)0x6C,(byte)0xA5,(byte)0x3D,(byte)0x2D,(byte)0x20,(byte)0x5C,(byte)0xDC,(byte)0x22,(byte)0x1A,(byte)0x6D,(byte)0x80,(byte)0xAC,(byte)0x6B,(byte)0x7A,(byte)0x55,(byte)0x93,(byte)0xE3,(byte)0xDD,(byte)0xA7,(byte)0xA7,(byte)0x07,(byte)0x06,(byte)0x26,(byte)0x6F,(byte)0x68,(byte)0x7C,(byte)0x44,(byte)0x4F,(byte)0x0D,(byte)0x02,(byte)0x6A,(byte)0x47,(byte)0x9D,(byte)0x25,(byte)0xF9,(byte)0x1E,(byte)0x8F,(byte)0x78,(byte)0x96,(byte)0x1C,(byte)0x7E,(byte)0x3A,(byte)0x70,(byte)0x69,(byte)0x0B,(byte)0x27,(byte)0x83,(byte)0x74,(byte)0x2F,(byte)0x7E,(byte)0x2E,(byte)0x3F,(byte)0xDF,(byte)0xFD,(byte)0xC2,(byte)0x6F,(byte)0x0C,(byte)0x01,(byte)0x95,(byte)0x08,(byte)0xA1,(byte)0x02,(byte)0xA7,(byte)0x07,(byte)0x64,(byte)0x79,(byte)0x6A,(byte)0x88,(byte)0x92,(byte)0x37,(byte)0xE4,(byte)0x00,(byte)0xA2};
	public static void main(String[] args) {
		
		//System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(temp));
		all_sendSMS yd = new all_sendSMS();

		//String data = "{\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"3\",\"reqBody\":{\"ccPasswd\":\"TIrCi4V5VJWBv+C58TFxUXLEHKVao5By4WKP7ShqqBl3KNYqsUDbVbZy/bEehiyZBixv9jQ0RoGdZeq2FujD9RoB6SRB58SDrngUfr379ZgbVoEM/cfF0OiavbliYhFrMlZfQuFGbZ/4XpJWWhc1MRcfBQzUbGkawMIWc0IQZhs=\",\"cellNum\":\"a0etqEPFnMxxXPPPGN+G0NHVWdfl2h3Mey+RI8qcSLAw6syALIuOfZ10MHwDpWpO6iOmOOMcRPZG/ufVc7eIkaNWQ8fQGXZdkEJTVqEtnKhieI/rN7YCWOk1pgw20BziSPR1soZYRlGktK1EQPhyuL8G9uITq52ORbYJptBP+G4=\",\"sendSmsFlag\":\"1\"},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"\"}";
		String data = "{\"cid\":\"uOwckxa/tK0uIjYmgHF9mo5D4R+pWBWDJ0QMPutDUkQJq64pewRTNW2i2TCTpDGoHX4ye1v9/eqz/QJqVhCTMERAhe38vOvbh77ChDh/eZd1NG0c8YzbUadnj4ix0tta\",\"ctid\":\"uOwckxa/tK0uIjYmgHF9mo5D4R+pWBWDJ0QMPutDUkQJq64pewRTNW2i2TCTpDGoHX4ye1v9/eqz/QJqVhCTMERAhe38vOvbh77ChDh/eZd1NG0c8YzbUadnj4ix0tta\",\"cv\":\"3.5.1\",\"en\":\"0\",\"reqBody\":{\"ccPasswd\":\"" +servicePassword + "\",\"cellNum\":\"" +  mobile + "\",\"sendSmsFlag\":\"1\"},\"sn\":\"EVA-AL10\",\"sp\":\"1080x1812\",\"st\":\"1\",\"sv\":\"6.0\",\"t\":\"\"}";
		yd.login(data);
		data = "{\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"0\",\"reqBody\":{\"cellNum\":\"13701346824\"},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"962b5407ae093cad36206ace578a2504\"}";
		yd.test(data);
		yd.out();

	}
	
	private void out(){
		System.out.println("JSESSIONID: "+JSESSIONID);
		System.out.println("UID: "+UID);
	}
	
	private static String JSESSIONID = "";
	private static String UID = "";
	
	/**
	 * 登录
	 */
	public void login(String data){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/uamlogin/login";
		HttpPost postMethod = new HttpPost(url);
//		/biz-orange/LN/uamrandcode/sendMsgLogin
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);//
		postMethod.setEntity(myEntity);
		
		ResponseValue res = doPostSSLUID(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: "+JSESSIONID);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	
	/**
	 * 登录
	 */
	public void test(String data){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/uamrandcode/sendMsgLogin";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = CommonHttpMethod.doPostSSL(postMethod);
		
		System.out.println(res.getResponse());
	}

    
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPostSSLUID(HttpPost postRequest) {

		ResponseValue response = new ResponseValue();
		
		postRequest.setHeader("Accept-Encoding", "gzip, deflate");
		postRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");

		CloseableHttpClient client = createSSLClientDefault();
		
		HttpClientContext context = HttpClientContext.create();
		try {
			HttpResponse httpResponse = client.execute(postRequest, context);
					
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == 200 || code == 302) {
				// get response cookies
				CookieStore cookieStore = context.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				
//				Header header[] = httpResponse.getHeaders("Set-Cookie");
//				UID = header[0].getValue().split("UID=")[1];
//				UID = UID.split(";")[0];
//				System.out.println("UID: " + UID);
				
				if (cookies != null) {
					response.setCookies(cookies);
				}

				Header[] hs = httpResponse.getAllHeaders();
				for (Header h : hs) {
					if (h.getName().equals("Location")) {
						System.out.println(h.getValue());
						response.setLocation(h.getValue());
					}
				}

				HttpEntity httpEntity = httpResponse.getEntity();
				String result = EntityUtils.toString(httpEntity);
				//System.out.println(result);
				response.setResponse(result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	
    public static CloseableHttpClient createSSLClientDefault(){
    	try {
             SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                 //信任所有
                 public boolean isTrusted(X509Certificate[] chain,
                                 String authType) throws CertificateException {
                     return true;
                 }
             }).build();
             SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
             return HttpClients.custom().setSSLSocketFactory(sslsf).build();
         } catch (KeyManagementException e) {
             e.printStackTrace();
         } catch (NoSuchAlgorithmException e) {
        	 e.printStackTrace();
         } catch (KeyStoreException e) {
             e.printStackTrace();
         }
         return  HttpClients.createDefault();
    }
}
