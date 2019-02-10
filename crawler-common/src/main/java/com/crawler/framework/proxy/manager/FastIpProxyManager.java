package com.crawler.framework.proxy.manager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wgf
 * @create: 2019-01-31 12:07
 * @description: 快代理 代理管理器
 **/
public class FastIpProxyManager implements ProxyManager {

    /**
     * 快代理的订单id
     */
    private String orderid;

    // 快代理API接口
    private final static String API = "http://ent.kuaidaili.com/api/getproxy/?orderid=[orderid]&num=100&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_tr=1&an_an=1&an_ha=1&sp1=1&quality=2&sort=1&sep=1";

    private final static String PLACEHOLDER = "[orderid]";

    public FastIpProxyManager(String orderid) {
        this.orderid = orderid;
    }

    @Override
    public Set<Proxy> getProxy() {

        Set<Proxy> resultSet = null;

        try {
            String url = API.replace(PLACEHOLDER, orderid);
            OkHttpClient client = new OkHttpClient.Builder().build();
            Response response = null;
            response = client.newCall(new Request.Builder().get().url(url).build()).execute();
            ResponseBody body = response.body();
            String content = body.string();
            resultSet = this.processProxy(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * 根据返回的数据解析出IP代理
     *
     * @return
     */
    private Set<Proxy> processProxy(String content) {
        Set<Proxy> resultSet = null;

        if (StringUtils.isNotBlank(content)) {
            resultSet = Arrays.stream(content.split("[\r\n]+"))
                    .distinct()
                    .filter(StringUtils::isNotBlank)
                    .map(line -> line.split(":"))
                    .map(split -> {
                        try {
                            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(split[0], Integer.valueOf(split[1])));
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        return resultSet;
    }
}
