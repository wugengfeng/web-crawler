package com.crawler.framework.proxy.selector;

import com.crawler.framework.exception.ConnectProxyException;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-09 16:32
 * @description: 基础代理选择器
 * <p>
 *  当IP代理不可用会抛出ConnectProxyException异常并且停止当前爬虫的爬取动作
 * </p>
 **/
public abstract class BasisProxySelector<T> extends ProxySelector {

    /**
     * 返回一个最适合访问该URI的代理服务器列表，
     * 其中会首选列表的第一个代理，如果不行则用第二个，
     * 如果全部不行就会调用connectFailed方法处理连接失败问题
     *
     * @param uri
     * @return
     */
    @Override
    public abstract List<Proxy> select(URI uri);

    /**
     * 连接代理服务器失败时的回调
     * <p>
     * 连接不上IP代理会抛出 {@link com.crawler.framework.exception.ConnectProxyException} 异常
     * </p>
     *
     * @param uri
     * @param sa
     * @param ioe
     */
    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
       /* Collection<Proxy> proxyList = this.getProxyList();

        Iterator<Proxy> iterator = proxyList.iterator();
        while (iterator.hasNext()) {
            Proxy proxy = iterator.next();
            if (proxy.address().equals(sa)) {
                proxyList.remove(proxy);
                break;
            }
        }*/

        throw new ConnectProxyException(ioe.getMessage(), uri, sa);
    }

    /**
     * 清除当前IP代理选择器的IP代理列表
     */
    public abstract boolean clearProxies(T t);

    /**
     * 将指定IP代理从当前代理列表中删除
     *
     * @param proxy
     */
    public abstract boolean removeProxy(Proxy proxy);

    /**
     * 添加一个IP代理到当前代理选择器列表
     *
     * @param proxy IP代理
     * @return
     */
    public abstract boolean addProxy(Proxy proxy);

    /**
     * 更新代理选择器IP代理列表
     * <p>此操作会将当前的IP代理列表清除，重新设置新的IP代理列表</p>
     *
     * @param newProxies 新的IP代理列表
     * @return
     */
    public abstract boolean updateProxies(Set<Proxy> newProxies, T t);

    /**
     * 获取代理列表
     *
     * @return
     */
    public abstract Collection<Proxy> getProxyList();
}
