package crawler.spider.demo.jd.entity;

import com.alibaba.fastjson.JSON;

/**
 * 京东商品数据爬虫解析实体
 */
public class JDInfo {
    private String sku;
    private Double price;
    private String productName;
    private String imgUrl;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
