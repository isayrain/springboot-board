package com.rest.api.advice.exception;

public class CUserNotFoundException extends RuntimeException {  // RuntimeException 상속받아서 생성
    public CUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CUserNotFoundException(String msg) {
        super(msg);
    }

    public CUserNotFoundException() {
        super() ;
    }
}
