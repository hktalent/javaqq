package com.blee.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUtil {

    private static Pattern login = Pattern.compile(".*('.*','.*','.*','.*','(.*)', '.*')");
    
    public static String parseLoginStatus(String source) {
        Matcher mt = login.matcher(source);
        if(mt.find()) {
            return mt.group(2);
        }
        return null;
    }
    
}
