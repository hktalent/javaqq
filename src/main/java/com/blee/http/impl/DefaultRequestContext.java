package com.blee.http.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.blee.http.RequestContext;
import com.blee.util.StringUtils;

public class DefaultRequestContext implements RequestContext {
	private Map<String, Object> params = null;

	private Map<String, Object> httpParams = null;

	private Map<String, String> headers = null;
	
	private Map<String, String> cookies = null;

	private String url;

	private String method = RequestContext.METHOD_POST;

	private boolean signRequired;
	
	private String cookiePath = "/";
	
	private String cookieDomain;
	
	public DefaultRequestContext() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getParams() {
		if (params != null)
			return params;
		return Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getHeaders() {
		if (headers != null)
			return headers;
		return Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
    public Map<String, Object> getHttpParams() {
		if (httpParams != null)
			return httpParams;
		return Collections.EMPTY_MAP;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public void setUrl(String url) {
		this.url = url;
		if(StringUtils.isEmpty(cookieDomain)) {
		    this.cookieDomain = StringUtils.parseDomainFromHttp(this.url);
		}
	}

	public void setParams(Map<String, Object> params) {
		if (params == null)
			throw new IllegalArgumentException("request params can't be null");
		this.params = params;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public DefaultRequestContext usingUrl(String url) {
		setUrl(url);
		return this;
	}

	public DefaultRequestContext usingGet() {
		setMethod(RequestContext.METHOD_GET);
		return this;
	}

	public DefaultRequestContext usingMethod(String method) {
		setMethod(method);
		return this;
	}

	public DefaultRequestContext usingPost() {
		setMethod(RequestContext.METHOD_POST);
		return this;
	}

	public DefaultRequestContext addParams(Map<String, Object> headers) {
		if (this.params == null) {
			this.params = new HashMap<String, Object>();
		}
		this.params.putAll(headers);
		return this;
	}

	@Override
	public DefaultRequestContext addParam(String name, Object value) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(name, value);
		return this;
	}

	public DefaultRequestContext addHeaders(Map<String, String> headers) {
		if (this.headers == null) {
			this.headers = new HashMap<String, String>();
		}
		this.headers.putAll(headers);
		return this;
	}

	public RequestContext addHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put(name, value);
		return this;
	}

	public DefaultRequestContext addHttpParams(Map<String, String> params) {
		if (this.httpParams == null) {
			this.httpParams = new HashMap<String, Object>();
		}
		this.httpParams.putAll(params);
		return this;
	}

	public RequestContext addHttpParam(String name, Object value) {
		if (this.httpParams == null) {
			this.httpParams = new HashMap<String, Object>();
		}
		httpParams.put(name, value);
		return this;
	}

	public void setHeaders(Map<String, String> headers) {
		if (headers == null)
			throw new IllegalArgumentException("headers can't be null");
		this.headers = headers;
	}

	public void setHttpParams(Map<String, Object> httpParams) {
		if (httpParams == null)
			throw new IllegalArgumentException("httpParams can't be null");
		this.httpParams = httpParams;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public Map<String, String> getCookies() {
	    if(cookies != null) {
	        return cookies;
	    }
	    return Collections.EMPTY_MAP;
	}
	
	@Override
	public RequestContext addCookie(String name, String value) {
	    if(cookies == null) {
	        cookies = new HashMap<String, String>();
	    }
	    cookies.put(name, value);
	    return this;
	}
	
	@Override
	public RequestContext addCookies(Map<String, String> cookies) {
	    if (this.cookies == null) {
            this.cookies = new HashMap<String, String>();
        }
        this.cookies.putAll(cookies);
        return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultRequestContext [url=");
		builder.append(url);
		builder.append(", method=");
		builder.append(method);
		builder.append(", signRequired=");
		builder.append(signRequired);
		builder.append(", params=");
		builder.append(params);
		builder.append(", headers=");
		builder.append(headers);
		builder.append("]");
		return builder.toString();
	}

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

}
