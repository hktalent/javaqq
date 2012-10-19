package com.blee.http.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;

import com.blee.exception.HttpException;
import com.blee.http.ResponseContext;
import com.blee.util.IOUtils;
import com.blee.util.StringUtils;
import com.google.gson.Gson;

public class DefaultResponseContext implements ResponseContext {

	private static final Charset defaultCharset = Consts.UTF_8;
	private HttpResponse result;

	private String stringResult;

	private ByteArrayOutputStream content;

	private Charset contentCharset;
	
	private List<Cookie> cookies;

	public DefaultResponseContext() {
	}

	public void setResult(HttpResponse result) throws IOException {
		this.result = result;
		HttpEntity entity = result.getEntity();
		int i = (int) entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		content = new ByteArrayOutputStream(i);
		ContentType contentType = ContentType.get(entity);
		Charset charset = contentType != null ? contentType.getCharset() : null;
		if (charset == null) {
			charset = defaultCharset;
		}
		contentCharset = charset;
		try {
			IOUtils.copy(entity.getContent(), content);
		} catch (IOException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
    public void setCookie(CookieStore cookieStore) {
	    if(cookieStore != null) {
	        this.cookies = cookieStore.getCookies();
	    } else {
	        this.cookies = Collections.EMPTY_LIST;
	    }
	}

	@Override
	public HttpResponse get() {
		return result;
	}

	@Override
	public int getStatus() {
		return result.getStatusLine().getStatusCode();
	}

	@Override
	public String toString() {
		return "statusCode:" + getStatus();
	}

	@Override
	public boolean isOk() {
		return getStatus() == HttpStatus.SC_OK;
	}

	@Override
	public String getAsString() {
		try {
			if (stringResult != null)
				return stringResult;
			if (this.content == null || this.content.size() == 0)
				return null;
			return (stringResult = content.toString(this.contentCharset.name()));
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new HttpException("getAsString error.", e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getAsJsonMap() {
		String str = getAsString();
		if (str == null) {
			return Collections.EMPTY_MAP;
		}
		Gson g = new Gson();
		return g.fromJson(getAsString(), Map.class);
	}

	@Override
	public String getHeader(String name) {
		Header header = get().getFirstHeader(name);
		return header != null ? header.getValue() : null;
	}

    @Override
    public Cookie getCookie(String name) {
        if(cookies == null) {
            return null;
        }
        Iterator<Cookie> it = cookies.iterator();
        while(it.hasNext()) {
            Cookie cookie = it.next();
            if(StringUtils.equals(name, cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

}
