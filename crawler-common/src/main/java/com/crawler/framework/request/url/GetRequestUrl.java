package com.crawler.framework.request.url;

import com.alibaba.fastjson.JSON;
import com.crawler.framework.exception.RequestUrlException;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-11 10:35
 * @description: HTTP URL对象，用于构建HTTP URL路径， 扩展HashMap
 * <p>
 * 在构建此对象时，URL的分页字段不能存在urlPrefix上，否则将抛出异常，
 * 分页信息强烈建议使用键值对设置
 * </p>
 **/
public class GetRequestUrl extends AbstractGetRequestUrl {

    protected GetRequestUrl() {
    }

    protected GetRequestUrl(String url) {
        super(url);
    }

    protected GetRequestUrl(String url, String pageNumField) {
        super(url, pageNumField);
    }

    protected GetRequestUrl(String url, String pageNumField, Integer step) {
        super(url, pageNumField, step);
    }

    /**
     * 构建 GetRequestUrl 对象，为外部提供创建此对象的方法
     *
     * @param url 请求URL
     * @return
     */
    public static GetRequestUrl build(String url) {
        return new GetRequestUrl(url);
    }

    /**
     * 构建 GetRequestUrl 对象，为外部提供创建此对象的方法
     *
     * @param url          请求URL
     * @param pageNumField URL的分页字段
     * @return
     */
    public static GetRequestUrl build(String url, String pageNumField) {
        return new GetRequestUrl(url, pageNumField);
    }

    /**
     * @param url          请求URL
     * @param pageNumField URL的分页字段
     * @param step         每页页数自增量
     * @return
     */
    public static GetRequestUrl build(String url, String pageNumField, Integer step) {
        return new GetRequestUrl(url, pageNumField, step);
    }

    @Override
    public GetRequestUrl addParam(Serializable key, Serializable value) {
        super.put(key, value);

        // 重新设置页数
        if (key.equals(this.pageNumField)) {
            this.initPageNum();
        }
        return this;
    }

    /**
     * 拼接成get请求参数
     *
     * @param isFormat 是否需要格式化（拼接问号）
     * @return
     */
    private String getUrlParam(boolean isFormat) {

        StringBuilder sb = new StringBuilder();
        if (isFormat) {
            sb.append("?");
        } else {
            sb.append("&");
        }

        int index = 0;
        for (Entry<Serializable, Serializable> entry : this.entrySet()) {
            if (index == 0) {
                index = -1;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sb.toString();
    }

    @Override
    public String getUrl() {
        String urlPrefix = this.urlPrefix;
        boolean isForamt = urlPrefix.contains("?");
        return this.urlPrefix + this.getUrlParam(!isForamt);
    }

    private String getCpUrl() {
        String urlPrefix = this.cpUroPrefix;
        boolean isForamt = urlPrefix.contains("?");
        return this.urlPrefix + this.getUrlParam(!isForamt);
    }

    @Override
    public String getJsonParam() {
        return JSON.toJSONString(this);
    }

    @Override
    public GetRequestUrl replaceUrlPrefix(String placeholder, String value) {
        this.urlPrefix = this.urlPrefix.replace(placeholder, value);
        return this;
    }

    @Override
    public String getNextPageUrl() {
        this.initPageNum();
        Objects.requireNonNull(this.pageNumField);
        Objects.requireNonNull(this.pageNum);
        Objects.requireNonNull(this.nextPageNum);

        String url = this.getUrl();
        this.checkUrl(url);

        String pageInfo = this.pageNumField + "=" + this.pageNum;
        String nextPageInfo = this.pageNumField + "=" + this.nextPageNum;
        return url.replace(pageInfo, nextPageInfo);
    }

    @Override
    public String getPreviousPageUrl() {
        this.initPageNum();
        Objects.requireNonNull(this.pageNumField);
        Objects.requireNonNull(this.pageNum);
        Objects.requireNonNull(this.previousPageNum);

        String url = this.getUrl();
        this.checkUrl(url);

        String pageInfo = this.pageNumField + "=" + this.pageNum;
        String previousPageInfo = this.pageNumField + "=" + this.previousPageNum;
        return url.replace(pageInfo, previousPageInfo);
    }

    @Override
    public String pageTurn() {
        this.initPageNum();
        Objects.requireNonNull(this.nextPageNum);
        Objects.requireNonNull(this.pageNum);
        Objects.requireNonNull(this.previousPageNum);
        Objects.requireNonNull(this.pageNumField);

        Integer oldPageNum = this.pageNum;

        String url = this.getUrl();
        this.checkUrl(url);

        String pageInfo = this.pageNumField + "=" + oldPageNum;
        String nextPagePageInfo = this.pageNumField + "=" + this.nextPageNum;

        this.previousPageNum += this.step;
        this.pageNum += this.step;
        this.nextPageNum += this.step;
        put(this.pageNumField, this.pageNum);
        return url.replace(pageInfo, nextPagePageInfo);
    }

    @Override
    public String pageTurn(Map<String, String> dict) {

        String url = null;
        if (MapUtils.isNotEmpty(dict)) {

            this.initPageNum();
            Objects.requireNonNull(this.nextPageNum);
            Objects.requireNonNull(this.pageNum);
            Objects.requireNonNull(this.previousPageNum);
            Objects.requireNonNull(this.pageNumField);

            Integer oldPageNum = this.pageNum;
            url = this.getCpUrl();
            this.checkUrl(url);

            String pageInfo = this.pageNumField + "=" + oldPageNum;
            String nextPagePageInfo = this.pageNumField + "=" + this.nextPageNum;
            this.previousPageNum += this.step;
            this.pageNum += this.step;
            this.nextPageNum += this.step;
            put(this.pageNumField, this.pageNum);
            url.replace(pageInfo, nextPagePageInfo);

            for (Map.Entry<String, String> entry : dict.entrySet()) {
                url.replace(entry.getKey(), entry.getValue());

            }
        }

        return url;
    }

    /**
     * 初始化分页数据
     */
    private void initPageNum() {
        Integer pageNum = MapUtils.getInteger(this, this.pageNumField);

        if (Objects.isNull(pageNum)) {
            throw new RequestUrlException(String.format("构建RequestUrl对象时设置的键值对没有分页字段 %s 信息",
                    this.pageNumField));
        }

        if (Objects.isNull(this.pageNum)) {
            this.pageNum = pageNum;
            this.previousPageNum = pageNum - this.step;
            this.nextPageNum = pageNum + this.step;
        }
    }

    /**
     * URL规范校验
     *
     * @param url
     */
    private void checkUrl(String url) {
        if (this.urlPrefix.contains(this.pageNumField)) {
            throw new RequestUrlException(String.format("RequestUrl对象的urlPrefix属性不能包含分页字段 %s，当前URL：%s",
                    this.pageNumField, url));
        }

        if (!url.contains(this.pageNumField)) {
            throw new RequestUrlException(String.format("构建RequestUrl对象时设置的键值对没有分页字段 %s 信息，当前URL：%s",
                    this.pageNumField, url));
        }
    }
}
