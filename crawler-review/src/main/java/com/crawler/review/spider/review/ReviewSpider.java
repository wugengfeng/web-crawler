package com.crawler.review.spider.review;

import com.crawler.constant.AmaoznReviewLevelEnum;
import com.crawler.constant.CrawlStatusEnum;
import com.crawler.framework.exception.SpiderCatchException;
import com.crawler.framework.exception.SpiderOverException;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.RequestManager;
import com.crawler.framework.spider.Spider;
import com.crawler.framework.spider.SpiderEngine;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.framework.spider.SpiderParse;
import com.crawler.review.entity.crawler.review.AmazonReview;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.review.service.crawler.AmazonReviewDetailsService;
import com.crawler.review.service.crawler.AmazonReviewService;
import com.crawler.util.AmazonSiteUtil;
import com.crawler.util.CrawlerUtil;
import com.crawler.util.ElementUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 18:12
 * @description: 亚马逊Review爬虫
 **/
public class ReviewSpider extends Spider<AmazonReviewDetails> {
    private static final Logger logger = LogManager.getLogger(ReviewSpider.class);

    /**
     * 亚马逊数据请求URL模板
     */
    public static final String URL = "https://[site]/gp/product-reviews/[asin]/ref=cm_cr_arp_d_viewopt_sr?ie=UTF8&showViewpoints=1&filterByStar=all_stars&pageSize=[size]&reviewerType=all_reviews&sortBy=recent";

    /**
     * 亚马逊分页字段
     */
    public static final String PAGE_NUMBER = "pageNumber";

    /**
     * 亚马逊分页页码
     */
    public static final String PAGE_SIZE = "pageSize";

    private AmazonReviewService amazonReviewService;

    private AmazonReviewDetailsService amazonReviewDetailsService;

    public ReviewSpider(List<String> allowedDomains, Map<String, Object> dict, List<ItemPipline<AmazonReviewDetails>> itemPiplines, RequestManager requestManager,
                        OkHttpClient client, AmazonReview amazonReview, AmazonReviewService amazonReviewService, AmazonReviewDetailsService amazonReviewDetailsService) {
        super(allowedDomains, dict, itemPiplines, requestManager, client);
        this.amazonReview = amazonReview;
        this.amazonReviewService = amazonReviewService;
        this.amazonReviewDetailsService = amazonReviewDetailsService;
    }

    private AmazonReview amazonReview;

