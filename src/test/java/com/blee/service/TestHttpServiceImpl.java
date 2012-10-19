package com.blee.service;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.cookie.Cookie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blee.http.RequestContext;
import com.blee.http.ResponseContext;
import com.blee.http.impl.DefaultRequestContext;
import com.blee.service.impl.HttpServiceImpl;

public class TestHttpServiceImpl {

    private HttpService httpService;
    
    @Before
    public void init() {
        httpService = new HttpServiceImpl();
    }
    
    @After
    public void destroy() {
        httpService.destroy();
    }
    
    @Test
    public void testStatus404() {
        DefaultRequestContext request = new DefaultRequestContext();
        request.setUrl("http://blee.sinaapp.com/404.php");
        try {
            ResponseContext response = httpService.execute(request);
            Assert.assertEquals(false, response.isOk());
            Assert.assertEquals(404, response.getStatus());
//            responseContext.getStatus();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void testGet() {
        DefaultRequestContext request = new DefaultRequestContext();
        request.setMethod(RequestContext.METHOD_GET);
        request.setUrl("http://blee.sinaapp.com/javaqq/success.php");
        request.addParam("name", "blee");
        try {
            ResponseContext response = httpService.execute(request);
            Assert.assertEquals("Hello,blee", response.getAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void testPost() {
        DefaultRequestContext request = new DefaultRequestContext();
        request.setMethod(RequestContext.METHOD_POST);
        request.setUrl("http://blee.sinaapp.com/javaqq/post.php");
        request.addParam("name", "blee");
        try {
            ResponseContext response = httpService.execute(request);
            Assert.assertEquals("Hello, post blee", response.getAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void testCookie() {
        DefaultRequestContext request = new DefaultRequestContext();
        request.setMethod(RequestContext.METHOD_POST);
        request.setUrl("http://blee.sinaapp.com/javaqq/cookie.php");
        request.addCookie("client_cookie", "test");
        try {
            ResponseContext response = httpService.execute(request);
            Cookie cookie = response.getCookie("server_cookie");
            Assert.assertNotNull(cookie);
            Assert.assertEquals("Blee", cookie.getValue());
            Assert.assertEquals("client_cookie=test", response.getAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
    
}
