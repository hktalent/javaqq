package com.blee.qq;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.blee.constant.LoginStatus;
import com.blee.qq.impl.DefaultVerifyCodeProvider;
import com.blee.qq.impl.Login;
import com.blee.util.StaticProperties;

public class TestLogin {

    private String qqNumber = StaticProperties.instance().loadString("number");
    private String password = StaticProperties.instance().loadString("password");
    
    private VerifyCodeProvider verifyCodeProvider;
    
    @Before
    public void init() {
        verifyCodeProvider = new DefaultVerifyCodeProvider();
    }
    
    @Test
    public void testLogin() {
        QQContext context = new QQContext();
        context.setQqNumber(qqNumber);
        context.setPassword(password);
        context.setVerifyCodeProvider(verifyCodeProvider);
        context.setStatus(LoginStatus.hidden);
        
        Protocol p = new Login();
        
        p.process(context);
        
        Assert.assertNotNull(context.getPsessionId());
        
    }
    
}