    @Override
    public SpiderParse<AmazonReviewDetails> parse(Response response, Call call, SpiderInfo<AmazonReviewDetails> spiderInfo, SpiderEngine<AmazonReviewDetails> spiderEngine)
            throws IOException {

        //this.stop();

        int code = response.code();

        if (code == 404) {
            // 产品下架
            logger.error(String.format("产品asin [ %s ]已下架，URL [ %s ]", amazonReview.getAsin(), response.request().url().toString()));
            return null;

        } else if (code == 301 || code == 302) {

            // URL被亚马逊重定向
            logger.error(String.format("URL[ %s ]被重定向为[ %s ]", this.requestManager.getRequestUrl().getUrl(), response.request().url().toString()));

            // 在head里标识是重定向的
            Map<String, String> head = new HashMap<>();
            head.put(REDIRECT, REDIRECT);

            // 重新请求
            Request request = this.requestManager.getRequest(head);
            super.client.newCall(request).enqueue(spiderEngine);

            // 继续爬
            Request nextRequest = this.requestManager.getNextPageRequest();
            return new SpiderParse<>(null, nextRequest);
        }

        String nowPageNumStr = CrawlerUtil.urlParamAnalysis(response.request().url().toString(), "pageNumber");
        Integer nowPageNum = Integer.valueOf(nowPageNumStr);

        // 页面源码
        String pageSource = response.body().string();
        Document doc = Jsoup.parse(pageSource);
        // 最后一页页面元素
        Elements lastPageFlag = doc.select("#cm_cr-pagination_bar > ul > li.a-disabled.a-last");
        // 下一页页面元素
        Elements nextPageFlag = doc.select("#cm_cr-pagination_bar > ul > li.a-last > a");

        // 检测页面元素是否被反爬虫
        if (Objects.isNull(lastPageFlag) && Objects.isNull(nextPageFlag)) {
            throw new SpiderCatchException("爬虫被亚马逊捕获", spiderInfo);
        }

        // 第一次请求先获取评论汇总信息
        Map<String, Object> dict = spiderInfo.getDict();
        boolean isFirstRequest = MapUtils.getBoolean(dict, "isFirstRequest", true);
        if (isFirstRequest) {
            dict.put("isFirstRequest", false);
            this.parseReview(doc, spiderInfo);
        }

        // 获取评论详情元素
        Elements reviewDetailList = doc.select("#cm_cr-review_list div[data-hook='review']");
        List<AmazonReviewDetails> itemList = this.parseReviewDetail(reviewDetailList);

        // 获取pageSize
        Integer pageSize = MapUtils.getInteger(this.dict, PAGE_SIZE);
        if (Objects.isNull(pageSize)) {
            pageSize = reviewDetailList.size();
            this.dict.put(ReviewSpider.PAGE_SIZE, pageSize);
        }

        Integer reviewTotal = MapUtils.getInteger(this.dict, "reviewTotal", 0);
        Integer countPageNum = CrawlerUtil.getPageCountNum(reviewTotal, pageSize);
        SpiderParse<AmazonReviewDetails> spiderParse = null;

        // 过滤 itemList
        itemList = this.amazonReviewDetailsService.checkAmazonReviewDetails(itemList, this.amazonReview);

        // 判断页面元素是否爬取到最后一页
        /*if (CollectionUtils.isEmpty(nextPageFlag) || CollectionUtils.isNotEmpty(lastPageFlag)) {
            spiderParse = new SpiderParse<>(itemList, null);

        } else */
        String redirect = response.request().header(REDIRECT);
        if (StringUtils.isNotBlank(redirect)) {

            // 重定向的回调则爬虫结束
            spiderParse = new SpiderParse<>(itemList, null);

        } else if ((itemList.size() != pageSize && CrawlStatusEnum.CRAWL.name().equals(amazonReview.getCrawlStatus()))
        || nowPageNum.equals(countPageNum)) {

            // 1.这个ASIN上次已经爬取完成并且当前页面有上次爬取的数据则认为当前爬虫爬取工作完成
            // 2.这个爬虫爬取到最后一页爬取工作完成
            spiderParse = new SpiderParse<>(itemList, null);
            // 更新时间
            AmazonReview updateAmazonReview = new AmazonReview();
            updateAmazonReview.setId(amazonReview.getId());
            updateAmazonReview.setCrawlStatus(CrawlStatusEnum.CRAWL.name());
            amazonReviewService.updateByPrimaryKeySelective(updateAmazonReview);

            logger.info(String.format("账号: [%s]  ASIN: [%s]  站点: [%s] 爬取完毕", amazonReview.getAmazonName(),
                    amazonReview.getAsin(), amazonReview.getAmazonSite()));

        } else if (nowPageNum < countPageNum) {

            // 继续爬
            Request request = this.requestManager.getNextPageRequest();
            spiderParse = new SpiderParse<>(itemList, request);
        }

        return spiderParse;
    }

    @Override
    public void reTry(Call call, IOException e) {
        String url = call.request().url().toString();
        Map<String, String> param = CrawlerUtil.urlParamAnalysis(url);
        Integer pageNum = MapUtils.getInteger(param, ReviewSpider.PAGE_NUMBER);

        AmazonReview updateAmazonReview = new AmazonReview();
        updateAmazonReview.setId(amazonReview.getId());
        updateAmazonReview.setInterruptPage(pageNum);
        updateAmazonReview.setCrawlStatus(CrawlStatusEnum.INTERRUPT.name());
        amazonReviewService.updateByPrimaryKeySelective(updateAmazonReview);
    }

