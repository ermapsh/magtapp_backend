package com.magtapp.pro.exception;

import lombok.Getter;

@Getter
public class BusinessRuleViolationException extends RuntimeException{
    private final Integer errorCode;

    public BusinessRuleViolationException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
