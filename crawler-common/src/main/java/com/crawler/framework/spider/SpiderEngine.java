package com.crawler.framework.spider;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.url.AbstractGetRequestUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 10:21
 * @description: 爬虫引擎
 **/
public class SpiderEngine<T> implements Callback {

    public SpiderEngine(Spider<T> spider) {
        this.spider = spider;
    }

    private Spider<T> spider;

    /**
     * 提交第一次请求
     */
    public void submitRequest() {
        boolean flag = true;

        // 判断域名
        if (CollectionUtils.isNotEmpty(spider.getAllowedDomains())) {
            String nowUrl = spider.getRequest().url().toString();
            for (String domain : spider.getAllowedDomains()) {
                flag &= nowUrl.contains(domain);
            }
        }

        if (flag) {
            spider.getClient().newCall(spider.getRequest()).enqueue(this);
        }

    }

    /**
     * 提交下一页请求
     * <p>
     * 当字典不为空，在构造Request对象时，创建URL根据构造RequestUrl对象构造函数设置的URL进行构造（urlPrefix的副本）
     * {@link AbstractGetRequestUrl}
     * </p>
     *
     * @param request
     */
    protected void submitNextPageRequest(Request request) {
        boolean flag = true;

        // 判断域名
        if (CollectionUtils.isNotEmpty(spider.getAllowedDomains())) {
            String nowUrl = spider.getRequest().url().toString();
            for (String domain : spider.getAllowedDomains()) {
                flag &= nowUrl.contains(domain);
            }
        }

        if (flag) {
            spider.getClient().newCall(request).enqueue(this);
        }
    }

    public Spider<T> getSpider() {
        return spider;
    }

    /**
     * 请求失败回调
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        spider.reTry(call, e);
    }

    /**
     * 请求成功回调
     *
     * @param call
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        // 解析返回结果

        SpiderInfo<T> spiderInfo = new SpiderInfo<>();
        spiderInfo.setSpider(this.spider.getClass());
        spiderInfo.setResponse(response);
        spiderInfo.setDict(spider.getDict());

        SpiderParse<T> spiderParse = this.spider.parse(response, call, spiderInfo, this);
        if (Objects.isNull(spiderParse)) {
            return;
        }

        List<T> itemList = spiderParse.getItemList();
        spiderInfo.setItemList(itemList);

        // 执行数据处理组件
        if (CollectionUtils.isNotEmpty(spider.getItemPiplines())) {
            for (ItemPipline<T> itemPipline : spider.getItemPiplines()) {
                itemPipline.beFore(spiderInfo);
                itemList = itemPipline.processItem(spiderInfo, itemList);
                itemPipline.alter(spiderInfo);
            }
        }

        // 继续爬取
        if (Objects.nonNull(spiderParse.getRequest())) {
            // 提交下一页请求
            this.submitNextPageRequest(spiderParse.getRequest());
        }
    }
}