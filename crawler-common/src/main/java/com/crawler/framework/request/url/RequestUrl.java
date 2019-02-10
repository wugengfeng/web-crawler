package com.crawler.framework.request.url;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-21 14:45
 * @description:
 **/
public abstract class RequestUrl extends HashMap<Serializable, Serializable> {

    /**
     * 基础URL
     */
    protected String urlPrefix;

    /**
     * 基础URL副本，此变量保存未进行占位符替换的urlPrefix
     */
    protected String cpUroPrefix;

    /**
     * URL的分页字段
     */
    protected String pageNumField;

    /**
     * 上一页页码
     */
    protected Integer previousPageNum;

    /**
     * 当前页码
     */
    protected Integer pageNum;

    /**
     * 下一页页码
     */
    protected Integer nextPageNum;

    /**
     * 每页页数自增量
     */
    protected Integer step = 1;

    /**
     * 获取url
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * 翻页，下一页
     *
     * @return
     */
    public abstract String pageTurn();

    /**
     * 翻页，下一页
     *
     * @param param 请求参数
     * @return
     */
    public abstract String pageTurn(Map<String, String> param);

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public String getCpUroPrefix() {
        return cpUroPrefix;
    }

    public String getPageNumField() {
        return pageNumField;
    }

    public Integer getPreviousPageNum() {
        return previousPageNum;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getNextPageNum() {
        return nextPageNum;
    }

    public Integer getStep() {
        return step;
    }
}
