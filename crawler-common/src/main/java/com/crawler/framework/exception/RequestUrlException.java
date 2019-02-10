package com.crawler.framework.exception;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-11 12:04
 * @description: RequestUrl 异常
 **/
public class RequestUrlException extends RuntimeException{

    public RequestUrlException() {
    }

    public RequestUrlException(String message) {
        super(message);
    }

    public RequestUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestUrlException(Throwable cause) {
        super(cause);
    }

    public RequestUrlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
