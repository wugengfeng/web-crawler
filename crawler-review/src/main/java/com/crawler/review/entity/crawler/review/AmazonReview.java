package com.crawler.review.entity.crawler.review;

import javax.persistence.*;
import java.util.Date;

@Table(name = "amazon_review")
public class AmazonReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * listing_id
     */
    @Column(name = "listing_id")
    private Integer listingId;

    /**
     * asin码
     */
    private String asin;

    /**
     * 亚马逊账号id
     */
    @Column(name = "amazon_id")
    private Integer amazonId;

    /**
     * 亚马逊账号(店铺)
     */
    @Column(name = "amazon_name")
    private String amazonName;

    /**
     * 站点id
     */
    @Column(name = "amazon_site_id")
    private Integer amazonSiteId;

    /**
     * 亚马逊站点
     */
    @Column(name = "amazon_site")
    private String amazonSite;

    /**
     * 评定星级
     */
    @Column(name = "rating_star")
    private Double ratingStar;

    /**
     * 评论总数
     */
    @Column(name = "all_total")
    private Integer allTotal;

    /**
     * 状态
     * 启用：ENABLE
     * 禁用：DISABLE
     */
    @Column(name = "review_status")
    private String reviewStatus;

    /**
     * 爬取状态
     * CRAWL：已爬取完毕
     * INTERRUPT ：中断爬取
     * NON_CRAWL：未开始爬取
     */
    @Column(name = "crawl_status")
    private String crawlStatus;

    @Column(name = "interrupt_page")
    private Integer interruptPage;

    /**
     * 1到3星是差评：BAD_REVIEW
     * 4到5星是好评：PRAISE
     */
    @Column(name = "rating_good")
    private String ratingGood;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 更新时间(爬取时间)
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 商品图片链接
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 五星占比
     */
    @Column(name = "total_5star")
    private Double total5star;

    /**
     * 四星占比
     */
    @Column(name = "total_4star")
    private Double total4star;

    /**
     * 三星占比
     */
    @Column(name = "total_3star")
    private Double total3star;

    /**
     * 二星占比
     */
    @Column(name = "total_2star")
    private Double total2star;

    /**
     * 一星占比
     */
    @Column(name = "total_1star")
    private Double total1star;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

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
     * 获取listing_id
     *
     * @return listing_id - listing_id
     */
    public Integer getListingId() {
        return listingId;
    }

    /**
     * 设置listing_id
     *
     * @param listingId listing_id
     */
    public void setListingId(Integer listingId) {
        this.listingId = listingId;
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
     * 获取亚马逊账号id
     *
     * @return amazon_id - 亚马逊账号id
     */
    public Integer getAmazonId() {
        return amazonId;
    }

    /**
     * 设置亚马逊账号id
     *
     * @param amazonId 亚马逊账号id
     */
    public void setAmazonId(Integer amazonId) {
        this.amazonId = amazonId;
    }

    /**
     * 获取亚马逊账号(店铺)
     *
     * @return amazon_name - 亚马逊账号(店铺)
     */
    public String getAmazonName() {
        return amazonName;
    }

    /**
     * 设置亚马逊账号(店铺)
     *
     * @param amazonName 亚马逊账号(店铺)
     */
    public void setAmazonName(String amazonName) {
        this.amazonName = amazonName;
    }

    /**
     * 获取站点id
     *
     * @return amazon_site_id - 站点id
     */
    public Integer getAmazonSiteId() {
        return amazonSiteId;
    }

    /**
     * 设置站点id
     *
     * @param amazonSiteId 站点id
     */
    public void setAmazonSiteId(Integer amazonSiteId) {
        this.amazonSiteId = amazonSiteId;
    }

    /**
     * 获取亚马逊站点
     *
     * @return amazon_site - 亚马逊站点
     */
    public String getAmazonSite() {
        return amazonSite;
    }

    /**
     * 设置亚马逊站点
     *
     * @param amazonSite 亚马逊站点
     */
    public void setAmazonSite(String amazonSite) {
        this.amazonSite = amazonSite;
    }

    /**
     * 获取评定星级
     *
     * @return rating_star - 评定星级
     */
    public Double getRatingStar() {
        return ratingStar;
    }

    /**
     * 设置评定星级
     *
     * @param ratingStar 评定星级
     */
    public void setRatingStar(Double ratingStar) {
        this.ratingStar = ratingStar;
    }

    /**
     * 获取评论总数
     *
     * @return all_total - 评论总数
     */
    public Integer getAllTotal() {
        return allTotal;
    }

    /**
     * 设置评论总数
     *
     * @param allTotal 评论总数
     */
    public void setAllTotal(Integer allTotal) {
        this.allTotal = allTotal;
    }

    /**
     * 获取状态
     * 启用：ENABLE
     * 禁用：DISABLE
     *
     * @return review_status - 状态
     * 启用：ENABLE
     * 禁用：DISABLE
     */
    public String getReviewStatus() {
        return reviewStatus;
    }

    /**
     * 设置状态
     * 启用：ENABLE
     * 禁用：DISABLE
     *
     * @param reviewStatus 状态
     *                     启用：ENABLE
     *                     禁用：DISABLE
     */
    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    /**
     * 获取爬取状态
     * crawl：已开始爬取
     * non_crawl：未开始爬取
     *
     * @return crawl_status - 爬取状态
     * crawl：已开始爬取
     * non_crawl：未开始爬取
     */
    public String getCrawlStatus() {
        return crawlStatus;
    }

    /**
     * 设置爬取状态
     * crawl：已开始爬取
     * non_crawl：未开始爬取
     *
     * @param crawlStatus 爬取状态
     *                    crawl：已开始爬取
     *                    non_crawl：未开始爬取
     */
    public void setCrawlStatus(String crawlStatus) {
        this.crawlStatus = crawlStatus;
    }

    /**
     * 获取1到3星是差评：BAD_REVIEW
     * 4到5星是好评：PRAISE
     *
     * @return rating_good - 1到3星是差评：BAD_REVIEW
     * 4到5星是好评：PRAISE
     */
    public String getRatingGood() {
        return ratingGood;
    }

    /**
     * 设置1到3星是差评：BAD_REVIEW
     * 4到5星是好评：PRAISE
     *
     * @param ratingGood 1到3星是差评：BAD_REVIEW
     *                   4到5星是好评：PRAISE
     */
    public void setRatingGood(String ratingGood) {
        this.ratingGood = ratingGood;
    }

    /**
     * 获取创建时间
     *
     * @return created_date - 创建时间
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 设置创建时间
     *
     * @param createdDate 创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取更新时间(爬取时间)
     *
     * @return update_date - 更新时间(爬取时间)
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间(爬取时间)
     *
     * @param updateDate 更新时间(爬取时间)
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取商品图片链接
     *
     * @return image_url - 商品图片链接
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置商品图片链接
     *
     * @param imageUrl 商品图片链接
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取五星占比
     *
     * @return total_5star - 五星占比
     */
    public Double getTotal5star() {
        return total5star;
    }

    /**
     * 设置五星占比
     *
     * @param total5star 五星占比
     */
    public void setTotal5star(Double total5star) {
        this.total5star = total5star;
    }

    /**
     * 获取四星占比
     *
     * @return total_4star - 四星占比
     */
    public Double getTotal4star() {
        return total4star;
    }

    /**
     * 设置四星占比
     *
     * @param total4star 四星占比
     */
    public void setTotal4star(Double total4star) {
        this.total4star = total4star;
    }

    /**
     * 获取三星占比
     *
     * @return total_3star - 三星占比
     */
    public Double getTotal3star() {
        return total3star;
    }

    /**
     * 设置三星占比
     *
     * @param total3star 三星占比
     */
    public void setTotal3star(Double total3star) {
        this.total3star = total3star;
    }

    /**
     * 获取二星占比
     *
     * @return total_2star - 二星占比
     */
    public Double getTotal2star() {
        return total2star;
    }

    /**
     * 设置二星占比
     *
     * @param total2star 二星占比
     */
    public void setTotal2star(Double total2star) {
        this.total2star = total2star;
    }

    /**
     * 获取一星占比
     *
     * @return total_1star - 一星占比
     */
    public Double getTotal1star() {
        return total1star;
    }

    /**
     * 设置一星占比
     *
     * @param total1star 一星占比
     */
    public void setTotal1star(Double total1star) {
        this.total1star = total1star;
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInterruptPage() {
        return interruptPage;
    }

    public void setInterruptPage(Integer interruptPage) {
        this.interruptPage = interruptPage;
    }
}