package com.blee.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blee.model.Check;

public class CheckUtil {
    
    static Pattern cp = Pattern.compile("\\'(.+)\\'\\,\\'(.+)\\'\\,\\'(.+)\\'");

    public static Check generateCheck(String checkStr) {
        Matcher m = cp.matcher(checkStr);
        String type = "";
        String enVerifyCode = "";
        String verifycodeHex = "";
        if (m.find()) {
            type = m.group(1);
            enVerifyCode = m.group(2);
            verifycodeHex = m.group(3);
            Check result = new Check();
            result.setType(type);
            result.setEnVerifyCode(enVerifyCode);
            result.setVerifycodeHex(verifycodeHex);
            return result;
        }
        return null;
    }
    
}
