package com.blee.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestStringUtils {

    @Test
    public void testBytesToHex() {
        byte[] bt = new byte[]{
                1,2,3,15
        };
        String result = StringUtils.bytesToHex(bt);
        Assert.assertEquals("0102030f", result);
    }
    
    @Test
    public void testParseDomainFromHttp() {
        String result = StringUtils.parseDomainFromHttp("http://blee.sinaapp.com/javaqq/cookie.php");
        Assert.assertEquals("blee.sinaapp.com", result);
        result = StringUtils.parseDomainFromHttp("http://blee.sinaapp.com");
        Assert.assertEquals("blee.sinaapp.com", result);
        result = StringUtils.parseDomainFromHttp("https://blee.sinaapp.com");
        Assert.assertEquals("blee.sinaapp.com", result);
    }
    
}
