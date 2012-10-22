package com.blee.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.blee.exception.ServiceException;

public class PasswordEncoder {
    
    private static ScriptEngine se;
    
    static {
        //加密与标准md5有些不一样,直接翻译js成java比较麻烦,直接用js
        InputStream in = PasswordEncoder.class.getResourceAsStream("/js/encodePass.js");
        Reader inreader = new InputStreamReader(in);
        ScriptEngineManager m = new ScriptEngineManager();
        se = m.getEngineByName("javascript");
        try {
            se.eval(inreader);
        } catch (Exception e) {
        } finally {
            try {
                inreader.close();
            } catch (IOException e) {
            }
        }
    }

    public static String passwordEncoding(String password, String code, String verifyCode) {
//        String md5Pass = hexchar2bin(MD5.getMD5(password));
//        String h = MD5.getMD5(md5Pass + code);
//        String g = MD5.getMD5(h + verifycode);
        Object t;
        try {
            t = se.eval(String.format("passwordEncoding('%s', '%s', '%s');", password, code, verifyCode.toUpperCase()));
        } catch (ScriptException e) {
            throw new ServiceException(e.getMessage());
        }
        return t.toString();
    }
    
}
