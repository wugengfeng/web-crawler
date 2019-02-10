package crawler.spider.demo.jd.pipeline;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import com.crawler.util.CrawlerUtil;
import crawler.spider.demo.jd.entity.JDInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 删除HTML代码组件
 * 删除京东商品名称包含的html代码
 */
public class DeleteHtmlPipeLine extends ItemPipline<JDInfo>{

    @Override
    public List<JDInfo> processItem(SpiderInfo<JDInfo> spiderInfo, List<JDInfo> itemList) {
        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.forEach(item -> {
                String productName = item.getProductName();
                productName = CrawlerUtil.deleteAllHTMLTag(productName);
                item.setProductName(productName);
            });
        }
        return itemList;
    }
}
