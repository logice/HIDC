package hbc315.HIDC.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import hbc315.HIDC.model.ResponseValue;

public class CommonHttpMethod {
	/**
	 * 通用get方法
	 * @param httpGet
	 * @return
	 */
	public static ResponseValue doGet(HttpGet httpGet) {
		//防止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false); 
		httpGet.setParams(params);
		
		String result = "";
		HttpResponse httpResponse;
		ResponseValue resValue = new ResponseValue();

		CloseableHttpClient client = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();
		try {

//			HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
//			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//			httpGet.setConfig(config);

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
				result = EntityUtils.toString(httpEntity);
				//System.out.println(result);
				resValue.setResponse(result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
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
	
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPost(HttpPost postRequest) {

		ResponseValue response = new ResponseValue();

		postRequest.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
	
	/**
	 * 通用get方法(没有任何头参数)
	 * @param httpGet
	 * @return
	 */
	public static ResponseValue doGetWithNoParams(HttpGet httpGet) {
		//防止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false); 
		httpGet.setParams(params);
		
		String result = "";
		HttpResponse httpResponse;
		ResponseValue resValue = new ResponseValue();

		CloseableHttpClient client = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();
		try {

//			HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
//			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//			httpGet.setConfig(config);

//			httpGet.setHeader("User-Agent",
//					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
//			httpGet.setHeader("Content-Type", "text/html; charset=utf-8");
//			httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
//			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");

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
				result = EntityUtils.toString(httpEntity);
				//System.out.println(result);
				resValue.setResponse(result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
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
	
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPostSSL(HttpPost postRequest) {

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
    
	/**
	 * 通用post方法
	 * @param postRequest
	 * @return
	 */
	public static ResponseValue doPostSSLUID(HttpPost postRequest) {

		ResponseValue response = new ResponseValue();
		String UID = "";
		
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
				
				Header header[] = httpResponse.getHeaders("Set-Cookie");
				UID = header[0].getValue().split("UID=")[1];
				UID = UID.split(";")[0];
				System.out.println("UID: " + UID);
				
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