    /**
     * 亚马逊评论信息解析
     *
     * @return
     */
    private void parseReview(Document doc, SpiderInfo<AmazonReviewDetails> spiderInfo) {

        String productName = doc.select("#cm_cr-product_info a[data-hook='product-link']").text();
        String reviewCountString = doc.select("div[class='a-row'] span[class*='totalReviewCount']").text();

        if (StringUtils.isBlank(reviewCountString)) {
            throw new SpiderOverException();
        }

        // 获取星评
        // 亚马逊会用千位分隔符分隔总数
        reviewCountString = reviewCountString.replace(".", "");
        int reviewCount = Integer.parseInt(reviewCountString);
        String star5 = this.replaceStar(doc.select("#histogramTable tr td:eq(2) a[class*='5star']").text());
        String star4 = this.replaceStar(doc.select("#histogramTable tr td:eq(2) a[class*='4star']").text());
        String star3 = this.replaceStar(doc.select("#histogramTable tr td:eq(2) a[class*='3star']").text());
        String star2 = this.replaceStar(doc.select("#histogramTable tr td:eq(2) a[class*='2star']").text());
        String star1 = this.replaceStar(doc.select("#histogramTable tr td:eq(2) a[class*='1star']").text());

        // 计算平均星评
        Double ratingStar = 0.0;
        if (reviewCount > 0) {
            Double allCount = (double) reviewCount;
            if (star1.contains("%") || star2.contains("%") || star3.contains("%") || star4.contains("%") || star5.contains("%")) {
                ratingStar = (CrawlerUtil.parsePercentage(star5) * allCount * 5 + CrawlerUtil.parsePercentage(star4) * allCount * 4
                        + CrawlerUtil.parsePercentage(star3) * allCount * 3 + CrawlerUtil.parsePercentage(star2) * allCount * 2
                        + CrawlerUtil.parsePercentage(star1) * allCount * 1) / allCount;
            } else {
                ratingStar = (Double.valueOf(star5) * 5 + Double.valueOf(star4) * 4 + Double.valueOf(star3) * 3
                        + Double.valueOf(star2) * 2 + Double.valueOf(star1) * 1) / allCount;
            }
            BigDecimal manyDecimal = new BigDecimal(ratingStar);
            ratingStar = manyDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        Element imageUrlElement = doc.select("#cm_cr-product_info div a img").first();
        String imageUrl = null;
        if (imageUrlElement != null) {
            imageUrl = imageUrlElement.attr("src");
        }

        amazonReview.setProductName(productName);
        amazonReview.setAllTotal(reviewCount);
        amazonReview.setRatingStar(ratingStar);
        amazonReview.setTotal5star(Double.valueOf(star5.replace("%", "")));
        amazonReview.setTotal4star(Double.valueOf(star4.replace("%", "")));
        amazonReview.setTotal3star(Double.valueOf(star3.replace("%", "")));
        amazonReview.setTotal2star(Double.valueOf(star2.replace("%", "")));
        amazonReview.setTotal1star(Double.valueOf(star1.replace("%", "")));
        amazonReview.setRatingGood(AmaoznReviewLevelEnum.getLevelByStar(ratingStar));
        amazonReview.setImageUrl(imageUrl);

        amazonReviewService.updateByPrimaryKeySelective(amazonReview);
        dict.put("reviewTotal", reviewCount);
    }

    /**
     * 亚马逊评论详情解析
     *
     * @return
     */
    private List<AmazonReviewDetails> parseReviewDetail(Elements reviewDetailList) {
        List<AmazonReviewDetails> detailsList = new ArrayList<>();

        // 解析评论明细
        if (CollectionUtils.isNotEmpty(reviewDetailList)) {

            Date now = new Date();
            detailsList = reviewDetailList.stream().map(review -> {
                String reviewUUID = review.attr("id");

                // 评论星级
                Elements starElement = review.select("i[data-hook='review-star-rating'][class*='star']");
                Set<String> classList = starElement.first().classNames();

                // 评论标题
                String title = ElementUtil.getTextByElements(review.select("[data-hook='review-title']"));
                // 评论用户名称
                String username = ElementUtil.getTextByElements(review.select(".a-profile-content > span"));
                // 评论时间
                String date = ElementUtil.getTextByElements(review.select("[data-hook='review-date']"));
                // 评论内容
                String comment = ElementUtil.getTextByElements(review.select("[data-hook='review-body']"));
                // 是否购买
                String purchase = ElementUtil.getTextByElements(review.select("span[data-hook='avp-badge']"));
                // 评论买家的message页面URL
                String messageUrl = ElementUtil.getAttrByElements(review.select(".a-profile"), "href");
                // 最佳评论者
                String topReviewer = ElementUtil.getTextByElement(review.select("a[class*='-reviewer']").first());
                // 有用评论数
                String usefull = ElementUtil.getTextByElement(review.select("span[class='review-votes']").first());
                // ASIN码，先提取URL
                String asinUrl = ElementUtil.getAttrByElements(review.select(".review-format-strip > span > a"), "href");
                // 评论图片
                String imageUrl = "";
                Elements reviewImages = review.select("div.review-image-tile-section img");
                if (CollectionUtils.isNotEmpty(reviewImages)) {
                    // 图片可能有多张
                    List<String> imgScr = new ArrayList<>(reviewImages.size());
                    for (Element image : reviewImages) {
                        imgScr.add(ElementUtil.getAttrByElement(image, "src"));
                    }
                    imageUrl = StringUtils.join(imgScr, "|");
                }

                AmazonReviewDetails detail = new AmazonReviewDetails();
                detail.setAsinUrl(asinUrl);
                detail.setMessageUrl(messageUrl);
                detail.setReviewer(username);
                detail.setReviewConent(comment);
                detail.setClassList(classList);
                detail.setReviewDate(date);
                detail.setReviewTitle(title);
                detail.setSite(this.amazonReview.getAmazonSite());
                detail.setAmazonUuid(reviewUUID);
                detail.setTopReviewerLevel(topReviewer);
                detail.setUsefullNum(AmazonSiteUtil.gainUserFullNum(amazonReview.getAmazonSite(), usefull));
                detail.setImageUrl(imageUrl);
                detail.setReviewerId(amazonReview.getId());
                detail.setAccountId(amazonReview.getAmazonId());
                detail.setCreateDate(now);
                if (StringUtils.isNotBlank(purchase)) {
                    detail.setPurchase("Y");
                } else {
                    detail.setPurchase("N");
                }

                try {
                    detail.setStandardDate(AmazonSiteUtil.chooseDateForSite(date, this.amazonReview.getAmazonSite()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 店铺
                if (StringUtils.isNotBlank(this.amazonReview.getAmazonName())) {
                    detail.setAccountName(this.amazonReview.getAmazonName());
                }

                this.extractAsin(detail);

                return detail;
            }).collect(Collectors.toList());
        }
        return detailsList;
    }

    private String replaceStar(String star) {
        return StringUtils.isNotBlank(star) ? star.replaceAll("\\u00A0", "").
                replaceAll(",", "").replace(".", "") : "0";
    }

    /**
     * 在URL中提取asin码
     *
     * @param details
     * @return
     */
    private void extractAsin(AmazonReviewDetails details) {
        // 处理ASIN 从URL中提取出asin码
        String asinUrl = details.getAsinUrl();
        String asin = null;
        if (StringUtils.isNotBlank(asinUrl)) {
            asin = StringUtils.isNotBlank(asinUrl) ? asinUrl.split("product-reviews\\/")[1].split("\\/ref")[0] : null;
        }

        // 如果在URL提取不到asin,则使用默认的asin
        if (StringUtils.isBlank(asin)) {
            asin = amazonReview.getAsin();
        }

        details.setAsin(asin);
    }
}