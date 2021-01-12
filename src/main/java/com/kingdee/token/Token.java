package com.kingdee.token;

import java.util.Date;

public class Token {
    /* 令牌 */
    private String Token;

    private String code;

    /* 有效期 默认１小时 3600s */
    private int Validity;

    private String IPAddress;

    private String Language;

    // 更新时间
    private Date Create;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValidity() {
        return Validity;
    }

    public void setValidity(int validity) {
        Validity = validity;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String iPAddress) {
        IPAddress = iPAddress;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public Date getCreate() {
        return Create;
    }

    public void setCreate(Date create) {
        Create = create;
    }

}
