package com.blee.model;

public class Check {

    private String type;
    
    private String vCode;
    
    private String enVerifyCode;
    
    private String verifycodeHex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnVerifyCode() {
        return enVerifyCode;
    }

    public void setEnVerifyCode(String enVerifyCode) {
        this.enVerifyCode = enVerifyCode;
    }

    public String getVerifycodeHex() {
        return verifycodeHex;
    }

    public void setVerifycodeHex(String verifycodeHex) {
        this.verifycodeHex = verifycodeHex;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    @Override
    public String toString() {
        return "Check [type=" + type + ", vCode=" + vCode + ", enVerifyCode="
                + enVerifyCode + ", verifycodeHex=" + verifycodeHex + "]";
    }
    
}
