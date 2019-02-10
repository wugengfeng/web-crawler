package com.crawler.framework.spider;


import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.RequestManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-12 16:49
 * @description: 爬虫基础
 **/
public abstract class Spider<T> implements BasisSpider<T> {

    protected final static String REDIRECT = "redirect";

    public Spider() {
    }

    public Spider(List<String> allowedDomains, Map<String, Object> dict, List<ItemPipline<T>> itemPiplines, RequestManager requestManager, OkHttpClient client) {
        this.allowedDomains = allowedDomains;
        if (MapUtils.isEmpty(dict)) {
            dict = new HashMap<>();
        }
        this.dict = dict;
        this.itemPiplines = itemPiplines;
        this.requestManager = requestManager;
        this.client = client;
    }

    /**
     * 允许爬取的域名集合
     */
    protected List<String> allowedDomains;

    /**
     * 字典，用于自定义数据在爬虫组件中传递
     */
    protected Map<String, Object> dict = new HashMap<>();

    /**
     * 爬虫数据处理组件
     */
    protected List<ItemPipline<T>> itemPiplines;

    /**
     * OKHTTP3请求对象
     */
    protected RequestManager requestManager;

    /**
     * OKHTTP3请求实例，强烈建议一个爬虫拥有一个实例
     */
    protected OkHttpClient client;

    /**
     * 构建下一页请求
     *
     * @return
     */
    protected Request buildNextPageRequest() {
        return this.requestManager.getNextPageRequest();
    }

    /**
     * @param dict
     * @return
     */
    protected Request buildNextPageRequest(Map<String, String> dict) {
        return this.requestManager.getNextPageRequest(dict);
    }

    public List<String> getAllowedDomains() {
        return allowedDomains;
    }

    public void setAllowedDomains(List<String> allowedDomains) {
        this.allowedDomains = allowedDomains;
    }

    public List<ItemPipline<T>> getItemPiplines() {
        return itemPiplines;
    }

    public void setItemPiplines(List<ItemPipline<T>> itemPiplines) {
        this.itemPiplines = itemPiplines;
    }

    public Request getRequest() {
        return requestManager.getRequest();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public Map<String, Object> getDict() {
        return dict;
    }

    public void setDict(Map<String, Object> dict) {
        this.dict = dict;
    }

    /**
     * 随机暂停当前线程，用于测试
     */
    protected void stop() {
        int min = 1;
        int max = 10;
        int random = min + (int) (Math.random() * (max - min + 1));
        try {
            Thread.sleep(random * 1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
