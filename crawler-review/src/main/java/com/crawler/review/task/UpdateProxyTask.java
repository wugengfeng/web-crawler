package com.crawler.review.task;

import com.crawler.framework.proxy.manager.FreeProxy;
import com.crawler.framework.proxy.selector.BasisProxySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.util.Set;

/**
 * @author: wgf
 * @create: 2019-01-31 18:25
 * @description:
 **/
@Component
public class UpdateProxyTask {

    @Autowired
    private BasisProxySelector redisProxySelector;

    /*@Autowired
    private BasisProxySelector freeProxySelector;

    @Scheduled(initialDelay = 600 * 1000L, fixedDelay = 600 * 1000L)
    public void updateProxy() {
        FreeProxy freeProxy = new FreeProxy();
        Set<Proxy> proxyList = freeProxy.getProxy();
        freeProxySelector.updateProxies(proxyList, null);
    }*/

    @Scheduled(initialDelay = 600 * 1000L, fixedDelay = 600 * 1000L)
    public void updateProxy() {
        FreeProxy freeProxy = new FreeProxy();
        Set<Proxy> proxyList = freeProxy.getProxy();
        redisProxySelector.updateProxies(proxyList, null);
    }

}
