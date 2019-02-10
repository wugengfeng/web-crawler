package com.crawler.review.entity.crawler.feedback.result;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-18 18:25
 * @description:
 **/
public class Text {

    /**
     * 是否为空
     */
    private boolean isEmpty;

    /**
     * 截断的文本
     */
    private String truncatedText;

    /**
     * 评价文本
     */
    private String expandedText;

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setTruncatedText(String truncatedText) {
        this.truncatedText = truncatedText;
    }

    public String getTruncatedText() {
        return this.truncatedText;
    }

    public void setExpandedText(String expandedText) {
        this.expandedText = expandedText;
    }

    public String getExpandedText() {
        return this.expandedText;
    }
}