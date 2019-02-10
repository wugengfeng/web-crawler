package com.crawler.framework.request.url;

import java.util.Map;
import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-21 11:20
 * @description: POST请求URL构造
 **/
public class PostRequestUrl extends AbstractPostRequestUrl {

    protected PostRequestUrl() {
    }

    protected PostRequestUrl(String url) {
        super(url);
    }

    protected PostRequestUrl(String url, String pageNumField, Integer pageNum) {
        super(url, pageNumField, pageNum);
    }

    protected PostRequestUrl(String url, String pageNumField, Integer pageNum, Integer step) {
        super(url, pageNumField, pageNum, step);
    }

    /**
     * 构造PostRequestUrl
     *
     * @param url 请求URL
     * @return
     */
    public static PostRequestUrl build(String url) {
        return new PostRequestUrl(url);
    }

    /**
     * 构建 PostRequestUrl 对象
     *
     * @param url          请求URL
     * @param pageNumField URL的分页字段
     * @param pageNum      起始页码
     * @return
     */
    public static PostRequestUrl build(String url, String pageNumField, Integer pageNum) {
        return new PostRequestUrl(url, pageNumField, pageNum);
    }

    /**
     * @param url          请求URL
     * @param pageNumField URL的分页字段
     * @param pageNum      起始页码
     * @param step         每页页数自增量
     * @return
     */
    public static PostRequestUrl build(String url, String pageNumField, Integer pageNum, Integer step) {
        return new PostRequestUrl(url, pageNumField, pageNum, step);
    }

    @Override
    public PostRequestUrl addParam(String key, String value) {
        this.put(key, value);
        return this;
    }

    @Override
    public PostRequestUrl replaceUrlPrefix(String placeholder, String value) {
        this.urlPrefix = urlPrefix.replace(placeholder, value);
        return this;
    }

    @Override
    public String getUrl() {
        return this.urlPrefix;
    }

    /**
     * 这是POST请求，只更新对象本身键值对。不返回URL
     *
     * @return
     */
    @Override
    public String pageTurn() {
        Objects.requireNonNull(this.urlPrefix);
        Objects.requireNonNull(this.pageNumField);
        Objects.requireNonNull(this.pageNum);

        // 页数 + 自增值
        this.pageNum += this.step;
        this.put(this.pageNumField, String.valueOf(this.pageNum));

        return null;
    }

    /**
     * 这是POST请求，只更新对象本身键值对。不返回URL
     *
     * @param param 额外参数
     * @return
     */
    @Override
    public String pageTurn(Map<String, String> param) {
        Objects.requireNonNull(this.urlPrefix);
        Objects.requireNonNull(this.pageNumField);
        Objects.requireNonNull(this.pageNum);

        // 页数 + 自增值
        this.pageNum += this.step;
        this.put(this.pageNumField, String.valueOf(this.pageNum));
        this.putAll(param);

        return null;
    }
}
