package com.pukkaspice.web.common.exception;

@SuppressWarnings("serial")
public class UserExistsException extends Exception {

    public UserExistsException() {}
    
    public UserExistsException(String msg) {
        super(msg);
    }
}
