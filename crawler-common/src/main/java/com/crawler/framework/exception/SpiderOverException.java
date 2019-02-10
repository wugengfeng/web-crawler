package com.crawler.framework.exception;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-17 15:56
 * @description: 爬虫强制结束异常
 **/
public class SpiderOverException extends RuntimeException {
    public SpiderOverException() {
    }

    public SpiderOverException(String message) {
        super(message);
    }

    public SpiderOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpiderOverException(Throwable cause) {
        super(cause);
    }

    public SpiderOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
