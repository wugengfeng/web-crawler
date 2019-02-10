package com.crawler.review.entity.crawler.feedback.result;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-18 18:24
 * @description:
 **/
public class Details {

    /**
     * 是否为空
     */
    private boolean isEmpty;

    /**
     * 评价人
     */
    private String rater;

    /**
     * 评分
     */
    private Byte rating;

    /**
     * 评价数据
     */
    private RatingData ratingData;

    /**
     * 回应评价
     */
    private String responseRatingData;

    /**
     * 是否有回应
     */
    private boolean hasResponse;

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }

    public String getRater() {
        return this.rater;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public Byte getRating() {
        return this.rating;
    }

    public void setRatingData(RatingData ratingData) {
        this.ratingData = ratingData;
    }

    public RatingData getRatingData() {
        return this.ratingData;
    }

    public void setResponseRatingData(String responseRatingData) {
        this.responseRatingData = responseRatingData;
    }

    public String getResponseRatingData() {
        return this.responseRatingData;
    }

    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    public boolean getHasResponse() {
        return this.hasResponse;
    }
}
