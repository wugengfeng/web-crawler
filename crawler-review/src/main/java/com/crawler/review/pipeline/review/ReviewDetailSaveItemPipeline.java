package com.crawler.review.pipeline.review;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.review.service.crawler.AmazonReviewDetailsService;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 17:24
 * @description: Review数据存储组件
 **/
public class ReviewDetailSaveItemPipeline extends ItemPipline<AmazonReviewDetails> {

    public ReviewDetailSaveItemPipeline(AmazonReviewDetailsService amazonReviewDetailsService) {
        this.amazonReviewDetailsService = amazonReviewDetailsService;
    }

    private AmazonReviewDetailsService amazonReviewDetailsService;

    @Override
    public List<AmazonReviewDetails> processItem(SpiderInfo<AmazonReviewDetails> spiderInfo, List<AmazonReviewDetails> itemList) {

        amazonReviewDetailsService.insertList(itemList, null);
        return itemList;
    }
}
