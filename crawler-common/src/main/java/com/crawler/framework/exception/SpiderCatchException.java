package com.crawler.framework.exception;

import com.crawler.framework.spider.SpiderInfo;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-15 18:16
 * @description: 反爬虫异常
 **/
public class SpiderCatchException extends RuntimeException {
    private SpiderInfo<?> spiderInfo;

    public SpiderCatchException(SpiderInfo<?> spiderInfo) {
        this.spiderInfo = spiderInfo;
    }

    public SpiderCatchException(String message, SpiderInfo<?> spiderInfo) {
        super(message);
        this.spiderInfo = spiderInfo;
    }

    public SpiderCatchException(String message, Throwable cause, SpiderInfo<?> spiderInfo) {
        super(message, cause);
        this.spiderInfo = spiderInfo;
    }

    public SpiderCatchException(Throwable cause, SpiderInfo<?> spiderInfo) {
        super(cause);
        this.spiderInfo = spiderInfo;
    }

    public SpiderCatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, SpiderInfo<?> spiderInfo) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.spiderInfo = spiderInfo;
    }

    public SpiderInfo<?> getSpiderInfo() {
        return spiderInfo;
    }

    public void setSpiderInfo(SpiderInfo<?> spiderInfo) {
        this.spiderInfo = spiderInfo;
    }
}
