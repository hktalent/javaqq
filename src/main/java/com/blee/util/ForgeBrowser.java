package com.blee.util;

import java.util.HashMap;
import java.util.Map;

import com.blee.http.RequestContext;

public class ForgeBrowser {
//    if (null != refer) {
//        conn.addRequestProperty("Referer", refer);
//    }
////
//    conn.addRequestProperty("Cookie", cookie);
//    conn.addRequestProperty("Connection", "Keep-Alive");
//    conn.addRequestProperty("Accept-Language", "zh-cn");
//    conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
//    conn.addRequestProperty("Cache-Control", "no-cache");
//    conn.addRequestProperty("Accept-Charset", "UTF-8;");
//    conn.addRequestProperty("Host", "d.web2.qq.com");
//    conn.addRequestProperty(
//            "User-Agent",
//            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.19 (KHTML, like Gecko) Ubuntu/12.04 Chromium/18.0.1025.168 Chrome/18.0.1025.168 Safari/535.19");
    
    public static void forge(RequestContext request) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", "http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4");
        headers.put("Host", "d.web2.qq.com");
        headers.put("Origin", "http://d.web2.qq.com");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Charset", "UTF-8;");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-cn");
        request.addHeaders(headers);
    }
    
}
