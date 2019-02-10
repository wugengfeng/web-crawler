package com.crawler.constant;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 16:39
 * @description: 爬取状态枚举
 **/
public enum CrawlStatusEnum {
    CRAWL("爬取完成"),
    INTERRUPT("中断爬取"),
    NON_CRAWL("未开始爬取");

    private String description;

    CrawlStatusEnum(String description) {
        this.description = description;
    }
}
