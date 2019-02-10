package com.crawler.framework.proxy.manager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wgf
 * @create: 2019-01-31 14:44
 * @description: 免费代理测试
 **/
public class FreeProxy implements ProxyManager {

    private static final String URL = "https://www.kuaidaili.com/free/intr/";

    @Override
    public Set<Proxy> getProxy() {
        Set<Proxy> resultList = null;

        try {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Response response = null;
            response = client.newCall(new Request.Builder().get().url(URL).build()).execute();
            ResponseBody body = response.body();
            String content = body.string();
            resultList = this.processProxy(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private Set<Proxy> processProxy(String content) {
        Document doc = Jsoup.parse(content);
        Elements trList = doc.select("#list > table > tbody > tr");

        Set<Proxy> proxySet = new HashSet<>();
        for (Element tr : trList) {
            Elements tdList = tr.select("td");
            String ip = tdList.get(0).text();
            String port = tdList.get(1).text();
            System.out.println(ip + ":" + port);
            proxySet.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, Integer.valueOf(port))));
        }

        return proxySet;
    }
}
