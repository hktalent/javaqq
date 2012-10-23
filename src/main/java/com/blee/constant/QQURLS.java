package com.blee.constant;

/**
 * 保存webqq协议的url
 * @author blee
 *
 */
public interface QQURLS {

    //http://check.ptlogin2.qq.com/check?uin=${qqNumber}&appid=1003903&r=${random}
    public final static String ACOUNT_CHECK = "http://check.ptlogin2.qq.com/check";
    
    //http://ptlogin2.qq.com/login?u=895375817&p=5042354E7DDA007A8149337F7F6DF56E&verifycode=!3HA&webqq_type=10&remember_uin=1&login2qq=1&aid=1003903&u1=http%3A%2F%2Fweb3.qq.com%2Floginproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=6-15-28572&mibao_css=m_webqq&t=1&g=1
//    public final static String QQ_LOGIN = "http://ptlogin2.qq.com/login?u=${qqNumber}&p=${password}&verifycode=${vcode}&webqq_type=10&remember_uin=1&login2qq=1&aid=1003903&u1=${loginUrl}&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=7-24-1937704&mibao_css=m_webqq&t=1&g=1";
    public final static String QQ_LOGIN = "http://ptlogin2.qq.com/login?webqq_type=10&remember_uin=1&login2qq=1&u1=http%3A%2F%2Fweb3.qq.com%2Floginproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=6-15-28572&mibao_css=m_webqq&t=1&g=1";
    
    public final static String CHANNEL_URL = "http://d.web2.qq.com/channel/login2";
    
    //http://captcha.qq.com/getimage?aid=1003903&r=" + Math.random() + "&uin=" + account
    public final static String QQ_VERIFY_URL = "http://captcha.qq.com/getimage";
    
}
