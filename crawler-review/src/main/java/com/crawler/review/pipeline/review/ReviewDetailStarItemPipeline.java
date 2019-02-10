package com.crawler.review.pipeline.review;

import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.util.CrawlerUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 17:24
 * @description: 亚马逊Review星级处理
 **/
public class ReviewDetailStarItemPipeline extends ItemPipline<AmazonReviewDetails> {

    @Override
    public List<AmazonReviewDetails> processItem(SpiderInfo<AmazonReviewDetails> spiderInfo, List<AmazonReviewDetails> itemList) {

        // 根据className判断评论星级
        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.forEach(item -> {
                Set<String> classList = item.getClassList();
                if (CollectionUtils.isNotEmpty(classList)) {
                    String reviewStar = CrawlerUtil.chooseReviewDetailStar(classList);
                    item.setReviewStar(Integer.parseInt(reviewStar));
                }
            });
        }

        return itemList;
    }
}
