package com.pukkaspice.web.common.model.user;

import java.util.Date;

public class VerificationRecord {
    
    private int userId;
    
    private String key;
    
    private Date verificationDate;
    
    
    public VerificationRecord() { }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }
    
}
