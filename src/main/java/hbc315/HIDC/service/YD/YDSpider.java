package hbc315.HIDC.service.YD;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;


/**
 * 移动全国app，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YDSpider {

	private static String cid = "+C5zXwvbuUoKb0v7AhOkXRX+qPlj+26JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb"; 
//	private static String cid = "uOwckxa/tK0uIjYmgHF9mo5D4R+pWBWDJ0QMPutDUkQJq64pewRTNW2i2TCTpDGoHX4ye1v9/eqz/QJqVhCTMERAhe38vOvbh77ChDh/eZd1NG0c8YzbUadnj4ix0tta";
	private static String clientVersion = "3.5.1";
	private static String xk = "3e7eead938ee438c19236e1b81f1519318534377a910a1e2d31bc263c0370c40e483949f";
	private static String ak = "F4AA34B89513F0D087CA0EF11A3277469DC74905";
	
	private static String mobile = "15210970705";
	private static String servicePassword = "408800";
	//871008
	private static String month = "2017-01";
	private static String numEachPage = "200";
	private static String page = "1";
	
	public static void main(String[] args) {
		YDSpider yd = new YDSpider();

		String encryptMobile = YD_RSA_Encrypt.getEntrypt("leadeon" + mobile + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		String encryptServicePassword = YD_RSA_Encrypt.getEntrypt("leadeon" + servicePassword + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		
		//再次获取短信
		String data = "{\"ak\":\"" + ak + "\",\"cid\":\"" + cid + "\",\"ctid\":\"" + cid + "\",\"cv\":\"" + clientVersion + "\",\"en\":\"0\",\"reqBody\":{\"cellNum\":\"" +  mobile + "\"},\"sn\":\"EVA-AL10\",\"sp\":\"1080x1812\",\"st\":\"1\",\"sv\":\"6.0\",\"t\":\"\",\"xc\":\"A0001\",\"xk\":\"" + xk + "\"}";
		yd.getSendSMS(data);
		Scanner sc = new Scanner(System.in);
		String smsCode = sc.next();
		
		//身份认证
		data = "{\"ak\":\"" + ak + "\",\"cid\":\"" + cid + "\",\"ctid\":\"" + cid + "\",\"cv\":\"" + clientVersion + "\",\"en\":\"0\",\"reqBody\":{\"businessCode\":\"01\",\"cellNum\":\"" + encryptMobile + "\",\"passwd\":\"" + encryptServicePassword + "\",\"smsPasswd\":\"" + smsCode + "\"},\"sn\":\"EVA-AL10\",\"sp\":\"1080x1812\",\"st\":\"1\",\"sv\":\"6.0\",\"t\":\"cca72cde35f0bcb5f1d1c3119b3eece0\",\"xc\":\"A0001\",\"xk\":\"" + xk + "\"}";
		yd.identify(data);
		
		//查详单
		data = "{\"ak\":\"" + ak + "\",\"cid\":\"" + cid + "\",\"ctid\":\"" + cid + "\",\"cv\":\"" + clientVersion + "\",\"en\":\"0\",\"reqBody\":{\"billMonth\":\"" + month + "\",\"cellNum\":\"" + mobile + "\",\"page\":" + page + ",\"tmemType\":\"02\",\"unit\":" + numEachPage + "},\"sn\":\"EVA-AL10\",\"sp\":\"1080x1812\",\"st\":\"1\",\"sv\":\"6.0\",\"t\":\"cca72cde35f0bcb5f1d1c3119b3eece0\",\"xc\":\"A0001\",\"xk\":\"" + xk + "\"}";
		yd.getCallList(data);

	}
	
	private static String JSESSIONID = "";
	private static String UID = "";
	
	/**
	 * 发送短信验证码
	 */
	public void getSendSMS(String data){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/uamrandcode/sendMsgLogin";
		HttpPost postMethod = new HttpPost(url);
		
//		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
//		postMethod.setHeader("Cookie", cookie);
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);//
		postMethod.setEntity(myEntity);
		
		ResponseValue res = doPostSSLUID(postMethod,data);
		
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
	 * 二次身份认证
	 */
	public void identify(String data){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/tempIdentCode/getTmpIdentCode";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = doPostSSLUID(postMethod,data);
		
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
	 * 获取详单
	 */
	public void getCallList(String data){
		String url = "https://clientaccess.10086.cn/biz-orange/BN/queryDetail/getDetail";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = doPostSSLUID(postMethod, data);
		
		System.out.println(res.getResponse());
	}

    
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPostSSLUID(HttpPost postRequest,String data) {

		ResponseValue response = new ResponseValue();
		
		postRequest.setHeader("Accept-Encoding", "gzip, deflate");
		postRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		String encrypt = postRequest.getURI() + "_" + data + "_Leadeon/SecurityOrganization";
		System.out.println(encrypt);
		postRequest.setHeader("xs", string2MD5(encrypt));
		CloseableHttpClient client = createSSLClientDefault();
		
		//daili
//	    HttpHost proxy = new HttpHost("221.237.155.64",9797,"http");  
//        RequestConfig config = RequestConfig.custom().setSocketTimeout(13000).setConnectTimeout(13000).setProxy(proxy).build();  
//        postRequest.setConfig(config); 
	      
        
		HttpClientContext context = HttpClientContext.create();
		try {
			HttpResponse httpResponse = client.execute(postRequest, context);
					
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == 200 || code == 302) {
				// get response cookies
				CookieStore cookieStore = context.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				
				Header header[] = httpResponse.getHeaders("Set-Cookie");
				for(int i=0; i<header.length; i++){
					if(header[i].getValue().contains("UID")){
						UID = header[0].getValue().split("UID=")[1];
						UID = UID.split(";")[0];
						System.out.println("UID: " + UID);
					}
				}
				
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
    
    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
}
