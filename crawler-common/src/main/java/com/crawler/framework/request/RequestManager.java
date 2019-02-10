package com.crawler.framework.request;

import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.request.url.RequestUrl;
import okhttp3.Request;

import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-12 16:22
 * @description: OKHTTP3请求构造管理器
 **/
public abstract class RequestManager {

    protected static final String USER_AGENT = "User-Agent";

    public RequestManager(RequestUrl requestUrl, UserAgent userAgent) {
        this.requestUrl = requestUrl;
        this.userAgent = userAgent;
    }

    protected RequestUrl requestUrl;
    protected UserAgent userAgent;

    /**
     * 获取请求对象
     *
     * @return
     */
    public abstract Request getRequest();

    /**
     * 获取请求对象
     *
     * @param head 请求头
     * @return
     */
    public abstract Request getRequest(Map<String, String> head);

    /**
     * 获取下一页请求对象
     *
     * @return
     */
    public abstract Request getNextPageRequest();

    /**
     * 获取下一页请求对象
     *
     * @param dict 字典，用来替换第一次设置URL的占位符（使用的是RequestUrl 构造函数设置URL的副本变量）
     * @return
     */
    public abstract Request getNextPageRequest(Map<String, String> dict);

    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(RequestUrl requestUrl) {
        this.requestUrl = requestUrl;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgent userAgent) {
        this.userAgent = userAgent;
    }
}
