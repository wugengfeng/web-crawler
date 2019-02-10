package com.crawler.framework.spider;

import okhttp3.Request;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-15 10:44
 * @description: 爬虫解析对象
 **/
public class SpiderParse<T> {

    public SpiderParse(List<T> itemList, Request request) {
        this.itemList = itemList;
        this.request = request;
    }

    private List<T> itemList;
    private Request request;

    public List<T> getItemList() {
        return itemList;
    }

    public Request getRequest() {
        return request;
    }
}
