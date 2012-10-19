package com.blee.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blee.http.RequestContext;
import com.blee.http.ResponseContext;
import com.blee.http.impl.DefaultResponseContext;
import com.blee.service.HttpService;

public class HttpServiceImpl implements HttpService {
    private DefaultHttpClient client;
    private static final Logger log = LoggerFactory
            .getLogger(HttpServiceImpl.class);

    public HttpServiceImpl(ClientConnectionManager connectionManager) {
        client = new DefaultHttpClient(connectionManager);
    }

    public HttpServiceImpl() {
        this(new PoolingClientConnectionManager());
    }

    @Override
    public void destroy() {
        if (client != null)
            client.getConnectionManager().shutdown();
    }

    private static List<NameValuePair> toNameValueList(
            Map<String, Object> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> kv : params.entrySet()) {
            pairs.add(new BasicNameValuePair(kv.getKey(),// 使用空字符串""作为查询的参数为null时的值
                    kv.getValue() != null ? kv.getValue().toString() : ""));
        }
        return pairs;
    }

    private static String toQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> kv : params.entrySet()) {
            sb.append(kv.getKey());
            sb.append("=");
            // 使用空字符串""作为查询的参数为null时的值
            sb.append(kv.getValue() != null ? kv.getValue().toString() : "");
            sb.append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private void setup(HttpRequestBase request, RequestContext context) {
        for (Map.Entry<String, String> h : context.getHeaders().entrySet()) {
            request.addHeader(h.getKey(), h.getValue());
        }
        if (context.getHttpParams().size() > 0) {
            HttpParams httpParams = new BasicHttpParams();
            for (Map.Entry<String, Object> hp : context.getHttpParams()
                    .entrySet()) {
                httpParams.setParameter(hp.getKey(), hp.getValue());
            }
            request.setParams(httpParams);
        }
        if(context.getCookies().size() > 0) {
            CookieStore cookieStore = client.getCookieStore();
            for (Map.Entry<String, String> ck : context.getCookies().entrySet()) {
                BasicClientCookie2 bc2 = new BasicClientCookie2(ck.getKey(), ck.getValue());
                bc2.setPath(context.getCookiePath());
                bc2.setDomain(context.getCookieDomain());
                cookieStore.addCookie(bc2);
            }
            client.setCookieStore(cookieStore);
        }
    }

    @Override
    public ResponseContext execute(RequestContext context) throws IOException {
        log.info("http request " + context);
        if (RequestContext.METHOD_GET.equalsIgnoreCase(context.getMethod())) {
            String query = null;
            if (context.getParams() != null && context.getParams().size() > 0) {
                query = toQueryString(context.getParams());
            }
            String url = context.getUrl() + (query != null ? "?" + query : "");
            HttpGet httpGet = new HttpGet(url);
            setup(httpGet, context);
            try {
                log.info("execute Get " + url);
                HttpContext localContext = new BasicHttpContext();
                HttpResponse resp = client.execute(httpGet, localContext);
                DefaultResponseContext result = new DefaultResponseContext();
                result.setResult(resp);
                result.setCookie(client.getCookieStore());
                log.info("got response\n" + result);
                return result;
            } finally {
                if (httpGet != null) {
                    httpGet.releaseConnection();
                    httpGet.abort();
                }
            }
        } else if (RequestContext.METHOD_POST.equals(context.getMethod())) {
            HttpPost httpPost = new HttpPost(context.getUrl());
            List<NameValuePair> nameValuePairs = toNameValueList(context
                    .getParams());
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset
                    .forName("UTF-8")));
            setup(httpPost, context);
            try {
                log.info("execute Post to " + context.getUrl());
                HttpContext localContext = new BasicHttpContext();
                HttpResponse resp = client.execute(httpPost, localContext);
                DefaultResponseContext result = new DefaultResponseContext();
                result.setResult(resp);
                result.setCookie(client.getCookieStore());
                log.info("got response\n" + result);
                return result;
            } finally {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                    httpPost.abort();
                }
            }
        } else {
            throw new InvalidParameterException("Unknown http method "
                    + context.getMethod());
        }
    }
}
