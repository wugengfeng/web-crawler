package com.crawler.review.spider.feedback;

import com.alibaba.fastjson.JSON;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.RequestManager;
import com.crawler.framework.spider.Spider;
import com.crawler.framework.spider.SpiderEngine;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.framework.spider.SpiderParse;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;
import com.crawler.review.entity.crawler.feedback.result.FeedBackResult;
import com.crawler.util.UnicodeUtil;
import com.customer.base.entity.AmazonAuth;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: customer-service
 * @author: wgf
 * @create: 2019-01-18 15:24
 * @description: 亚马逊FeedBack爬虫
 **/
public class FeedBackSpider extends Spider<FeedBackDetail> {

    private static final Logger logger = LogManager.getLogger(FeedBackSpider.class);

    // 爬取字段
    public static final String URL = "https://[site]/sp/ajax/feedback";

    // 分页字段
    public static final String PAGE_NUM_FIELD = "pageNumber";

    // 每页评论条数
    public static final Integer PAGE_SIZE = 5;

    // 起始页
    public static final Integer PAGE_NUM = 0;

    private AmazonAuth amazonAuth;

    public FeedBackSpider(List<String> allowedDomains, Map<String, Object> dict, List<ItemPipline<FeedBackDetail>> itemPiplines, RequestManager requestManager, OkHttpClient client,
                          AmazonAuth amazonAuth) {
        super(allowedDomains, dict, itemPiplines, requestManager, client);
        this.amazonAuth = amazonAuth;
    }

    @Override
    public SpiderParse<FeedBackDetail> parse(Response response, Call call, SpiderInfo<FeedBackDetail> spiderInfo, SpiderEngine<FeedBackDetail> spiderEngine) throws IOException {
        SpiderParse<FeedBackDetail> spiderParse = null;

        // 当前页码
        logger.info(this.requestManager.getRequestUrl().getPageNum());

        int code = response.code();
        if (code == 404) {
            // 店铺不存在
        } else if (code == 301 || code == 302) {
            // URL被重定向
            logger.error(String.format("URL[ %s ]被重定向为[ %s ]", this.requestManager.getRequestUrl().getUrl(), response.request().url().toString()));

            // 在head里标识是重定向的
            Map<String, String> head = new HashMap<>();
            head.put(REDIRECT, "Y");

            // 重新请求
            Request request = this.requestManager.getRequest(head);
            super.client.newCall(request).enqueue(spiderEngine);

            // 继续爬
            Request nextRequest = this.requestManager.getNextPageRequest();
            return new SpiderParse<>(null, nextRequest);

        } else if (code == 200) {

            String resultJson = response.body().string();
            FeedBackResult feedBackResult = JSON.parseObject(resultJson, FeedBackResult.class);

            if (Objects.isNull(feedBackResult) || feedBackResult.getIsEmpty()) {
                // 店铺没有评论直接停止爬虫
                return spiderParse;
            }

            if (CollectionUtils.isNotEmpty(feedBackResult.getDetails())) {
                List<FeedBackDetail> detailList = feedBackResult.getDetails().stream()
                        .map(item -> {
                            FeedBackDetail feedBackDetail = new FeedBackDetail();
                            feedBackDetail.setAccountId(amazonAuth.getAccountId());
                            feedBackDetail.setAccountName(amazonAuth.getEnglishName());
                            feedBackDetail.setSiteId(amazonAuth.getSiteId());
                            feedBackDetail.setSite(amazonAuth.getSite());
                            feedBackDetail.setRating(item.getRating());
                            feedBackDetail.setDate(item.getRatingData().getDate());
                            feedBackDetail.setWasSuppressed(item.getRatingData().getWasSuppressed());
                            feedBackDetail.setResponseRatingData(item.getResponseRatingData());
                            feedBackDetail.setHasResponse(item.getHasResponse());
                            feedBackDetail.setSeller(feedBackResult.getActionParams().getSeller());
                            feedBackDetail.setMarketplaceId(feedBackResult.getActionParams().getMarketplaceID());

                            if (StringUtils.isNotBlank(item.getRater())) {
                                feedBackDetail.setRater(UnicodeUtil.convert(item.getRater()));
                            }

                            if (StringUtils.isNotBlank(item.getRatingData().getText().getExpandedText())) {
                                feedBackDetail.setExpandedText(UnicodeUtil.convert(item.getRatingData().getText().getExpandedText()));
                            }

                            if (StringUtils.isNotBlank(item.getRatingData().getSuppressReasonText())) {
                                feedBackDetail.setSuppressReasonText(UnicodeUtil.convert(item.getRatingData().getSuppressReasonText()));
                            }

                            // 摘要内容 = 评论用户 + 评论内容 + 评论时间 + 评分等级 + seller + marketplaceId
                            String content = feedBackDetail.getRater() + feedBackDetail.getExpandedText() + feedBackDetail.getDate() + feedBackDetail.getRating()
                                    + feedBackDetail.getSeller() + feedBackDetail.getMarketplaceId();
                            feedBackDetail.setContent(DigestUtils.md5DigestAsHex(content.getBytes()));

                            return feedBackDetail;
                        })
                        .collect(Collectors.toList());

                String isRedirect = response.request().header(REDIRECT);

                if (PAGE_SIZE > detailList.size() || !feedBackResult.getHasNextPage() || StringUtils.isNotBlank(isRedirect)) {
                    // 本页是最后一页或者是处理重定向的回调，结束爬虫
                    spiderParse = new SpiderParse<>(detailList, null);
                    logger.info(String.format("FEEDBACL爬虫爬取完毕, 账号：[%s]   站点：[%s]   页码：[%s]", amazonAuth.getEnglishName(),
                            amazonAuth.getSite(), this.requestManager.getRequestUrl().getPageNum()));
                } else if (feedBackResult.getHasNextPage()) {
                    // 还有下一页
                    spiderParse = new SpiderParse<>(detailList, this.requestManager.getNextPageRequest());
                }
            }

        } else {
            logger.error(String.format("REVIEW 爬虫错误, HTTP CODE: %s  , 店铺：[%s] 站点：[%s]",
                    code, amazonAuth.getEnglishName(), amazonAuth.getSite()));
        }

        return spiderParse;
    }

    @Override
    public void reTry(Call call, IOException e) {
        System.out.println("代理连接不上");
    }
}
