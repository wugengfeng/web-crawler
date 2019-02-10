package com.crawler.constant;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 12:10
 * @description: 亚马逊评论等级
 * 1 - 3 星差评
 * 4 - 5 星好评
 **/
public enum AmaoznReviewLevelEnum {
    NO_COMMENT(0.0),
    BAD_REVIEW(3.0),
    PRAISE(4.0);


    private Double star;

    AmaoznReviewLevelEnum(Double star) {
        this.star = star;
    }

    public static String getLevelByStar(Double star) {
        if (star == 0.0) {
            return AmaoznReviewLevelEnum.NO_COMMENT.name();
        }else if (star <= 3.0) {
            return AmaoznReviewLevelEnum.BAD_REVIEW.name();
        } else if (star >= 4.0) {
            return AmaoznReviewLevelEnum.PRAISE.name();
        }
        return null;
    }
}
