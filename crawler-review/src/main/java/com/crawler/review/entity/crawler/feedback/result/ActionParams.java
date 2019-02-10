package com.crawler.review.entity.crawler.feedback.result;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-18 18:26
 * @description:
 **/
public class ActionParams {
    private String seller;

    private String marketplaceID;

    private String url;

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSeller() {
        return this.seller;
    }

    public void setMarketplaceID(String marketplaceID) {
        this.marketplaceID = marketplaceID;
    }

    public String getMarketplaceID() {
        return this.marketplaceID;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

}