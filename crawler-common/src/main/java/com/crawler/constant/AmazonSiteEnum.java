package com.crawler.constant;

import java.util.Arrays;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-17 17:31
 * @description:
 *
 **/
public enum AmazonSiteEnum {
    MX("www.amazon.com.mx", "A1AM78C64UM0Y8", "墨西哥", 50, 19),
    ES("www.amazon.es", "A1RKKUPIHCS9HS", "西班牙", 50, 19),
    UK("www.amazon.co.uk", "A1F83G8C2ARO7P", "英国", 19, 10),
    US("www.amazon.com", "ATVPDKIKX0DER", "美国", 50, 19),
    FR("www.amazon.fr", "A13V1IB3VIYZZH", "法国", 50, 19),
    JP("www.amazon.co.jp", "A1VC38T7YXB528", "日本", 19, 10),
    IT("www.amazon.it", "APJ6JRA9NG5V4", "意大利", 50, 19),
    DE("www.amazon.de", "A1PA6795UKMFR9", "德国", 50, 19),
    CA("www.amazon.ca", "A2EUQ1WTGCTBG2", "加拿大", 19, 10);

    private String webSite;
    private String marketplaceId;
    private String zhName;
    private Integer maxPageSize;
    private Integer minPageSize;

    AmazonSiteEnum(String webSite, String marketplaceId, String zhName, Integer maxPageSize, Integer minPageSize) {
        this.webSite = webSite;
        this.marketplaceId = marketplaceId;
        this.zhName = zhName;
        this.maxPageSize = maxPageSize;
        this.minPageSize = minPageSize;
    }

    public static String getWebSiteByKey(String key) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> key.equals(item.name()))
                .findAny()
                .map(AmazonSiteEnum::getWebSite)
                .orElse(null);

    }

    public static String getMarketplaceIdByKey(String key) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> key.equals(item.name()))
                .findAny()
                .map(AmazonSiteEnum::getMarketplaceId)
                .orElse(null);

    }

    public static String getZhNameByKey(String key) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> key.equals(item.name()))
                .findAny()
                .map(AmazonSiteEnum::getZhName)
                .orElse(null);

    }

    public static String getKeyByMarketplaceId(String marketplaceId) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> marketplaceId.equals(item.marketplaceId))
                .findAny()
                .map(AmazonSiteEnum::name)
                .orElse(null);

    }

    public static Integer getMaxPageSizeByKey(String key) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> key.equals(item.name()))
                .findAny()
                .map(AmazonSiteEnum::getMaxPageSize)
                .orElse(null);
    }

    public static Integer getMixPageSizeByKey(String key) {
        return Arrays.stream(AmazonSiteEnum.values())
                .filter(item -> key.equals(item.name()))
                .findAny()
                .map(AmazonSiteEnum::getMinPageSize)
                .orElse(null);
    }

    public String getWebSite() {
        return webSite;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public String getZhName() {
        return zhName;
    }

    public Integer getMaxPageSize() {
        return maxPageSize;
    }

    public Integer getMinPageSize() {
        return minPageSize;
    }
}
