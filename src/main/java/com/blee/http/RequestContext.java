package com.blee.http;

import java.util.Map;

public interface RequestContext {
	String METHOD_GET = "GET", METHOD_POST = "POST";

	/**
	 * http请求参数
	 * 
	 * @return
	 */
	Map<String, Object> getParams();

	/**
	 * http请求头
	 * 
	 * @return
	 */
	Map<String, String> getHeaders();

	/**
	 * @return http连接参数
	 */
	Map<String, Object> getHttpParams();
	
	Map<String, String> getCookies();
	
	/**
	 * 默认从url里求
	 * @param domain
	 */
	void setCookieDomain(String domain);
	
	String getCookieDomain();
	
	/**
	 * 默认 '/'
	 * @param path
	 */
	void setCookiePath(String path);
	
	String getCookiePath();

	String getUrl();

	String getMethod();

	/**
	 * 添加http请求参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	RequestContext addParam(String name, Object value);

	/**
	 * 添加http请求参数
	 * 
	 * @param headers
	 * @return
	 */
	RequestContext addParams(Map<String, Object> headers);

	/**
	 * 添加http请求头
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	RequestContext addHeader(String name, String value);

	/**
	 * 添加http请求参数
	 * 
	 * @param headers
	 * @return
	 */
	RequestContext addHeaders(Map<String, String> headers);

	/**
	 * 添加http连接参数
	 * 
	 * @param params
	 * @return
	 */
	public RequestContext addHttpParams(Map<String, String> params);

	/**
	 * 添加http连接参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public RequestContext addHttpParam(String name, Object value);
	
	/**
	 * 添加cookie
	 * @param name
	 * @param value
	 * @return
	 */
	public RequestContext addCookie(String name, String value);
	
	/**
	 * 添加cookie
	 * @param cookies
	 * @return
	 */
	public RequestContext addCookies(Map<String, String> cookies);
	
	public RequestContext initDefault();

}
