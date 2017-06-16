package hbc315.HIDC.service.YD;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;


/**
 * 移动全国app，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_GetCallList {
	
	private String JSESSIONID = "efe35f71-ea21-4f6d-9717-5bfe398b5407";
	private String UID = "9d99ad32406d4dfea84b91eb21ce79c0";
	private String month = "2016-08";
	private String mobile = "13701346824";
	private static String servicePassword = "920408";

	//numEachPage最大是200
	private int numEachPage = 200;
	//当前page页码
	private int pageNO = 1;
	
	private static String smsCode = "672237";	
	
	public String getYDCallList(String mobile, String month, String smsCode,String UID,String JSESSIONID){
		this.mobile = mobile;
		this.month = month;
		this.JSESSIONID = JSESSIONID;
		this.UID = UID;
		smsCheck(smsCode);
		String detail = getDetail();
		
		return detail;
	}
	
	
	public static void main(String[] args) {
		YD_GetCallList yd = new YD_GetCallList();
		yd.smsCheck(smsCode);
		yd.getDetail();
	}
	
	
	/**
	 * 手机验证码
	 */
	public void smsCheck(String smsCode){
		String url = "https://clientaccess.10086.cn/biz-orange/LN/tempIdentCode/getTmpIdentCode";
		HttpPost postMethod = new HttpPost(url);
		
		String EncryptMobile = YD_RSA_Encrypt.getEntrypt("leadeon" + mobile + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		String EncryptServicePassword =  YD_RSA_Encrypt.getEntrypt("leadeon" + servicePassword + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		String data = "{\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"0\",\"reqBody\":{\"businessCode\":\"01\",\"cellNum\":\""+EncryptMobile+"\",\"passwd\":\""+EncryptServicePassword+"\",\"smsPasswd\":\"" + smsCode + "\"},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"962b5407ae093cad36206ace578a2504\"}";
		
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
	public String getDetail(){
		String url = "https://clientaccess.10086.cn/biz-orange/BN/queryDetail/getDetail";
		HttpPost postMethod = new HttpPost(url);
		
		String data = "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"ctid\":\"+C5zXwvbuUoKb0v7AhOkXRX+qPlj+28JYaoJaxCMK8vBCLxJ9aeq2IlqFgZDcj8NoSzAohRNVoSYW0ltlxT0S3DqkzEcNiLsfb4V1OaqDHx/09zZz6UDAnKGytrluheb\",\"cv\":\"3.1.0\",\"en\":\"0\",\"reqBody\":{\"billMonth\":\"" + month + "\",\"cellNum\":\"" + mobile + "\",\"page\":" + pageNO + ",\"tmemType\":\"02\",\"unit\":" + numEachPage + "},\"sn\":\"H30-T10\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"e517313049ef8e12b82bf3e30d574362\"}";	   
		String cookie = "JSESSIONID=" + JSESSIONID + "; UID=" + UID + "; Comment=SessionServer-unity; Path=/; Secure";
		postMethod.setHeader("Cookie", cookie);
		
		
		StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
		postMethod.setEntity(myEntity);
		
		ResponseValue res = CommonHttpMethod.doPostSSL(postMethod);
		
		System.out.println(res.getResponse());
		return res.getResponse();
	}
}
