package com.blee.qq.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.blee.util.LoginUtil;
import com.blee.util.PasswordEncoder;
import com.blee.util.ServiceUtil;
import com.blee.util.StringUtils;
import com.google.gson.Gson;

public class Login implements Protocol {
    
    private static Logger log = LoggerFactory.getLogger(Login.class);

    @SuppressWarnings("unchecked")
    @Override
    public void process(QQContext context) {
        check(context);
        Check check = processCheck(context);
        if(check == null) {
            throw new ServiceException("can't load check");
        }
        check.setQqNumber(context.getQqNumber());
        VerifyCodeProvider provider = context.getVerifyCodeProvider();
        String vCode = provider.getVerifyCode(check);
        check.setvCode(vCode.toUpperCase());
        encodePassword(context, check);
//        Template template = new Template(QQURLS.QQ_LOGIN);
//        template.set("qqNumber", context.getQqNumber());
//        template.set("password", context.getEnPassword().toLowerCase());
//        template.set("vcode", check.getvCode());
//        String loginStr = "http%3A%2F%2Fweb3.qq.com%2Floginproxy.html%3Flogin2qq%3D1%26webqq_type%3D10";
//        template.set("loginUrl", loginStr);
        RequestContext request = new DefaultRequestContext().usingGet().usingUrl(QQURLS.QQ_LOGIN)/*.initDefault()*/;
        request.addParam("u", context.getQqNumber());
        request.addParam("p", context.getEnPassword());
        request.addParam("verifycode", check.getvCode());
        request.addParam("aid", Static.APP_ID);
        request.addParam("r", Math.random());
        try {
            ResponseContext response = ServiceUtil.getHttpService().execute(request);
            String result = response.getAsString();
            log.info("第一次登陆:{}", result);
            String loginResult = LoginUtil.parseLoginStatus(result);
            log.info("登陆结果: {}", loginResult);
            if(!StringUtils.isEmpty(loginResult) && loginResult.indexOf("登录成功") != -1) {
                String ptwebqq = response.getCookie("ptwebqq").getValue();
                String skey = response.getCookie("skey") == null ? null : response.getCookie("skey").getValue();
                //set to context
                context.setPtwebqq(ptwebqq);
                context.setSkey(skey);
                Map<String, String> r = new HashMap<String, String>();
                r.put("status", context.getStatus().name());
                r.put("ptwebqq", ptwebqq);
                r.put("passwd_sig", "");
                r.put("clientid", Static.CLIENT_ID);
                r.put("psessionid", null);
                Gson gson = new Gson();
                String rStr = gson.toJson(r);
                log.info(rStr);
                //二次登陆
                //r:{"status":"online","ptwebqq":"1820c15121d77a5427a0e1df4af6981bb050d6f27bcaa36104212a0e5d7e7cd2","passwd_sig":"","clientid":"50428031","psessionid":null}
                //clientid:50428031
                //psessionid:null
                request = new DefaultRequestContext().usingPost().usingUrl(QQURLS.CHANNEL_URL).initDefault();
                String contents = "{\"status\":\"" + context.getStatus().name() + "\",\"ptwebqq\":\"" + ptwebqq
                        + "\",\"passwd_sig\":\"\",\"clientid\":\"" + Static.CLIENT_ID + "\"}";

                log.info(contents);

                request.addParam("r", contents);
//                request.addParam("clientid", null);
//                request.addParam("psessionid", null);
                response = ServiceUtil.getHttpService().execute(request);
//                {
//                    "retcode": 0,
//                    "result": {
//                            "uin": qqnumber,
//                            "cip": 2005991581,
//                            "index": 1060,
//                            "port": 50257,
//                            "status": "hidden",
//                            "vfwebqq": "longtext",
//                            "psessionid": "longtext",
//                            "user_state": 0,
//                            "f": 0
//                    }
//                }
                result = response.getAsString();
                log.info("channel login result :  {}", result);
                
                Map<String, Object> rm = gson.fromJson(result, Map.class);
                int retcode = ((Double)rm.get("retcode")).intValue();
                if(retcode == 0) {
                    String loginResult2 = rm.get("result").toString();
                    Map<String, Object> lrm = gson.fromJson(loginResult2, Map.class);
                    context.setPsessionId(lrm.get("psessionid").toString());
                    context.setVfwebqq(lrm.get("vfwebqq").toString());
                    return;
                }
            } else {
                throw new ServiceException(loginResult);
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
        RequestContext request = new DefaultRequestContext().usingGet().usingUrl(QQURLS.ACOUNT_CHECK).initDefault();
        request.addParam("uin", qqNumber);
        request.addParam("appid", Static.APP_ID);
        request.addParam("r", Math.random());
        
        try {
            ResponseContext response = ServiceUtil.getHttpService().execute(request);
            String result = response.getAsString();
            log.info("check result: {}", result);
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
        String enPassword = PasswordEncoder.passwordEncoding(context.getPassword(), check.getvCode().toUpperCase(), check.getVerifycodeHex());
        context.setEnPassword(enPassword);
    }

}
