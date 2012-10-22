package com.blee.qq;

import com.blee.model.Check;

public interface VerifyCodeProvider {

    public String getVerifyCode(Check check);
    
}
