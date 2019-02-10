package com.crawler.review.task;

import com.crawler.constant.AmazonSiteEnum;
import com.crawler.constant.Constant;
import com.crawler.constant.CrawlStatusEnum;
import com.crawler.framework.agent.DefaultUserAgent;
import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.GetRequestManager;
import com.crawler.framework.request.RequestManager;
import com.crawler.framework.request.url.GetRequestUrl;
import com.crawler.framework.spider.SpiderEngine;
import com.crawler.review.entity.crawler.review.AmazonReview;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.review.pipeline.review.ReviewDetailDataItemPipeline;
import com.crawler.review.pipeline.review.ReviewDetailSaveItemPipeline;
import com.crawler.review.pipeline.review.ReviewDetailStarItemPipeline;
import com.crawler.review.service.crawler.AmazonReviewDetailsService;
import com.crawler.review.service.crawler.AmazonReviewService;
import com.crawler.review.spider.review.ReviewSpider;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * @program: customer-service
 * @author: wgf
 * @create: 2019-01-17 17:17
 * @description: 亚马逊Review爬虫定时任务
 **/
//@Component
public class AmazonReviewTask {

    private static final Logger logger = LogManager.getLogger(AmazonReviewTask.class);

    @Autowired
    @Qualifier("reviewClient")
    private OkHttpClient client;

    @Autowired
    private AmazonReviewService amazonReviewService;

    @Autowired
    private AmazonReviewDetailsService amazonReviewDetailsService;

    @Scheduled(initialDelay = 1000L, fixedDelay = 5 * 1000L)
    public void crawl() {
        logger.info("=============  REVIEW爬虫开始  ==============");

        List<AmazonReview> reviewList = amazonReviewService.selectAmazonReviewByStatus(Constant.Status.ENABLE);

        for (AmazonReview amazonReview : reviewList) {

            // 控制同时只有20只爬虫在爬取
            logger.info(String.format("当前REVIEW爬虫数量 %s", client.dispatcher().queuedCallsCount()));
            if (client.dispatcher().runningCallsCount() > 20) {
                return;
            }

            // 起始页码为1，如果有中断的则由中断页码开始
            int pageNumber = 1;
            if (CrawlStatusEnum.INTERRUPT.name().equals(amazonReview.getCrawlStatus()) && Objects.nonNull(amazonReview.getInterruptPage())) {
                pageNumber = amazonReview.getInterruptPage();
            }

            GetRequestUrl getRequestUrl = GetRequestUrl.build(ReviewSpider.URL, ReviewSpider.PAGE_NUMBER)
                    .addParam(ReviewSpider.PAGE_NUMBER, pageNumber);


            // 计算页数大小
            Integer pageSize = this.getPageSize(amazonReview);
            String site = AmazonSiteEnum.getWebSiteByKey(amazonReview.getAmazonSite());
            getRequestUrl.replaceUrlPrefix("[site]", site)
                    .replaceUrlPrefix("[asin]", amazonReview.getAsin())
                    .replaceUrlPrefix("[size]", pageSize.toString());


            UserAgent userAgent = new DefaultUserAgent();
            RequestManager requestManager = new GetRequestManager(getRequestUrl, userAgent);

            // 爬虫字典构造
            Map<String, Object> dict = new HashMap<>();
            dict.put("site", site);
            dict.put("asin", amazonReview.getAsin());
            dict.put(ReviewSpider.PAGE_SIZE, pageSize);

            // ItemPipeline 组件构造
            List<ItemPipline<AmazonReviewDetails>> itemPiplineList = Arrays.asList(
                    new ReviewDetailStarItemPipeline(),
                    new ReviewDetailDataItemPipeline(),
                    new ReviewDetailSaveItemPipeline(amazonReviewDetailsService)
            );

            // 将爬虫提交给引擎开始爬取
            ReviewSpider reviewSpider = new ReviewSpider(null, dict, itemPiplineList, requestManager, client, amazonReview,
                    amazonReviewService, amazonReviewDetailsService);
            SpiderEngine<AmazonReviewDetails> spiderEngine = new SpiderEngine<>(reviewSpider);
            spiderEngine.submitRequest();

            // 修改更新时间
            amazonReview.setUpdateDate(new Date());
            amazonReviewService.updateByPrimaryKeySelective(amazonReview);
        }
    }

    /**
     * 亚马逊每个站点的pageSize的限制都不同，为了最大速度及稳定爬取
     * 这里根据不同站点的评论总数计算出合理的pageSize
     * @return
     */
    private Integer getPageSize(AmazonReview amazonReview) {
        String site = amazonReview.getAmazonSite();
        Integer maxPageSize = AmazonSiteEnum.getMaxPageSizeByKey(site);
        Integer minPageSize = AmazonSiteEnum.getMixPageSizeByKey(site);
        Integer result = minPageSize;

        // 如果有评论总数
        if (Objects.nonNull(amazonReview.getAllTotal())) {
            if (amazonReview.getAllTotal() > maxPageSize) {
                result = maxPageSize;
            }
        }
        return result;
    }
}
