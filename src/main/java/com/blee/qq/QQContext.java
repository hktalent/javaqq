package com.blee.qq;

public class QQContext {

    private String qqNumber;
    
    private String password;
    
    /**
     * 加密后的密码
     */
    private String hexPassword;

    private VerifyCodeProvider verifyCodeProvider;
    
    
    
    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VerifyCodeProvider getVerifyCodeProvider() {
        return verifyCodeProvider;
    }

    public void setVerifyCodeProvider(VerifyCodeProvider verifyCodeProvider) {
        this.verifyCodeProvider = verifyCodeProvider;
    }

    public String getHexPassword() {
        return hexPassword;
    }

    public void setHexPassword(String hexPassword) {
        this.hexPassword = hexPassword;
    }
    
}
