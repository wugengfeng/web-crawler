package crawler.spider.demo.jd;

import com.crawler.framework.agent.DefaultUserAgent;
import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.proxy.manager.FreeProxy;
import com.crawler.framework.proxy.selector.BasisProxySelector;
import com.crawler.framework.proxy.selector.BlockingQueueProxySelector;
import com.crawler.framework.request.GetRequestManager;
import com.crawler.framework.request.RequestManager;
import com.crawler.framework.request.url.GetRequestUrl;
import com.crawler.framework.spider.SpiderEngine;
import crawler.spider.demo.jd.entity.JDInfo;
import crawler.spider.demo.jd.pipeline.DeleteHtmlPipeLine;
import crawler.spider.demo.jd.pipeline.PrintPipeLine;
import crawler.spider.demo.jd.spider.JDSpider;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 京东搜索页爬取测试
 */
public class JdTest {

    /**
     * 请求可能会失败（免费代理不是所有都能用的）
     * @param args
     */
    public static void main(String[] args) {

        // 搜索关键词
        String key = "java";

        // 获取免费代理
        FreeProxy freeProxy = new FreeProxy();
        Set<Proxy> proxySet = freeProxy.getProxy();

        /**
         * 创建代理选择器
         * 正常项目建议代理选择器实例交由spring管理，并使用定时任务调用选择器的updateProxies定时刷新IP代理池
         */
        BasisProxySelector proxySelector = new BlockingQueueProxySelector(proxySet);

        // OkHttp客户端，正常项目和代理选择器一样，建议交由spring管理，使用时使用自动注入
        OkHttpClient client = new OkHttpClient.Builder().proxySelector(proxySelector).build();

        // 创建HTTP URL 对象
        GetRequestUrl getRequestUrl = GetRequestUrl.build(JDSpider.URL, JDSpider.PAGE_NUM_FIELD, JDSpider.STEP)
                .addParam(JDSpider.PAGE_NUM_FIELD, JDSpider.PAGE_NUM)
                .replaceUrlPrefix("[keyword]", key);

        // 使用默认实现的userAgent
        UserAgent userAgent = new DefaultUserAgent();

        // 创建请求管理器
        RequestManager requestManager = new GetRequestManager(getRequestUrl, userAgent);

        // 创建数据处理组件
        List<ItemPipline<JDInfo>> itemPiplines = Arrays.asList(new DeleteHtmlPipeLine(), new PrintPipeLine());

        // 创建爬虫
        JDSpider jdSpider = new JDSpider(null, null, itemPiplines, requestManager, client);

        // 将爬虫提交到引擎
        SpiderEngine<JDInfo> spiderEngine = new SpiderEngine(jdSpider);

        // 爬虫开始请求
        spiderEngine.submitRequest();
    }
}
