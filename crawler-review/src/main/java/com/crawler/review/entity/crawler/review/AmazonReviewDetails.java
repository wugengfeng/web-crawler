package com.crawler.review.entity.crawler.review;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Table(name = "amazon_review_details")
public class AmazonReviewDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 主表id
     */
    @Column(name = "reviewer_id")
    private Integer reviewerId;

    /**
     * 账号id
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 亚马逊店铺
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * asin码
     */
    private String asin;

    /**
     * 站点
     */
    private String site;

    /**
     * 亚马逊品论id
     */
    @Column(name = "amazon_uuid")
    private String amazonUuid;

    /**
     * 评星
     */
    @Column(name = "review_star")
    private Integer reviewStar;

    /**
     * 评论日期
     */
    @Column(name = "review_date")
    private String reviewDate;

    /**
     * 是否购买
     */
    private String purchase;

    /**
     * 标准时间
     */
    @Column(name = "standard_date")
    private Date standardDate;

    /**
     * 最佳评论等级
     */
    @Column(name = "top_reviewer_level")
    private String topReviewerLevel;

    /**
     * 有用评论数
     */
    @Column(name = "usefull_num")
    private Integer usefullNum;

    /**
     * 亚马逊加密的用户标识
     */
    @Column(name = "encrypt_profile_id")
    private String encryptProfileId;

    /**
     * 评论标题
     */
    @Column(name = "review_title")
    private String reviewTitle;

    /**
     * 评论内容
     */
    @Column(name = "review_conent")
    private String reviewConent;

    @Transient
    private String asinUrl;

    @Transient
    private String messageUrl;

    @Transient
    private Set<String> ClassList;

    @Transient
    private String imageUrl;

    /**
     * 评论者
     */
    private String reviewer;

    @Column(name = "create_date")
    private Date createDate;

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
     * 获取主表id
     *
     * @return reviewer_id - 主表id
     */
    public Integer getReviewerId() {
        return reviewerId;
    }

    /**
     * 设置主表id
     *
     * @param reviewerId 主表id
     */
    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取asin码
     *
     * @return asin - asin码
     */
    public String getAsin() {
        return asin;
    }

    /**
     * 设置asin码
     *
     * @param asin asin码
     */
    public void setAsin(String asin) {
        this.asin = asin;
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
     * 获取亚马逊品论id
     *
     * @return amazon_uuid - 亚马逊品论id
     */
    public String getAmazonUuid() {
        return amazonUuid;
    }

    /**
     * 设置亚马逊品论id
     *
     * @param amazonUuid 亚马逊品论id
     */
    public void setAmazonUuid(String amazonUuid) {
        this.amazonUuid = amazonUuid;
    }

    /**
     * 获取评星
     *
     * @return review_star - 评星
     */
    public Integer getReviewStar() {
        return reviewStar;
    }

    /**
     * 设置评星
     *
     * @param reviewStar 评星
     */
    public void setReviewStar(Integer reviewStar) {
        this.reviewStar = reviewStar;
    }

    /**
     * 获取评论日期
     *
     * @return review_date - 评论日期
     */
    public String getReviewDate() {
        return reviewDate;
    }

    /**
     * 设置评论日期
     *
     * @param reviewDate 评论日期
     */
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * 获取是否购买
     *
     * @return purchase - 是否购买
     */
    public String getPurchase() {
        return purchase;
    }

    /**
     * 设置是否购买
     *
     * @param purchase 是否购买
     */
    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    /**
     * 获取标准时间
     *
     * @return standard_date - 标准时间
     */
    public Date getStandardDate() {
        return standardDate;
    }

    /**
     * 设置标准时间
     *
     * @param standardDate 标准时间
     */
    public void setStandardDate(Date standardDate) {
        this.standardDate = standardDate;
    }

    /**
     * 获取最佳评论等级
     *
     * @return top_reviewer_level - 最佳评论等级
     */
    public String getTopReviewerLevel() {
        return topReviewerLevel;
    }

    /**
     * 设置最佳评论等级
     *
     * @param topReviewerLevel 最佳评论等级
     */
    public void setTopReviewerLevel(String topReviewerLevel) {
        this.topReviewerLevel = topReviewerLevel;
    }

    /**
     * 获取有用评论数
     *
     * @return usefull_num - 有用评论数
     */
    public Integer getUsefullNum() {
        return usefullNum;
    }

    /**
     * 设置有用评论数
     *
     * @param usefullNum 有用评论数
     */
    public void setUsefullNum(Integer usefullNum) {
        this.usefullNum = usefullNum;
    }

    /**
     * 获取亚马逊加密的用户标识
     *
     * @return encrypt_profile_id - 亚马逊加密的用户标识
     */
    public String getEncryptProfileId() {
        return encryptProfileId;
    }

    /**
     * 设置亚马逊加密的用户标识
     *
     * @param encryptProfileId 亚马逊加密的用户标识
     */
    public void setEncryptProfileId(String encryptProfileId) {
        this.encryptProfileId = encryptProfileId;
    }

    /**
     * 获取评论标题
     *
     * @return review_title - 评论标题
     */
    public String getReviewTitle() {
        return reviewTitle;
    }

    /**
     * 设置评论标题
     *
     * @param reviewTitle 评论标题
     */
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    /**
     * 获取评论内容
     *
     * @return review_conent - 评论内容
     */
    public String getReviewConent() {
        return reviewConent;
    }

    /**
     * 设置评论内容
     *
     * @param reviewConent 评论内容
     */
    public void setReviewConent(String reviewConent) {
        this.reviewConent = reviewConent;
    }

    /**
     * 获取评论者
     *
     * @return reviewer - 评论者
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * 设置评论者
     *
     * @param reviewer 评论者
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getAsinUrl() {
        return asinUrl;
    }

    public void setAsinUrl(String asinUrl) {
        this.asinUrl = asinUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public Set<String> getClassList() {
        return ClassList;
    }

    public void setClassList(Set<String> classList) {
        ClassList = classList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmazonReviewDetails that = (AmazonReviewDetails) o;
        return Objects.equals(accountName, that.accountName) &&
                Objects.equals(asin, that.asin) &&
                Objects.equals(site, that.site) &&
                Objects.equals(amazonUuid, that.amazonUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, asin, site, amazonUuid);
    }
}