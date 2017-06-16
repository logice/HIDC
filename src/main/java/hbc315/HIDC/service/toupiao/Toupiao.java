package hbc315.HIDC.service.toupiao;

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


/**
 * 上海移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class Toupiao {

	
	private String wdcid = "33c54315c1ef2214";
	private String ASP_NET_SessionId = "pq3h5qn1ow4n1krqqxnc3eer";
	
	public static void main(String[] args) {
		Toupiao yd = new Toupiao();
		
		yd.hualongshiting();
		//yd.chongqingribao();
	}	

	public void chongqingribao(){
		String url = "http://cqjyywtp.cqnews.net/fp2016jy/page.aspx?id=553";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "wdcid=" + wdcid + "; ASP.NET_SessionId" + ASP_NET_SessionId + "; wdlast" + (System.currentTimeMillis()/1000);
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin","http://cqjyywtp.cqnews.net");
		postMethod.addHeader("Referer", "http://cqjyywtp.cqnews.net/fp2016jy/page.aspx?id=553");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__EVENTTARGET", "LinkButton1"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", "%2FwEPDwULLTIxMDIzMzY0MTdkZJESBz%2FNUXmFjDogSDC7CcyB%2FdwM"));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "A0D022DD"));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", "%2FwEWBAL24839CQLklo2qBgKsqNMJAsz0%2B6YPXKG6h8MyuptwXK%2B7GJvx2DSK9FY%3D"));
		params.add(new BasicNameValuePair("currentid", "553"));
		params.add(new BasicNameValuePair("sortid", "1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = doPost(postMethod);		
		
		for(Cookie c : res.getCookies()){
			System.out.println("Cookie: " + c.getName() + "=" + c.getValue());
		}
		
		System.out.println(res.getResponse());
	}
	
	
	public void hualongshiting(){
		String url = "http://cqjyywtp.cqnews.net/fp2016/page.aspx?id=553";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "wdcid=" + wdcid + "; ASP.NET_SessionId" + ASP_NET_SessionId + "; wdlast" + (System.currentTimeMillis()/1000);
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin","http://cqjyywtp.cqnews.net");
		postMethod.addHeader("Referer", "http://cqjyywtp.cqnews.net/fp2016/page.aspx?id=553");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__EVENTTARGET", "LinkButton1"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwULLTIxMDIzMzY0MTdkZNmkzr+SVaEWImgM98rzCaNqYIpI"));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "A0D022DD"));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWBALEjoiBDgLklo2qBgKsqNMJAsz0+6YPSHLUMZpuiqEevVVz5NvdsMheFSU="));
		params.add(new BasicNameValuePair("currentid", "553"));
		params.add(new BasicNameValuePair("sortid", "1"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = doPost(postMethod);		
		
		for(Cookie c : res.getCookies()){
			System.out.println("Cookie: " + c.getName() + "=" + c.getValue());
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPost(HttpPost postRequest) {

		ResponseValue response = new ResponseValue();

		postRequest.setHeader("User-Agent",
				"Mozilla/5.0 (Linux; Android 4.4.2; H30-T10 Build/HuaweiH30-T10) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile MQQBrowser/6.2 TBS/036555 Safari/537.36 MicroMessenger/6.3.22.821 NetType/WIFI Language/zh_CN");
		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("Accept-Encoding", "gzip, deflate");
		postRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");

//		HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
//		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//		postRequest.setConfig(config);

		CloseableHttpClient client = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();
		try {
			HttpResponse httpResponse = client.execute(postRequest, context);
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == 200 || code == 302) {
				// get response cookies
				CookieStore cookieStore = context.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
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
				System.out.println(result);
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
}
