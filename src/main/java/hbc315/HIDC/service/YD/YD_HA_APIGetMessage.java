package hbc315.HIDC.service.YD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import hbc315.HIDC.model.ResponseValue;
import hbc315.HIDC.util.CommonHttpMethod;


/**
 * 河南移动，通话详单，需要提供服务密码和短信验证码
 * @author zcy
 *
 */
public class YD_HA_APIGetMessage {


	private static String mobile = "13670046183";
	private static String month = "201605";
	
	private static String SMSCode = "595583";	//验证码
	private String CmWebtokenid = "18268144220,zj";
	private String cmtokenid = "bde3f7db951f45299838b55fe3c8092f@zj.ac.10086.cn";
	private String _a_m_b_b = "0eaa221ddc0f4da7af97e3964f13e52f";
	private String _st = "6M9An8DFwUPQNWOOhyL9oTrbGuYylaB2";
	private String JUCSSESSIONID = "td5w-D_mZPaJXExQAuMkamWjkKiCpd2Qf_iZd6hqlMgoXa3OX3II!-188784637";
	private String _n_r_n_r = "1";
	private String JSESSIONID = "6M9An8DFwUPQNWOOhyL9oTrbGuYylaB2";
	
	public static void main(String[] args) {
		YD_HA_APIGetMessage yd = new YD_HA_APIGetMessage();

		yd.ValidateSMSCode();
		yd.getQueryMonth();
		yd.getDetailCallList();
	}	
	
	private String _sst = "";
	/**
	 * 第二次验证
	 */
	private void ValidateSMSCode(){
		String url = "https://gd.ac.10086.cn/ucs/second/authen.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "JUCSSESSIONID=" + JUCSSESSIONID + "; "
				+ "cmtokenid=" + cmtokenid + "; "
				+ "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "CmLocation=200|200; CmProvid=gd; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
//				+ "_gscu_1502255179=" + _gscu_1502255179 + "; "
//				+ "_gscs_1502255179=" + _gscs_1502255179 + "; "
//				+ "_gscbrs_1502255179=" + _gscbrs_1502255179 + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Origin", "https://gd.ac.10086.cn");
		postMethod.addHeader("Referer", "https://gd.ac.10086.cn/ucs/second/loading.jsps?reqType=0&channel=0&cid=10003&backURL=http://gd.10086.cn/my/REALTIME_LIST_SEARCH.shtml&type=2");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dpwd", mobile));
		params.add(new BasicNameValuePair("type", "2"));
		params.add(new BasicNameValuePair("cid", "10003"));
		params.add(new BasicNameValuePair("channel", "0"));
		params.add(new BasicNameValuePair("reqType", "0"));
		params.add(new BasicNameValuePair("backURL", "http%3A%2F%2Fgd.10086.cn%2Fmy%2FREALTIME_LIST_SEARCH.shtml"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);	
		
		for(Cookie c : res.getCookies()){
			if(c.getName().equals("_sst")){
				_sst = c.getValue();
				System.out.println("_sst: " + _sst);
			}
			if(c.getName().equals("_a_m_b_b")){
				_a_m_b_b = c.getValue();
				System.out.println("_a_m_b_b: " + _a_m_b_b);
			}
		}

		System.out.println(res.getResponse());
	}
	
	private String uniqueTag = "";
	/**
	 * 获取查询时间
	 * 
	 */
	public void getQueryMonth(){
		String url = "http://gd.10086.cn/commodity/servicio/nostandardserv/realtimeListSearch/query.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
//				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
//				+ "_gscu_1502255179=" + _gscu_1502255179 + "; "
//				+ "_gscs_1502255179=" + _gscs_1502255179 + "; "
//				+ "_gscbrs_1502255179=" + _gscbrs_1502255179 + "; "
//				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
//				+ "_a_h_b_c=" + _a_h_b_c + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "http://service.zj.10086.cnhttp://gd.10086.cn/my/REALTIME_LIST_SEARCH_GOTONE.shtml?uniqueTag=" + uniqueTag);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("month", month));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		try {
			uniqueTag = res.getResponse().split("value\":\"")[1];
			uniqueTag = uniqueTag.split("\"")[0];
			System.out.println("uniqueTag: " + uniqueTag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(res.getResponse());
	}
	
	/**
	 * 获取通话详情
	 * 
	 */
	public void getDetailCallList(){
		String url = "http://gd.10086.cn/commodity/servicio/nostandardserv/realtimeListSearch/ajaxRealQuery.jsps";
		HttpPost postMethod = new HttpPost(url);
		
		String cookie = "_st=" + _st + "; "
				+ "CmWebtokenid=" + CmWebtokenid + "; "
				+ "_n_r_n_r=" + _n_r_n_r + "; "
				+ "CmLocation=200|200; CmProvid=gd; CmLocationB=200%7C200; "
//				+ "ECOPPJSESSIONID=" + ECOPPJSESSIONID + "; "
				+ "JSESSIONID=" + JSESSIONID + "; "
				+ "_sst=" + _sst + "; "
				+ "_a_m_b_b=" + _a_m_b_b + "; ";
//				+ "_gscu_1502255179=" + _gscu_1502255179 + "; "
//				+ "_gscs_1502255179=" + _gscs_1502255179 + "; "
//				+ "_gscbrs_1502255179=" + _gscbrs_1502255179 + "; "
//				+ "_t_y_t_b_ip=" + _t_y_t_b_ip + "; "
//				+ "_a_h_b_c=" + _a_h_b_c + "; ";
		postMethod.addHeader("Cookie", cookie);
		postMethod.addHeader("Referer", "http://service.zj.10086.cn/yw/detail/queryHisDetailBill.do?menuId=13009&bid=BD399F39E69148CFE044001635842131");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("startTimeReal", ""));
		params.add(new BasicNameValuePair("endTimeReal", ""));
		params.add(new BasicNameValuePair("uniqueTag", "20160623101618645"));
		params.add(new BasicNameValuePair("month", ""));
		params.add(new BasicNameValuePair("monthListType", "0"));
		params.add(new BasicNameValuePair("isChange", ""));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResponseValue res = CommonHttpMethod.doPost(postMethod);
		
		System.out.println(res.getResponse());
	}
}
