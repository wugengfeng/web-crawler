package com.crawler.review.entity.crawler.feedback.result;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-18 18:25
 * @description:
 **/
public class RatingData {

    /**
     * 是否为空
     */
    private boolean isEmpty;

    /**
     * 评价文本内容
     */
    private Text text;

    /**
     * 评价日期
     */
    private String date;

    /**
     * 是否被禁止
     */
    private boolean wasSuppressed;

    /**
     * 禁止原因文本
     */
    private String suppressReasonText;

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Text getText() {
        return this.text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setWasSuppressed(boolean wasSuppressed) {
        this.wasSuppressed = wasSuppressed;
    }

    public boolean getWasSuppressed() {
        return this.wasSuppressed;
    }

    public void setSuppressReasonText(String suppressReasonText) {
        this.suppressReasonText = suppressReasonText;
    }

    public String getSuppressReasonText() {
        return this.suppressReasonText;
    }
}
