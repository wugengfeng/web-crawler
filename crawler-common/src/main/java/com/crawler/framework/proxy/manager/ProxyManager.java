package com.crawler.framework.proxy.manager;

import java.net.Proxy;
import java.util.Set;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-11 10:30
 * @description: IP代理管理器接口
 * <p>
 * 此接口定义了通过HTTP协议获取IP代理的方法
 * 并重写 getProxy方法，提供回调参数，可自定义解析
 * HTTP请求的Response对象
 * </p>
 **/
public interface ProxyManager {

    Set<Proxy> getProxy();
}
