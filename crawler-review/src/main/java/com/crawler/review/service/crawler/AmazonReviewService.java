package com.crawler.review.service.crawler;

import com.crawler.common.service.BaseService;
import com.crawler.review.entity.crawler.review.AmazonReview;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 15:43
 * @description:
 **/
public interface AmazonReviewService extends BaseService<AmazonReview> {

    /**
     * 根据状态检索数据，并将结果按照更新时间升序
     * @param status
     * @return
     */
    List<AmazonReview> selectAmazonReviewByStatus(String status);
}
