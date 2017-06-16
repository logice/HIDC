package hbc315.HIDC.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

public class ResponseValue {
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private String response;
	private String location;

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> value) {
		cookies = value;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String value) {
		response = value;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String value) {
		location = value;
	}

}