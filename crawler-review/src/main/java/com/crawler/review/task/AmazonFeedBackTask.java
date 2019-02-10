package com.crawler.review.task;

import com.crawler.constant.AmazonSiteEnum;
import com.crawler.framework.agent.DefaultUserAgent;
import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.FormPostRequestManager;
import com.crawler.framework.request.url.PostRequestUrl;
import com.crawler.framework.spider.SpiderEngine;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;
import com.crawler.review.pipeline.feedback.FeedBackDateItemPipline;
import com.crawler.review.pipeline.feedback.FeedBackSaveItemPipline;
import com.crawler.review.service.crawler.FeedBackDetailService;
import com.crawler.review.spider.feedback.FeedBackSpider;
import com.customer.base.entity.AmazonAuth;
import com.customer.base.service.AmazonAuthService;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-29 17:12
 * @description: 亚马逊FEEDBACK爬虫定时任务
 **/
@Component
public class AmazonFeedBackTask {
    private static final Logger logger = LogManager.getLogger(AmazonFeedBackTask.class);

    @Autowired
    @Qualifier("feedBackClient")
    private OkHttpClient client;

    @Autowired
    private AmazonAuthService amazonAuthService;

    @Autowired
    private FeedBackDetailService feedBackDetailService;

    @Scheduled(initialDelay = 1000L, fixedDelay = 5 * 1000L)
    public void crawl() {
        logger.info("=============  FEED_BACK爬虫开始  ==============");
        List<AmazonAuth> amazonAuthList = amazonAuthService.selectFeedBack();

        Date now = new Date();
        for (AmazonAuth amazonAuth : amazonAuthList) {

            // 控制同时只有20只爬虫在爬取
            Integer callsCount = client.dispatcher().runningCallsCount();
            logger.info(String.format("当前FEED_BACK爬虫数量 %s", callsCount));
            if (callsCount >= 20) {
                return;
            }

            // 构造URL对象
            String webSite = AmazonSiteEnum.getWebSiteByKey(amazonAuth.getSite());
            PostRequestUrl postRequestUrl = PostRequestUrl.build(FeedBackSpider.URL, FeedBackSpider.PAGE_NUM_FIELD, FeedBackSpider.PAGE_NUM)
                    .replaceUrlPrefix("[site]", webSite)
                    .addParam("seller", amazonAuth.getSellerId())
                    .addParam("marketplaceID", amazonAuth.getMarketplaceId());

            UserAgent userAgent = new DefaultUserAgent();

            // 构造请求管理器
            FormPostRequestManager formPostRequestManager = new FormPostRequestManager(postRequestUrl, userAgent);

            // ItemPipeline 组件构造
            List<ItemPipline<FeedBackDetail>> itemPiplines = Arrays.asList(
                    new FeedBackDateItemPipline(),
                    new FeedBackSaveItemPipline(feedBackDetailService)
            );

            // 构造爬虫，并将爬虫交给引擎处理
            FeedBackSpider feedBackSpider = new FeedBackSpider(null, null, itemPiplines, formPostRequestManager,
                    client, amazonAuth);
            SpiderEngine<FeedBackDetail> spiderEngine = new SpiderEngine<>(feedBackSpider);
            spiderEngine.submitRequest();

            // 更新评论更新时间
            AmazonAuth updateAmazonAuth = new AmazonAuth();
            updateAmazonAuth.setId(amazonAuth.getId());
            updateAmazonAuth.setFeedBackUpdateDate(now);
            this.amazonAuthService.updateByPrimaryKeySelective(updateAmazonAuth);
        }
    }
}
