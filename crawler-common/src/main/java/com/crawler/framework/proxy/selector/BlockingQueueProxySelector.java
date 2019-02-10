package com.crawler.framework.proxy.selector;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-08 19:47
 * @description: 基于队列实现的IP代理选择器
 **/
public class BlockingQueueProxySelector<T> extends BasisProxySelector<T> {

    public BlockingQueueProxySelector(Collection<Proxy> proxies) {
        this.proxies = new LinkedBlockingQueue<>(proxies);
    }

    /**
     * IP动态代理阻塞队列
     * 选择阻塞队列作为代理集合是因为阻塞队列可以解决
     * 在定时更新IP代理时爬虫无IP代理可用问题。
     */
    private final BlockingQueue<Proxy> proxies;

    @Override
    public boolean clearProxies(T t) {
        this.proxies.clear();
        return Boolean.TRUE;
    }

    @Override
    public boolean removeProxy(Proxy proxy) {

        if (proxy != null) {
            this.proxies.remove(proxy);
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean addProxy(Proxy proxy) {

        if (proxy != null) {
            return this.proxies.add(proxy);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean updateProxies(Set<Proxy> newProxies, T t) {
        if (CollectionUtils.isNotEmpty(this.proxies)) {
            this.clearProxies(t);
        }

        return this.proxies.addAll(newProxies);
    }

    @Override
    public Collection<Proxy> getProxyList() {
        return this.proxies;
    }

    /**
     * 每次请求获取IP代理
     *
     * @param uri
     * @return
     */
    @Override
    public List<Proxy> select(URI uri) {
        Proxy proxy = null;
        try {
            proxy = this.proxies.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(proxy.address());

        // 归还代理到队列尾部
        this.proxies.add(proxy);
        return Collections.singletonList(proxy);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

        // 先删除不可用代理
        Proxy errorProxy = this.proxies.stream()
                .filter(item -> item.address().equals(sa))
                .findAny()
                .orElse(null);

        if (errorProxy != null) {
            this.proxies.remove(errorProxy);
        }

        super.connectFailed(uri, sa, ioe);
    }
}
