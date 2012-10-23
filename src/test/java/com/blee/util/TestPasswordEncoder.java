package com.blee.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestPasswordEncoder {

    @Test
    public void testEncoding() {
//        String enPassword = PasswordEncoder.passwordEncoding("thisismypassword", "fuckqq", "helloblee");
//        Assert.assertEquals("AB6D408B21C181D0937842AA77CFD15D", enPassword);
        String enPassword = PasswordEncoder.passwordEncoding("lelse+06291212", "LUDF", "\\x00\\x00\\x00\\x00\\x35\\x5e\\x59\\xc9");
        Assert.assertEquals("AF560F6EC4260CACA72C5D25270A72FC", enPassword);
    }
    
}
