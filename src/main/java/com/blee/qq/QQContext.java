package com.blee.qq;

import com.blee.constant.LoginStatus;

public class QQContext {

    private String qqNumber;
    
    private String password;
    
    /**
     * 加密后的密码
     */
    private String enPassword;

    private VerifyCodeProvider verifyCodeProvider;
    
    private LoginStatus status;
    
    private String ptwebqq;
    
    private String skey;
    
    private String psessionId = null;
    
    private String vfwebqq = null;
    
    
    
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

    public String getEnPassword() {
        return enPassword;
    }

    public void setEnPassword(String enPassword) {
        this.enPassword = enPassword;
    }

    public LoginStatus getStatus() {
        return status;
    }

    public void setStatus(LoginStatus status) {
        this.status = status;
    }

    public String getPtwebqq() {
        return ptwebqq;
    }

    public void setPtwebqq(String ptwebqq) {
        this.ptwebqq = ptwebqq;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getPsessionId() {
        return psessionId;
    }

    public void setPsessionId(String psessionId) {
        this.psessionId = psessionId;
    }

    public String getVfwebqq() {
        return vfwebqq;
    }

    public void setVfwebqq(String vfwebqq) {
        this.vfwebqq = vfwebqq;
    }
    
}
