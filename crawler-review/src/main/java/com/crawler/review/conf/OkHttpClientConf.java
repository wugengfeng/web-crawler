package com.crawler.review.conf;

import com.crawler.framework.proxy.manager.FreeProxy;
import com.crawler.framework.proxy.selector.BasisProxySelector;
import com.crawler.framework.proxy.selector.BlockingQueueProxySelector;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.Proxy;
import java.util.Set;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-17 17:24
 * @description: 爬虫请求客户端配置
 * 同一类型的爬虫建议使用一个客户端
 **/
@Configuration
public class OkHttpClientConf {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /*@Bean(name = "proxySelector")
    public BasisProxySelector proxySelector() {
        System.out.println("获取代理选择器");
        FastIpProxyManager fastIpProxyManager = new FastIpProxyManager("");
        List<Proxy> proxyList = fastIpProxyManager.getProxy();

        BasisProxySelector proxySelector = new BlockingQueueProxySelector(proxyList);
        return proxySelector;
    }*/

    @Bean(name = "freeProxySelector")
    public BasisProxySelector freeProxySelector() {
        System.out.println("================  免费代理  ================");
        FreeProxy freeProxy = new FreeProxy();
        Set<Proxy> proxySet = freeProxy.getProxy();

        BasisProxySelector proxySelector = new BlockingQueueProxySelector(proxySet);
        return proxySelector;
    }

    /*@Bean(name = "redisProxySelector")
    public BasisProxySelector redisProxySelector() {
        FreeProxy freeProxy = new FreeProxy();
        Set<Proxy> proxySet = freeProxy.getProxy();
        BasisProxySelector selector = new RedisProxySelector(redisTemplate, proxySet,null);
        return selector;
    }*/

    @Bean(name = "reviewClient")
    public OkHttpClient reviewClient(BasisProxySelector freeProxySelector) {
        OkHttpClient.Builder reviewClientBuilder = new OkHttpClient.Builder();

        if (CollectionUtils.isNotEmpty(freeProxySelector.getProxyList())) {
            reviewClientBuilder.proxySelector(freeProxySelector);
        }

        return reviewClientBuilder.build();
    }

    @Bean(name = "feedBackClient")
    public OkHttpClient feedBackClient(BasisProxySelector freeProxySelector) {
        OkHttpClient.Builder feedBackClientBuilder = new OkHttpClient.Builder();

        if (CollectionUtils.isNotEmpty(freeProxySelector.getProxyList())) {
            feedBackClientBuilder.proxySelector(freeProxySelector);
        }

        return feedBackClientBuilder.build();
    }
}
