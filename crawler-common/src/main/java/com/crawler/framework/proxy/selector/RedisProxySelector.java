package com.crawler.framework.proxy.selector;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-09 18:00
 * @description: 基于Redis实现的IP代理选择器
 **/
public class RedisProxySelector<T> extends BasisProxySelector<T> {

    private static final Long RAMDOM_SIZE = 2L;
    private RedisTemplate<String, Object> redisTemplate;
    private SetOperations<String, Object> setOperations;
    private String proxySetKey = "ip:proxy";

    public RedisProxySelector(RedisTemplate<String, Object> redisTemplate,Set<Proxy> proxySet, String proxySetKey) {
        this.redisTemplate = redisTemplate;
        this.setOperations = redisTemplate.opsForSet();

        if (Objects.nonNull(proxySetKey)) {
            this.proxySetKey = proxySetKey;
        }

        if (CollectionUtils.isNotEmpty(proxySet)) {
            this.updateProxies(proxySet, null);
        }
    }

    @Override
    public boolean clearProxies(T t) {
        return this.redisTemplate.delete(this.proxySetKey);
    }

    @Override
    public boolean removeProxy(Proxy proxy) {
        String ipProxy = proxy.address().toString();
        return this.setOperations.remove(this.proxySetKey, ipProxy) > 0 ? true : false;
    }

    @Override
    public boolean addProxy(Proxy proxy) {
        String ipProxy = proxy.address().toString();
        return this.setOperations.add(this.proxySetKey, ipProxy) > 0 ? true : false;
    }

    @Override
    public boolean updateProxies(Set<Proxy> newProxies, T t) {

        if (CollectionUtils.isNotEmpty(newProxies)) {
            List<String> proxyList = newProxies.stream()
                    .map(item -> item.address().toString().replace("/", ""))
                    .collect(Collectors.toList());

            this.clearProxies(null);
            this.setOperations.add(this.proxySetKey, proxyList.toArray());

            return true;
        }

        return false;
    }

    @Override
    public Collection<Proxy> getProxyList() {
        Set<Object> set = this.setOperations.members(this.proxySetKey);

        if (CollectionUtils.isNotEmpty(set)) {
            List<Proxy> proxyList = set.stream()
                    .map(item -> {
                        String proxy = item.toString();
                        String arr[] = proxy.split(":");
                        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(arr[0], Integer.valueOf(arr[1])));
                    }).collect(Collectors.toList());
            return proxyList;
        }

        return null;
    }

    @Override
    public List<Proxy> select(URI uri) {
        List<Object> list = this.setOperations.randomMembers(this.proxySetKey, RAMDOM_SIZE);

        if (CollectionUtils.isNotEmpty(list)) {
            List<Proxy> proxyList = list.stream()
                    .map(item -> {
                        String proxy = item.toString();
                        String arr[] = proxy.split(":");
                        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(arr[0], Integer.valueOf(arr[1])));
                    }).collect(Collectors.toList());
            return proxyList;
        }

        return null;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        String ipProxy = sa.toString().replace("/", "");
        this.setOperations.remove(this.proxySetKey, ipProxy);
    }
}
