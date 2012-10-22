package com.blee.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestPasswordEncoder {

    @Test
    public void testEncoding() {
        String enPassword = PasswordEncoder.passwordEncoding("thisismypassword", "fuckqq", "helloblee");
        Assert.assertEquals("AB6D408B21C181D0937842AA77CFD15D", enPassword);
    }
    
}
