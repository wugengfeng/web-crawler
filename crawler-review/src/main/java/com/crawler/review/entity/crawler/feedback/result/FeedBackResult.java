package com.crawler.review.entity.crawler.feedback.result;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-18 18:13
 * @description: 亚马逊FeedBack返回实体
 **/
public class FeedBackResult {

    /**
     * 是否为空
     */
    private boolean isEmpty;

    /**
     * 店铺评论明细
     */
    private List<Details> details;

    /**
     * 请求参数
     */
    private ActionParams actionParams;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有上一页
     */
    private boolean hasFeedback;

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public List<Details> getDetails() {
        return this.details;
    }

    public void setActionParams(ActionParams actionParams) {
        this.actionParams = actionParams;
    }

    public ActionParams getActionParams() {
        return this.actionParams;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean getHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasFeedback(boolean hasFeedback) {
        this.hasFeedback = hasFeedback;
    }

    public boolean getHasFeedback() {
        return this.hasFeedback;
    }
}
