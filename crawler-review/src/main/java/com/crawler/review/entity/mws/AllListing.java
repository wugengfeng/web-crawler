package com.crawler.review.entity.mws;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "all_listing")
public class AllListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 大区id
     */
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 站点id
     */
    @Column(name = "site_id")
    private Integer siteId;

    /**
     * 账号id
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 亚马逊sku
     */
    @Column(name = "seller_sku")
    private String sellerSku;

    /**
     * 销售编码
     */
    private String asin;

    private Boolean parent;

    @Column(name = "parent_asin")
    private String parentAsin;

    @Column(name = "listing_id")
    private String listingId;

    @Column(name = "open_date")
    private Date openDate;

    private BigDecimal price;

    @Column(name = "product_id")
    private String productId;

    private Integer quantity;

    private String status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 大区名称
     */
    private String area;

    /**
     * 账号名称
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 站点名称
     */
    @Column(name = "site_name")
    private String siteName;

    /**
     * mws任务id
     */
    @Column(name = "process_id")
    private Integer processId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "image_url")
    private String imageUrl;

    private String remark;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取大区id
     *
     * @return area_id - 大区id
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置大区id
     *
     * @param areaId 大区id
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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
     * 获取亚马逊sku
     *
     * @return seller_sku - 亚马逊sku
     */
    public String getSellerSku() {
        return sellerSku;
    }

    /**
     * 设置亚马逊sku
     *
     * @param sellerSku 亚马逊sku
     */
    public void setSellerSku(String sellerSku) {
        this.sellerSku = sellerSku;
    }

    /**
     * 获取销售编码
     *
     * @return asin - 销售编码
     */
    public String getAsin() {
        return asin;
    }

    /**
     * 设置销售编码
     *
     * @param asin 销售编码
     */
    public void setAsin(String asin) {
        this.asin = asin;
    }

    /**
     * @return parent
     */
    public Boolean getParent() {
        return parent;
    }

    /**
     * @param parent
     */
    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    /**
     * @return parent_asin
     */
    public String getParentAsin() {
        return parentAsin;
    }

    /**
     * @param parentAsin
     */
    public void setParentAsin(String parentAsin) {
        this.parentAsin = parentAsin;
    }

    /**
     * @return listing_id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * @param listingId
     */
    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    /**
     * @return open_date
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return product_id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取大区名称
     *
     * @return area - 大区名称
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置大区名称
     *
     * @param area 大区名称
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取账号名称
     *
     * @return account_name - 账号名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号名称
     *
     * @param accountName 账号名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取站点名称
     *
     * @return site_name - 站点名称
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * 设置站点名称
     *
     * @param siteName 站点名称
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * 获取mws任务id
     *
     * @return process_id - mws任务id
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * 设置mws任务id
     *
     * @param processId mws任务id
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    /**
     * @return item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return image_url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}