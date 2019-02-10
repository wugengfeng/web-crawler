package com.crawler.framework.request;

import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.request.url.GetRequestUrl;
import okhttp3.Request;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-12 16:26
 * @description: GET请求请求管理器
 **/
public class GetRequestManager extends RequestManager {

    public GetRequestManager(GetRequestUrl requestUrl, UserAgent userAgent) {
        super(requestUrl, userAgent);
    }

    @Override
    public Request getRequest() {
        return this.getRequest(null);
    }

    @Override
    public Request getRequest(Map<String, String> head) {
        Request request = null;
        Request.Builder requestBuilder = null;

        if (Objects.nonNull(userAgent)) {
            requestBuilder = new Request.Builder()
                    .get()
                    .url(requestUrl.getUrl())
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, userAgent.getUserAgent());
        } else {
            requestBuilder = new Request.Builder()
                    .get()
                    .url(requestUrl.getUrl());
        }

        if (MapUtils.isNotEmpty(head)) {
            for (Map.Entry<String, String> entry : head.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (Objects.nonNull(requestBuilder)) {
            request = requestBuilder.build();
        }

        return request;
    }

    @Override
    public Request getNextPageRequest() {
        Request request;

        if (Objects.nonNull(userAgent)) {
            request = new Request.Builder()
                    .get()
                    .url(requestUrl.pageTurn())
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, userAgent.getUserAgent())
                    .build();
        } else {
            request = new Request.Builder()
                    .get()
                    .url(requestUrl.pageTurn())
                    .build();
        }

        return request;
    }

    @Override
    public Request getNextPageRequest(Map<String, String> dict) {
        Request request;

        if (Objects.nonNull(userAgent)) {
            request = new Request.Builder()
                    .get()
                    .url(requestUrl.pageTurn(dict))
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, userAgent.getUserAgent())
                    .build();
        } else {
            request = new Request.Builder()
                    .get()
                    .url(requestUrl.pageTurn(dict))
                    .build();
        }

        return request;
    }
}
