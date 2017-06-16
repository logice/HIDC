package hbc315.HIDC.service.DX;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;
import hbc315.HIDC.util.DX_BJ_PwdEncrypt;

/**
 * 电信——北京——需要登录密码；查询时，手机验证环节有漏洞，可跳过，直接查询
 * @author zhaochunyu
 *
 */
public class DX_BJ_APIGetMessage {

	public void getCallDetail(String mobile, String password, String year, String month) {
		DX_BJ_APIGetMessage dx = new DX_BJ_APIGetMessage();
		dx.doLogin(mobile,password);
		dx.doECS();
		dx.doECS2();
		dx.sendShortMessage(mobile);
		dx.checkMobileCode(mobile);
		dx.billDetailQuery(mobile,1,year,month);
	}


	private String ECSUrl = "";
	private String ECSLoginReq = "";
	private String ECSLoginToken = "";

	/**
	 * 登录
	 * 
	 */
	public void doLogin(String mobile, String password) {
		String url = "http://login.189.cn/login";
		HttpPost postMethod = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Account", mobile));
		params.add(new BasicNameValuePair("UType", "201"));
		params.add(new BasicNameValuePair("ProvinceID", ""));
		params.add(new BasicNameValuePair("AreaCode", ""));
		params.add(new BasicNameValuePair("RandomFlag", "0"));
		params.add(new BasicNameValuePair("Password", DX_BJ_PwdEncrypt.encrypt(password)));
		params.add(new BasicNameValuePair("Captcha", ""));

		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ResponseValue res = CommonHttpMethod.doPost(postMethod);

		ECSUrl = res.getLocation();

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("ECSLoginReq")) {
				ECSLoginReq = c.getValue();
			}

