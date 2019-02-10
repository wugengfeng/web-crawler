package com.crawler.review.pipeline.feedback;

import com.crawler.constant.AmazonSiteEnum;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;
import com.crawler.util.AmazonSiteUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-23 10:58
 * @description: 店铺评论日期格式处理
 **/
public class FeedBackDateItemPipline extends ItemPipline<FeedBackDetail> {

    @Override
    public List<FeedBackDetail> processItem(SpiderInfo<FeedBackDetail> spiderInfo, List<FeedBackDetail> itemList) {

        if (CollectionUtils.isNotEmpty(itemList)) {

            Date now = new Date();
            String marketplaceId = itemList.get(0).getMarketplaceId();
            String site = AmazonSiteEnum.getKeyByMarketplaceId(marketplaceId);

            itemList.forEach(item -> {
                String date = item.getDate();
                try {
                    item.setRatingDate(AmazonSiteUtil.chooseDateForSite(date, site));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                item.setCreateDate(now);

            });
        }
        return itemList;
    }
}
