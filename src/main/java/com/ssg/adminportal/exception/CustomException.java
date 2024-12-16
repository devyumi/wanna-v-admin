package com.ssg.adminportal.exception;

import com.ssg.adminportal.common.ErrorCode;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getError());
        this.errorCode = errorCode;
    }
}