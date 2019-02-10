package com.crawler.framework.pipeline;

import com.crawler.framework.spider.SpiderInfo;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 09:50
 * @description: 爬虫处理数据组件
 **/
public abstract class ItemPipline<T> {

    /**
     * 前置通知，调用 processItem方法前调用
     *
     * @param spiderInfo
     */
    public void beFore(SpiderInfo<T> spiderInfo) {
    }

    /**
     * 数据处理方法
     *
     * @param spiderInfo
     * @param itemList
     * @return
     */
    public List<T> processItem(SpiderInfo<T> spiderInfo, List<T> itemList) {
        return null;
    }

    /**
     * 后置通知，调用processItem方法后调用
     */
    public void alter(SpiderInfo<T> spiderInfo) {
    }
}
