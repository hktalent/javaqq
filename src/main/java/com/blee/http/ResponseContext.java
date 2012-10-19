package com.blee.http;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;

public interface ResponseContext {

	HttpResponse get();

	String getAsString();

	@SuppressWarnings("rawtypes")
	Map getAsJsonMap();

	int getStatus();
	
	boolean isOk();
	
	String getHeader(String name);
	
	Cookie getCookie(String name);
	
}