			if (c.getName().equals("ECSLoginToken")) {
				ECSLoginToken = c.getValue();
			}
		}

	}

	private String JSESSIONID_JT = "";
	private String _ybtj_189_cn = "";
	private String USERId = "";

	/**
	 * 登录成功后取凭证信息
	 * 
	 */
	private void doECS() {
		if (!ECSUrl.equals("")) {
			HttpGet getMethod = new HttpGet(ECSUrl);

			ResponseValue res = CommonHttpMethod.doGet(getMethod);

			for (Cookie c : res.getCookies()) {
				if (c.getName().equals("JSESSIONID-JT")) {
					JSESSIONID_JT = c.getValue();
				}
				if (c.getName().equals("userId")) {
					USERId = c.getValue();
				}
				if (c.getName().equals(".ybtj.189.cn")) {
					_ybtj_189_cn = c.getValue();
				}
			}

		}
	}

	private String JSESSIONID_bj = "";
	
	/**
	 * 取参数JSESSIONID_bj
	 * 
	 */
	private void doECS2() {
		String fastCode = "01390638";

		String url = "http://www.189.cn/login/sso/ecs.do?method=linkTo&platNo=10001&"
				+ "toStUrl=http://bj.189.cn/iframe/feequery/detailBillIndex.action?fastcode=" + fastCode
				+ "&cityCode=bj";
		String cookies = "s_pers=%20s_fid%3D2878A408A237DEC9-349FF71D3E3CF607%7C1525327484219%3B; lvid=1b7e5553d1bc2187561621ca9c856ebd; nvid=1; "
				+ "userId="+USERId+"; .ybtj.189.cn="+_ybtj_189_cn+"; cityCode=bj; SHOPID_COOKIEID=10001; "
				+ "trkId=0F704B45-D598-429E-9F01-9B89F288888E; JSESSIONID-JT="+JSESSIONID_JT+"; loginStatus=non-logined; "
				+ "s_sess=%20s_cc%3Dtrue%3B%20s_sq%3D%3B; trkHmClickCoords=79%2C438%2C2357; "
				+ "s_fid=2500DF5D38D43392-297A07F1139B9189; s_cc=true; s_sq=eshipeship-189-all%3D%252"
				+ "6pid%253D%25252Fdqmh%25252Fmy189%25252FinitMy189home.do%2526pidt%253D1%2526oid%253D"
				+ "javascript%25253AgotoIfremBody%252528%252527%25252Fdqmh%25252FssoLink.do%25253Fmeth"
				+ "od%25253DlinkTo%252526platNo%25253D10001%252526toStUrl%25253Dhttp%25253A%25252F%2525"
				+ "2Fbj.189.cn%25252Fifram%2526ot%253DA; trkHmLinks=0; trkHmCoords=0; trkHmCity=0; "
				+ "trkHmPageName=%2Fbj%2F; "
				+ "ECSLoginReq="+ECSLoginReq+"; "
				+ "ECSLoginToken="+ECSLoginToken+";";

		HttpGet getMethod = new HttpGet(url);
		getMethod.setHeader("Cookie", cookies);

		ResponseValue res = CommonHttpMethod.doGet(getMethod);

		for (Cookie c : res.getCookies()) {
			if (c.getName().equals("JSESSIONID_bj")) {
				JSESSIONID_bj = c.getValue();
			}
		}

	}


	private String smsCode = "";
	private void sendShortMessage(String mobile) {
		String url = "http://bj.189.cn/iframe/feequery/smsRandCodeSend.action";
		HttpPost postMethod = new HttpPost(url);

		postMethod.setHeader("Cookie",
				"dqmhIpCityInfos=%E5%8C%97%E4%BA%AC%E5%B8%82+%E5%85%89%E7%8E%AF%E6%96%B0%E7%BD%91; "
						+ "lvid=184d2fd1bf0327fc8e5d67e183c567bf; nvid=1; WT_SS=1461752291203new; "
						+ "trkHmPageName=%2Fbj%2F; trkHmCoords=0; trkHmCity=0; trkHmLinks=0; "
						+ "JSESSIONID_bj="+JSESSIONID_bj+"; "
						+ "s_sess=%20s_cc%3Dtrue%3B%20s_sq%3D%3B; s_pers=%20s_fid%3D6DA2763B04E41683-32EF347DFB1846F9%7C1525189457236%3B; "
						+ "aactgsh111220=" + mobile + "; userId=201%7C20160000000013420325; isLogin=logined; .ybtj.189.cn=F41AA16701DD342B8E124FF16C2DC2B2; "
						+ "cityCode=bj; SHOPID_COOKIEID=10001; loginStatus=logined; s_cc=true; s_fid=127DEA000042CD8F-19B41B527AD46C18; "
						+ "trkId=5F4F1134-9700-469E-B109-E1613827C4F3; "
						+ "s_sq=eshipeship-189-all%3D%2526pid%253D%25252Fdqmh%25252Fmy189%25252FinitMy189home.do%2526pidt%253D1%2526oid%253Djavascript%25253AgotoIfremBody%252528%252527%25252Fdqmh%25252FssoLink.do%25253Fmethod%25253DlinkTo%252526platNo%25253D10001%252526toStUrl%25253Dhttp%25253A%25252F%25252Fbj.189.cn%25252Fifram%2526ot%253DA; "
						+ "trkHmClickCoords=87%2C444%2C2358; Hm_lvt_5b3beae528c7fc9af9c016650f4581e0=1462105388,1462105690,1462106173,1462117477; "
						+ "Hm_lpvt_5b3beae528c7fc9af9c016650f4581e0=1462117477; WT_FPC=id=156b4e981bc0cac238c1461752291203");

		postMethod.setHeader("Referer",
				"http://bj.189.cn/iframe/feequery/detailBillIndex.action?fastcode=01390638&cityCode=bj");
		postMethod.setHeader("Origin", "http://bj.189.cn");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("accNum", mobile));

		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		JSONObject resJson = JSON.parseObject(res.getResponse());
		smsCode = resJson.get("SRandomCode").toString();
	}

	private void checkMobileCode(String mobile) {
		String url = "http://bj.189.cn/iframe/feequery/detailValidCode.action";
		HttpPost postMethod = new HttpPost(url);

		postMethod.setHeader("Cookie","lvid=cb2443c253d786993b99fb62ddd4e3b3; nvid=1; trkHmPageName=%2Fbj%2F;"
				+ " trkHmCoords=0; trkHmCity=0; trkHmLinks=0; WT_SS=1462329930986new; "
				+ "s_pers=%20s_fid%3D4DFFEFD803788145-0B09F0D25ABEBD53%7C1525411269102%3B; "
				+ "s_sess=%20s_cc%3Dtrue%3B%20s_sq%3D%3B; aactgsh111220=" + mobile + "; "
				+ "userId="+USERId+"; isLogin=logined; .ybtj.189.cn="+_ybtj_189_cn+"; "
				+ "cityCode=bj; SHOPID_COOKIEID=10001; loginStatus=logined; s_cc=true; "
				+ "s_fid=79584C3EFC41944F-378FA639F5B6BD91; trkId=B4F81158-15F5-4096-9999-1966E05B979B; "
				+ "s_sq=eshipeship-189-all%3D%2526pid%253D%25252Fdqmh%25252Fmy189%25252FinitMy189home.do%2526pidt%253D1%2526oid%253Djavascript%25253AgotoIfremBody%252528%252527%25252Fdqmh%25252FssoLink.do%25253Fmethod%25253DlinkTo%252526platNo%25253D10001%252526toStUrl%25253Dhttp%25253A%25252F%25252Fbj.189.cn%25252Fifram%2526ot%253DA; "
				+ "JSESSIONID_bj="+JSESSIONID_bj+"; "
				+ "trkHmClickCoords=78%2C437%2C2641; Hm_lvt_5b3beae528c7fc9af9c016650f4581e0=1462329927,1462339289; "
				+ "Hm_lpvt_5b3beae528c7fc9af9c016650f4581e0=1462339289; WT_FPC=id=15231283c0fd52912cc1462329930986");

		postMethod.setHeader("Referer",
				"http://bj.189.cn/iframe/feequery/detailBillIndex.action?fastcode=01390638&cityCode=bj");
		postMethod.setHeader("Origin", "http://bj.189.cn");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("requestFlag", "asynchronism"));
		params.add(new BasicNameValuePair("accNum", mobile));
		params.add(new BasicNameValuePair("sRandomCode", smsCode));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		JSONObject resJson = JSON.parseObject(res.getResponse());
		smsCode = resJson.get("SRandomCode").toString();
	}
	
	/**
	 * 查询语音通话记录
	 * @param page  从第几页开始查询
	 * @param year	查询年份，格式xxxx
	 * @param month	查询月份，格式xx
	 */
	private void billDetailQuery(String mobile, int page, String year, String month) {
		String url = "http://bj.189.cn/iframe/feequery/billDetailQuery.action";
		HttpPost postMethod = new HttpPost(url);
		
		postMethod.setHeader("Cookie","lvid=cb2443c253d786993b99fb62ddd4e3b3; nvid=1; trkHmPageName=%2Fbj%2F; "
				+ "trkHmCoords=0; trkHmCity=0; trkHmLinks=0; WT_SS=1462329930986new; "
				+ "s_pers=%20s_fid%3D4DFFEFD803788145-0B09F0D25ABEBD53%7C1525411269102%3B; "
				+ "s_sess=%20s_cc%3Dtrue%3B%20s_sq%3D%3B; aactgsh111220=" + mobile + "; userId="+USERId+"; "
				+ "isLogin=logined; .ybtj.189.cn="+_ybtj_189_cn+"; cityCode=bj; SHOPID_COOKIEID=10001; "
				+ "loginStatus=logined; s_cc=true; s_fid=79584C3EFC41944F-378FA639F5B6BD91; "
				+ "trkId=B4F81158-15F5-4096-9999-1966E05B979B; "
				+ "s_sq=eshipeship-189-all%3D%2526pid%253D%25252Fdqmh%25252Fmy189%25252FinitMy189home.do%2526pidt%253D1%2526oid%253Djavascript%25253AgotoIfremBody%252528%252527%25252Fdqmh%25252FssoLink.do%25253Fmethod%25253DlinkTo%252526platNo%25253D10001%252526toStUrl%25253Dhttp%25253A%25252F%25252Fbj.189.cn%25252Fifram%2526ot%253DA; "
				+ "JSESSIONID_bj="+JSESSIONID_bj+"; "
				+ "trkHmClickCoords=78%2C437%2C2641; Hm_lvt_5b3beae528c7fc9af9c016650f4581e0=1462329927,1462339289; "
				+ "Hm_lpvt_5b3beae528c7fc9af9c016650f4581e0=1462339289; WT_FPC=id=15231283c0fd52912cc1462329930986");

		postMethod.setHeader("Referer",
				"http://bj.189.cn/iframe/feequery/detailBillIndex.action?fastcode=01390638&cityCode=bj");
		postMethod.setHeader("Origin", "http://bj.189.cn");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("requestFlag", "synchronization"));
		params.add(new BasicNameValuePair("billDetailType", "1"));
		params.add(new BasicNameValuePair("qryMonth", year+"年"+month+"月"));
		params.add(new BasicNameValuePair("startTime","1"));
		params.add(new BasicNameValuePair("accNum", mobile));
		params.add(new BasicNameValuePair("endTime", getMaxDays(year,month)));
		params.add(new BasicNameValuePair("billPage", String.valueOf(page)));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//处理请求
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		//预处理
		res.setResponse("<html>"+res.getResponse().trim()+"</html>");
		InputStream inputResult = new ByteArrayInputStream(res.getResponse().getBytes());
		//提取信息
		DX_XPath xpath = new DX_XPath();
		//总页码数
		int totlePage  = xpath.readResult(inputResult);
		
		//递归读取所有页码信息
		if(page < totlePage){
			billDetailQuery(mobile,++page,year,month);
		}
	}
	
	/**
	 * 获取当月最大天数
	 * @param year
	 * @param month
	 * @return
	 */
	private String getMaxDays(String year, String month){
		Calendar cal = Calendar.getInstance();   
		cal.set(Calendar.YEAR,Integer.valueOf(year));   
		cal.set(Calendar.MONTH,Integer.valueOf(month));
		return String.valueOf(cal.getActualMaximum(Calendar.DATE));
	}


}
