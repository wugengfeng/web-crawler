package com.crawler.framework.spider;

import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 10:22
 * @description: 基础爬虫
 **/
public interface BasisSpider<T> {

    /**
     * 爬虫数据解析
     * <p>
     * 返回 SpiderParse 对象，如果SpiderParse对象里的 Request为空则说明爬虫爬取完毕，
     * 不再继续往下爬取但会执行ItemPipeLine，如果返回null说明本次爬虫直接停止，不执行ItemPipeLine
     * {@link com.crawler.framework.spider.SpiderParse}
     * </p>
     *
     * @param response HTTP响应
     * @return
     * @throws IOException
     */
    SpiderParse<T> parse(Response response, Call call, SpiderInfo<T> spiderInfo, SpiderEngine<T> spiderEngine) throws IOException;

    /**
     * 爬虫失败重试回掉
     *
     * @param call
     * @param e
     * @return
     */
    void reTry(Call call, IOException e);
}
