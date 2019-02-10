package com.crawler.review.pipeline.feedback;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;
import com.crawler.review.service.crawler.FeedBackDetailService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: customer-service
 * @author: wgf
 * @create: 2019-01-23 18:32
 * @description: FeedBack持久化ItemPipline
 **/
public class FeedBackSaveItemPipline extends ItemPipline<FeedBackDetail> {
    private FeedBackDetailService feedBackDetailService;

    public FeedBackSaveItemPipline(FeedBackDetailService feedBackDetailService) {
        this.feedBackDetailService = feedBackDetailService;
    }

    @Override
    public List<FeedBackDetail> processItem(SpiderInfo<FeedBackDetail> spiderInfo, List<FeedBackDetail> itemList) {

        // 删除评论
        if (CollectionUtils.isNotEmpty(itemList)) {
            List<String> contentList = itemList.stream()
                    .map(FeedBackDetail::getContent)
                    .collect(Collectors.toList());

            this.feedBackDetailService.deleteFeedBackByContent(contentList);
        }

        // 新增评论
        this.feedBackDetailService.insertList(itemList, null);
        return itemList;
    }
}
