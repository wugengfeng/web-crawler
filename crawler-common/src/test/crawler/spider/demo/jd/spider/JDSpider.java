package crawler.spider.demo.jd.spider;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.request.RequestManager;
import com.crawler.framework.spider.Spider;
import com.crawler.framework.spider.SpiderEngine;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.framework.spider.SpiderParse;
import com.crawler.util.ElementUtil;
import crawler.spider.demo.jd.entity.JDInfo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 京东搜索爬虫
 */
public class JDSpider extends Spider<JDInfo> {
    /**
     * https://search.jd.com/Search?keyword=java&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=1&s=54&click=0
     * https://search.jd.com/Search?keyword=java&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=31&s=54&click=0
     * 其中 keyword为搜索关键字
     * page为页码偏移量
     * 页码自增量为2 第一页page=1 第二页page=3
     */
    public static final String URL = "https://search.jd.com/Search?keyword=[keyword]&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&s=54&click=0";

    // 分页字段
    public static final String PAGE_NUM_FIELD = "page";

    // 起始页
    public static final Integer PAGE_NUM = 1;

    // 页码偏移量
    public static final Integer STEP = 2;

    public JDSpider() {
    }

    public JDSpider(List<String> allowedDomains, Map<String, Object> dict, List<ItemPipline<JDInfo>> itemPiplines, RequestManager requestManager, OkHttpClient client) {
        super(allowedDomains, dict, itemPiplines, requestManager, client);
    }

    /**
     * HTTP请求回调解析
     *
     * @param response     HTTP响应
     * @param call
     * @param spiderInfo
     * @param spiderEngine
     * @return
     * @throws IOException
     */
    @Override
    public SpiderParse<JDInfo> parse(Response response, Call call, SpiderInfo<JDInfo> spiderInfo, SpiderEngine<JDInfo> spiderEngine) throws IOException {
        int code = response.code();
        SpiderParse<JDInfo> result = null;
        List<JDInfo> jdInfoList = null;

        if (code == 200) {
            String context = response.body().string();

            //使用 jsoup 解析HTML
            Document doc = Jsoup.parse(context);
            Elements list = doc.select("#J_goodsList > ul > li");

            jdInfoList = new ArrayList<>();
            for (Element element : list) {
                // 抓取SKU
                String sku = element.attr("data-sku");

                // 抓取价格
                String priceStr = element.select("div > div.p-price > strong > i").text();
                Double price = StringUtils.isBlank(priceStr) ? -1D : Double.valueOf(priceStr);

                // 抓取商品名字
                String priceName = ElementUtil.getTextByElements(element.select("a > em"));

                // 抓取商品图片
                String imgUrl = element.select("div > div.p-img > a > img").first().attr("source-data-lazy-img");
                imgUrl = StringUtils.isBlank(imgUrl) ? imgUrl : "https:" + imgUrl;

                JDInfo jdInfo = new JDInfo();
                jdInfo.setSku(sku);
                jdInfo.setPrice(price);
                jdInfo.setProductName(priceName);
                jdInfo.setImgUrl(imgUrl);
                jdInfoList.add(jdInfo);
            }

            // 只爬取前两页的信息
            if (this.requestManager.getRequestUrl().getPageNum() >= 3) {
                result = new SpiderParse<>(jdInfoList, null);
            } else {
                // 继续爬取下一页
                Request request = this.requestManager.getNextPageRequest();
                result = new SpiderParse<>(jdInfoList, request);
            }
        }

        return result;
    }

    @Override
    public void reTry(Call call, IOException e) {

    }
}
