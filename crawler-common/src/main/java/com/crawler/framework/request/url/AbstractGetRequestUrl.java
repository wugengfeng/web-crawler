package com.crawler.framework.request.url;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-12 16:05
 * @description: GET URL请求构造器
 **/
public abstract class AbstractGetRequestUrl extends RequestUrl {

    protected AbstractGetRequestUrl() {
    }

    protected AbstractGetRequestUrl(String url) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
    }

    protected AbstractGetRequestUrl(String url, String pageNumField) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
        this.pageNumField = pageNumField;
    }

    protected AbstractGetRequestUrl(String url, String pageNumField, Integer step) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
        this.pageNumField = pageNumField;
        this.step =step;
    }

    /**
     * 添加请求参数
     *
     * @param key   参数
     * @param value 参数值
     * @return
     */
    protected abstract AbstractGetRequestUrl addParam(Serializable key, Serializable value);

    /**
     * 将请求参数转为JSON格式
     */
    public abstract String getJsonParam();

    /**
     * URL前缀占位符值替换
     *
     * @param placeholder 占位符
     * @param value       占位符的值
     * @return
     */
    public abstract AbstractGetRequestUrl replaceUrlPrefix(String placeholder, String value);

    /**
     * 获取下一页URL
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractGetRequestUrl(String url, String pageNumField)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @return
     */
    public abstract String getNextPageUrl();

    /**
     * 获取上一页URL
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractGetRequestUrl(String url, String pageNumField)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @return
     */
    public abstract String getPreviousPageUrl();

    /**
     * 翻页，下一页
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractGetRequestUrl(String url, String pageNumField)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @return
     */
    public abstract String pageTurn();

    /**
     * 翻页，下一页。此方法会对第一次设置的URL进行占位符替换（URL副本），页数为当前页数+1
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractGetRequestUrl(String url, String pageNumField)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @param dict 字典，用来替换URL占位符
     * @return
     */
    public abstract String pageTurn(Map<String, String> dict);
}
