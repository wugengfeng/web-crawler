package com.crawler.review.entity.crawler.feedback;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Table(name = "feed_back_detail")
public class FeedBackDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号id
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 账号名称（店铺）
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 站点id
     */
    @Column(name = "site_id")
    private Integer siteId;

    /**
     * 站点
     */
    private String site;

    /**
     * 评分等级
     */
    private Byte rating;

    /**
     * 评价日期
     */
    private String date;

    /**
     * 评价日期（格式化）
     */
    @Column(name = "rating_date")
    private Date ratingDate;

    /**
     * 爬取时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 评论是否被取消
     */
    @Column(name = "was_suppressed")
    private Boolean wasSuppressed;

    /**
     * 评论是否有回应
     */
    @Column(name = "has_response")
    private Boolean hasResponse;

    /**
     * 评论唯一标识（主要字段MD5加密获得）
     */
    private String content;

    /**
     * 亚马逊卖家标识
     */
    private String seller;

    /**
     * 亚马逊站点标识
     */
    @Column(name = "marketplace_id")
    private String marketplaceId;

    /**
     * 买家名称
     */
    private String rater;

    /**
     * 评价内容
     */
    @Column(name = "expanded_text")
    private String expandedText;

    /**
     * 评论取消原因
     */
    @Column(name = "suppress_reason_text")
    private String suppressReasonText;

    /**
     * 回应内容
     */
    @Column(name = "response_rating_data")
    private String responseRatingData;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取账号id
     *
     * @return account_id - 账号id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置账号id
     *
     * @param accountId 账号id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取账号名称（店铺）
     *
     * @return account_name - 账号名称（店铺）
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号名称（店铺）
     *
     * @param accountName 账号名称（店铺）
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取站点id
     *
     * @return site_id - 站点id
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * 设置站点id
     *
     * @param siteId 站点id
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * 获取站点
     *
     * @return site - 站点
     */
    public String getSite() {
        return site;
    }

    /**
     * 设置站点
     *
     * @param site 站点
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * 获取评分等级
     *
     * @return rating - 评分等级
     */
    public Byte getRating() {
        return rating;
    }

    /**
     * 设置评分等级
     *
     * @param rating 评分等级
     */
    public void setRating(Byte rating) {
        this.rating = rating;
    }

    /**
     * 获取评价日期
     *
     * @return date - 评价日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置评价日期
     *
     * @param date 评价日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取评价日期（格式化）
     *
     * @return rating_date - 评价日期（格式化）
     */
    public Date getRatingDate() {
        return ratingDate;
    }

    /**
     * 设置评价日期（格式化）
     *
     * @param ratingDate 评价日期（格式化）
     */
    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    /**
     * 获取爬取时间
     *
     * @return create_date - 爬取时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置爬取时间
     *
     * @param createDate 爬取时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取评论是否被取消
     *
     * @return was_suppressed - 评论是否被取消
     */
    public Boolean getWasSuppressed() {
        return wasSuppressed;
    }

    /**
     * 设置评论是否被取消
     *
     * @param wasSuppressed 评论是否被取消
     */
    public void setWasSuppressed(Boolean wasSuppressed) {
        this.wasSuppressed = wasSuppressed;
    }

    /**
     * 获取评论是否有回应
     *
     * @return has_response - 评论是否有回应
     */
    public Boolean getHasResponse() {
        return hasResponse;
    }

    /**
     * 设置评论是否有回应
     *
     * @param hasResponse 评论是否有回应
     */
    public void setHasResponse(Boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    /**
     * 获取评论唯一标识（主要字段MD5加密获得）
     *
     * @return content - 评论唯一标识（主要字段MD5加密获得）
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论唯一标识（主要字段MD5加密获得）
     *
     * @param content 评论唯一标识（主要字段MD5加密获得）
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取亚马逊卖家标识
     *
     * @return seller - 亚马逊卖家标识
     */
    public String getSeller() {
        return seller;
    }

    /**
     * 设置亚马逊卖家标识
     *
     * @param seller 亚马逊卖家标识
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * 获取亚马逊站点标识
     *
     * @return marketplace_id - 亚马逊站点标识
     */
    public String getMarketplaceId() {
        return marketplaceId;
    }

    /**
     * 设置亚马逊站点标识
     *
     * @param marketplaceId 亚马逊站点标识
     */
    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    /**
     * 获取买家名称
     *
     * @return rater - 买家名称
     */
    public String getRater() {
        return rater;
    }

    /**
     * 设置买家名称
     *
     * @param rater 买家名称
     */
    public void setRater(String rater) {
        this.rater = rater;
    }

    /**
     * 获取评价内容
     *
     * @return expanded_text - 评价内容
     */
    public String getExpandedText() {
        return expandedText;
    }

    /**
     * 设置评价内容
     *
     * @param expandedText 评价内容
     */
    public void setExpandedText(String expandedText) {
        this.expandedText = expandedText;
    }

    /**
     * 获取评论取消原因
     *
     * @return suppress_reason_text - 评论取消原因
     */
    public String getSuppressReasonText() {
        return suppressReasonText;
    }

    /**
     * 设置评论取消原因
     *
     * @param suppressReasonText 评论取消原因
     */
    public void setSuppressReasonText(String suppressReasonText) {
        this.suppressReasonText = suppressReasonText;
    }

    /**
     * 获取回应内容
     *
     * @return response_rating_data - 回应内容
     */
    public String getResponseRatingData() {
        return responseRatingData;
    }

    /**
     * 设置回应内容
     *
     * @param responseRatingData 回应内容
     */
    public void setResponseRatingData(String responseRatingData) {
        this.responseRatingData = responseRatingData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedBackDetail that = (FeedBackDetail) o;
        return Objects.equals(rater, that.rater) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(date, that.date) &&
                Objects.equals(content, that.content) &&
                Objects.equals(seller, that.seller) &&
                Objects.equals(marketplaceId, that.marketplaceId) &&
                Objects.equals(expandedText, that.expandedText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rater, rating, date, content, seller, marketplaceId, expandedText);
    }
}