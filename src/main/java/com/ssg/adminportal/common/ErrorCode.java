package com.ssg.adminportal.common;

public enum ErrorCode {
    INVALID_VALUE(400, "Invalid value provided"),
    NON_EXISTENT_ID(404, "The ID does not exist");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return "[" + code + "]" + " " + message;
    }
}
