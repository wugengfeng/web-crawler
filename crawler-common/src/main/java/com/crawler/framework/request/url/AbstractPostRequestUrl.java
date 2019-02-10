package com.crawler.framework.request.url;

import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-21 11:51
 * @description: POST URL请求构造器
 **/
public abstract class AbstractPostRequestUrl extends RequestUrl {

    protected AbstractPostRequestUrl() {
    }

    protected AbstractPostRequestUrl(String url) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
    }

    protected AbstractPostRequestUrl(String url, String pageNumField, Integer pageNum) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
        this.pageNumField = pageNumField;
        this.pageNum = pageNum;
    }

    protected AbstractPostRequestUrl(String url, String pageNumField, Integer pageNum, Integer step) {
        this.urlPrefix = url;
        this.cpUroPrefix = url;
        this.pageNumField = pageNumField;
        this.pageNum = pageNum;
        this.step = step;
    }

    /**
     * URL还原到构造时设置的值
     */
    public void initUrl() {
        this.urlPrefix = this.cpUroPrefix;
    }

    /**
     * 添加请求参数
     *
     * @param key
     * @param value
     * @return
     */
    protected abstract AbstractPostRequestUrl addParam(String key, String value);

    /**
     * url字符串替换
     *
     * @param placeholder
     * @param value
     * @return
     */
    protected abstract AbstractPostRequestUrl replaceUrlPrefix(String placeholder, String value);

    /**
     * 翻页，下一页
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractPostRequestUrl(String url, String pageNumField, Integer pageNum)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @return
     */
    public abstract String pageTurn();

    /**
     * 翻页，下一页
     * <p>
     * 调用此方法必须使用此构造函数构建 <code>AbstractPostRequestUrl(String url, String pageNumField, Integer pageNum)</code>,
     * 否则将抛出 RequestUrlException 异常
     * </p>
     *
     * @param param 额外参数
     * @return
     */
    public abstract String pageTurn(Map<String, String> param);

    public String getPageNumField() {
        return pageNumField;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public String getUrlCp() {
        return cpUroPrefix;
    }
}
