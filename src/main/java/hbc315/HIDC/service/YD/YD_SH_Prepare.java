package hbc315.HIDC.service.YD;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;

public class YD_SH_Prepare {
	
	public static void main(String[] args) {
		YD_SH_Prepare yd = new YD_SH_Prepare();

		yd.perpare();
		yd.getParams();
		yd.getJSESSIONID();
		yd.act10();
		yd.getRnum();
		yd.syso();
	}

	public void syso(){
		System.out.println("JSESSIONID: " + JSESSIONID);
		System.out.println("ANsession_fullName: " + ANsession_fullName);
		System.out.println("ANsession_value: " + ANsession_value);
		System.out.println("AN_nav1: " + AN_nav1);
		System.out.println("vpn_auto: " + vpn_auto);
	}
	
	private String JSESSIONID = "";
	private String ANsession_fullName = "";
	private String ANsession_value = "";
	private String AN_nav1 = "";
	private String vpn_auto = "";
	
	private String ANbookmark = "";
	private void perpare() {
		String url = "https://sh.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().startsWith("ANbookmark")){
				ANbookmark = c.getValue();
				System.out.println("ANbookmark: "+ ANbookmark);
			}
			if(c.getName().equals("ANsession")){
				ANsession_fullName = c.getName();
				ANsession_value = c.getValue();
				System.out.println(ANsession_fullName + ": "+ ANsession_value);
			}
		}
		
		System.out.println(res.getResponse());
		System.out.println();
	}
	
	/**
	 * 获取一些参数
	 */
	private void getParams() {
		String url = "https://sh.ac.10086.cn/prx/000/http/localhost/login";
		HttpGet getMethod = new HttpGet(url);

		String cookies = ANsession_fullName + "=" + ANsession_value + "; "
				+ "ANbookmark=" + ANbookmark + "; ";
		
		getMethod.setHeader("Cookie", cookies);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().startsWith("ANsession")){
				ANsession_fullName = c.getName();
				ANsession_value = c.getValue();
				System.out.println(ANsession_fullName + ": " + ANsession_value);
			}
			if(c.getName().equals("AN_nav1")){
				AN_nav1 = c.getValue();
				System.out.println("AN_nav1: "+ AN_nav1);
			}
			if(c.getName().equals("vpn_auto")){
				vpn_auto = c.getValue();
				System.out.println("vpn_auto: "+vpn_auto);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取JSESSIONID
	 */
	private void getJSESSIONID() {
		String url = "https://sh.ac.10086.cn/login";
		HttpGet getMethod = new HttpGet(url);

		String cookies = ANsession_fullName + "=" + ANsession_value + "; "
				+ "AN_nav1=" + AN_nav1 + "; " 
				+ "vpn_auto=" + vpn_auto ;
		
		getMethod.setHeader("Cookie", cookies);
		
		ResponseValue res = CommonHttpMethod.doGet(getMethod);
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("JSESSIONID")){
				JSESSIONID = c.getValue();
				System.out.println("JSESSIONID: "+ JSESSIONID);
			}
		}
		
		System.out.println(res.getResponse());
	}
	
	public void act10(){
		String url = "https://sh.ac.10086.cn/loginex?act=10";
		HttpGet getMethod = new HttpGet(url);
		
		String cookie = ANsession_fullName + "=" + ANsession_value + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "AN_nav1=" + AN_nav1 + "; "
				+ "vpn_auto=" + vpn_auto + "; ";
		
		getMethod.addHeader("Cookie", cookie);
		getMethod.addHeader("Referer", "https://sh.ac.10086.cn/login");

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
	}
	
	/**
	 * 获取验证码
	 */
	private void getRnum(){
		String url = "https://sh.ac.10086.cn/validationCode?rnd=";
		HttpGet getMethod = new HttpGet(url);

		String cookies = "AN_nav1=" + AN_nav1 + "; "
				+ "vpn_auto=" + vpn_auto + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ ANsession_fullName + "=" + ANsession_value;
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);
			
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(res.getResponse().getBytes("ISO-8859-1"));
			String picName = "d:\\RandomCode\\YD_SH.jpg";
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
