package crawler.spider.demo.jd.pipeline;

import com.crawler.framework.pipeline.ItemPipline;
import com.crawler.framework.spider.SpiderInfo;
import crawler.spider.demo.jd.entity.JDInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 打印组件
 * 可以传入service持久化数据
 */
public class PrintPipeLine extends ItemPipline<JDInfo>{

    @Override
    public List<JDInfo> processItem(SpiderInfo<JDInfo> spiderInfo, List<JDInfo> itemList) {
        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.forEach(item -> System.out.println(item));
        }

        return itemList;
    }
}
