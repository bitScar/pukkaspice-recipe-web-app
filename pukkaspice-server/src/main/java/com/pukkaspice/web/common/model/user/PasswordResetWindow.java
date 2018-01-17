package com.pukkaspice.web.common.model.user;

import java.util.Date;

public class PasswordResetWindow {
    
    private String emailAddress;
    
    private String resetKey;
    
    private Date experationDate;
    
    private Date dateChanged;
    
    
    public PasswordResetWindow() { }
    
    public PasswordResetWindow(String emailAddress, String resetKey, Date experationDate, Date dateChanged) {
        super();
        this.emailAddress = emailAddress;
        this.resetKey = resetKey;
        this.experationDate = experationDate;
        this.dateChanged = dateChanged;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getExperationDate() {
        return experationDate;
    }

    public void setExperationDate(Date experationDate) {
        this.experationDate = experationDate;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }
    
}
