package com.crawler.review.pipeline.review;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.util.UnicodeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-16 18:10
 * @description: 亚马逊用户品论详情数据处理管道
 **/
public class ReviewDetailDataItemPipeline extends ItemPipline<AmazonReviewDetails> {

    @Override
    public List<AmazonReviewDetails> processItem(SpiderInfo<AmazonReviewDetails> spiderInfo, List<AmazonReviewDetails> itemList) {

        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.forEach(item -> {

                // 提取ProfileId
                this.extractEncryptProfileId(item);

                // 数据转Unicode
                item.setReviewer(UnicodeUtil.convert(item.getReviewer()));
                item.setReviewConent(UnicodeUtil.convert(item.getReviewConent()));
                item.setReviewTitle(UnicodeUtil.convert(item.getReviewTitle()));
            });
        }

        return itemList;
    }

    /**
     * 根据messageUrl提取被亚马逊加密的ProfileId
     *
     * @param details
     * @return
     */
    private void extractEncryptProfileId(AmazonReviewDetails details) {
        String messageUrl = details.getMessageUrl();
        String encryptProfileId = null;

        // gp/profile/amzn1.account.AGVD4GCY2FRRXPBUGH3DUF3GW65A/ref=cm_cr_arp_d_gw_lft?ie=UTF8
        if (StringUtils.isNotBlank(messageUrl)) {
            encryptProfileId = messageUrl.split("profile\\/")[1].split("\\/ref")[0];
        }

        details.setEncryptProfileId(encryptProfileId);
    }
}
