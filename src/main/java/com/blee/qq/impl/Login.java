package com.blee.qq.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blee.constant.QQURLS;
import com.blee.constant.Static;
import com.blee.exception.ServiceException;
import com.blee.http.RequestContext;
import com.blee.http.ResponseContext;
import com.blee.http.impl.DefaultRequestContext;
import com.blee.model.Check;
import com.blee.qq.Protocol;
import com.blee.qq.QQContext;
import com.blee.qq.VerifyCodeProvider;
import com.blee.util.CheckUtil;
import com.blee.util.PasswordEncoder;
import com.blee.util.ServiceUtil;

public class Login implements Protocol {
    
    private static Logger log = LoggerFactory.getLogger(Login.class);

    @Override
    public void process(QQContext context) {
        check(context);
        Check check = processCheck(context);
        if(check == null) {
            throw new ServiceException("can't load check");
        }
        VerifyCodeProvider provider = context.getVerifyCodeProvider();
        String vCode = provider.getVerifyCode(check);
        check.setvCode(vCode);
        encodePassword(context, check);
        RequestContext request = new DefaultRequestContext().usingGet().usingUrl(QQURLS.QQ_LOGIN);
        request.addParam("u", context.getQqNumber());
        request.addParam("p", context.getHexPassword());
        request.addParam("verifycode", check.getvCode());
        request.addParam("appid", Static.APP_ID);
        request.addParam("r", Math.random());
        try {
            ResponseContext response = ServiceUtil.getHttpService().execute(request);
            String result = response.getAsString();
            if(result.indexOf("登陆成功") != -1) {
                //r:{"status":"online|hidden","ptwebqq":"1820c15121d77a5427a0e1df4af6981bb050d6f27bcaa36104212a0e5d7e7cd2","passwd_sig":"","clientid":"50428031","psessionid":null}
                //clientid:50428031
                //psessionid:null
                //TODO
            }
        } catch (IOException e) {
            log.error("login fail", e);
            throw new ServiceException(e);
        }
        throw new ServiceException("login failed");
    }
    
    private void check(QQContext context) {
        //TODO 检查context里参数是不是完整
        //不完整抛出ServiceException
    }
    
    private Check processCheck(QQContext context) {
        String qqNumber = context.getQqNumber();
        RequestContext request = new DefaultRequestContext().usingGet().usingUrl(QQURLS.ACOUNT_CHECK);
        request.addParam("uin", qqNumber);
        request.addParam("appid", Static.APP_ID);
        request.addParam("r", Math.random());
        
        try {
            ResponseContext response = ServiceUtil.getHttpService().execute(request);
            String result = response.getAsString();
            Check check = CheckUtil.generateCheck(result);
            if(check == null) {
                throw new ServiceException("can't load check from " + QQURLS.ACOUNT_CHECK);
            }
            return check;
        } catch (IOException e) {
            log.error("can't execute check method for " + e.getMessage(), e);
            return null;
        }
    }
    
    private void encodePassword(QQContext context, Check check) {
        String enPassword = PasswordEncoder.passwordEncoding(context.getPassword(), check.getvCode(), check.getVerifycodeHex());
        context.setHexPassword(enPassword);
    }

}
