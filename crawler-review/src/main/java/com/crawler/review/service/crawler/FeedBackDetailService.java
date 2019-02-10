package com.crawler.review.service.crawler;

import com.crawler.common.service.BaseService;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-22 15:15
 * @description:
 **/
public interface FeedBackDetailService extends BaseService<FeedBackDetail> {

    /**
     * 根据唯一标识判断当前爬取的FeedBack是否被爬取过
     * @param feedBackDetailList
     * @return 没有被爬取过的FeedBack
     */
    List<FeedBackDetail> checkFeedBackDetail(List<FeedBackDetail> feedBackDetailList);

    /**
     * 根据摘要删除评论
     * @param contentList
     */
    void deleteFeedBackByContent(List<String> contentList);
}
