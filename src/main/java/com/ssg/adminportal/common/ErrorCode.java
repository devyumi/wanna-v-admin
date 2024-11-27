package com.ssg.adminportal.common;

public enum ErrorCode {
    INVALID_VALUE(400, "Invalid value provided"),
    NON_EXISTENT_ID(404, "The ID does not exist"),
    INVALID_REQUEST_TYPE(400, "Invalid request type provided"),
    DATE_CALCULATION_ERROR(500, "An error occurred while calculating the date range"),
    SENTIMENT_ANALYSIS_FAILURE(500, "An review sentiment analysis failed"),
    CLOVA_API_CLIENT_ERROR(400, "Clova Studio API returned a client error"),
    CLOVA_API_SERVER_ERROR(500, "Clova Studio API returned a server error");

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
