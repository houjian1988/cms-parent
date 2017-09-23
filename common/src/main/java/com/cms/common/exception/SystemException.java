package com.cms.common.exception;

/**
 * 系统异常信息
 * Created by houjian on 2015/8/1.
 */
public class SystemException extends RuntimeException {
    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
