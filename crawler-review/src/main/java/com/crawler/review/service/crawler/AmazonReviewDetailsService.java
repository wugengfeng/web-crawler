package com.crawler.review.service.crawler;

import com.crawler.common.service.BaseService;
import com.crawler.review.entity.crawler.review.AmazonReview;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 15:46
 * @description:
 **/
public interface AmazonReviewDetailsService extends BaseService<AmazonReviewDetails> {

    /**
     * 判断当前爬取的商品评论是否被爬取过
     *
     * @param list
     * @param amazonReview
     * @return 返回未被爬取的评论
     */
    List<AmazonReviewDetails> checkAmazonReviewDetails(List<AmazonReviewDetails> list, AmazonReview amazonReview);
}
