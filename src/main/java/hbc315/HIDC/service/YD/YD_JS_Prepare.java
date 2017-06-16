package hbc315.HIDC.service.YD;

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
public class YD_JS_Prepare {
	
	public static void main(String[] args) {
		YD_JS_Prepare yd = new YD_JS_Prepare();
			
		yd.addCookie();
		yd.getRnum();
		yd.show();
	}

	/**
	 * 显示
	 */
	private void show(){
		System.out.println("wt_dl123: " + wt_dl123);
		System.out.println("l_key: " + l_key);
	}
	
	private String wt_dl123 = "";
	
	/**
	 * 获取 wt_dl123
	 */
	public void addCookie(){
		String url = "https://js.ac.10086.cn/jsauth/dzqd/addCookie";
		HttpPost postMethod = new HttpPost(url);
		
		postMethod.addHeader("Origin","https://js.ac.10086.cn");
		postMethod.addHeader("Referer","https://js.ac.10086.cn/jsauth/dzqd/mh/index.html?v=1");
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("wt_dl123")){
				wt_dl123 = c.getValue();
				System.out.println("wt_dl123: "+ wt_dl123);
			}
		}
		
		System.out.println(res.getResponse());
	}

	private String l_key = "";
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		l_key = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
		String url = "https://js.ac.10086.cn/jsauth/dzqd/zcyzm?t=new&"
				+ "ik=l_image_code&ss=" + RandomNumUtil.getRandomNumber_16() + "&"
				+ "l_key=" + l_key;
		HttpGet getMethod = new HttpGet(url);

		String cookies = "CmProvid=js; wt_dl123=" + wt_dl123;
		getMethod.setHeader("Cookie", cookies);
		getMethod.setHeader("Referer", "https://js.ac.10086.cn/jsauth/dzqd/mh/index.html?v=1");
		
		doGet(getMethod);
		
	}
	
	/**
	 * get方法(图片特殊处理)
	 * @param httpGet
	 * @return
	 */
	public ResponseValue doGet(HttpGet httpGet) {
		//防止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false); 
		httpGet.setParams(params);
		
		InputStream result;
		HttpResponse httpResponse;
		ResponseValue resValue = new ResponseValue();

		CloseableHttpClient client = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();
		try {
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
			httpGet.setHeader("Content-Type", "text/html; charset=utf-8");
			httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");

			httpResponse = client.execute(httpGet, context);
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == 200 || code == 302) {
				// get response cookies
				CookieStore cookieStore = context.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies != null) {
					resValue.setCookies(cookies);
				}

				// get 302 location
				Header[] hs = httpResponse.getAllHeaders();
				for (Header h : hs) {
					if (h.getName().equals("Location")) {
						System.out.println(h.getValue());
						resValue.setLocation(h.getValue());
					}
				}

				// get response body
				HttpEntity httpEntity = httpResponse.getEntity();
				result = httpEntity.getContent();
				
				String picName = "d:\\RandomCode\\YD_JS.jpg";
				FileOutputStream fos = new FileOutputStream(picName);
				
				byte[] b = new byte[1024];
				while((result.read(b)) != -1){
					fos.write(b);
				}
				fos.close();
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
		return resValue;
	}
}
