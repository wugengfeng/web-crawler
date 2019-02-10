package com.crawler.framework.spider;

import okhttp3.Response;

import java.util.List;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 09:51
 * @description: 爬虫数据对象
 **/
public class SpiderInfo<T> {

    /**
     * 当前解析对象
     */
    private List<T> itemList;

    /**
     * 请求响应
     */
    private Response response;

    /**
     * 爬虫
     */
    private Class<?> spider;

    /**
     * 数据字段，用于在爬虫组建间通信
     */
    private Map<String, Object> dict;

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Class<?> getSpider() {
        return spider;
    }

    public void setSpider(Class<?> spider) {
        this.spider = spider;
    }

    public Map<String, Object> getDict() {
        return dict;
    }

    public void setDict(Map<String, Object> dict) {
        this.dict = dict;
    }
}
