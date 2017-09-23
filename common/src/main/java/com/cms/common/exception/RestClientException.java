package com.cms.common.exception;

/**
 * RestClient 异常消息处理
 * Created by houjian on 2015/8/1.
 */
public class RestClientException extends RuntimeException {
    private String message;

    private Throwable cause;

    public RestClientException() {
        super();
    }

    public RestClientException(String message) {
        this.message = message;
    }

    public RestClientException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public RestClientException(Throwable cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }
}
