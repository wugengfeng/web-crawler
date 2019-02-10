package com.crawler.framework.request;

import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.request.url.PostRequestUrl;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @program: customer-service
 * @author: wgf
 * @create: 2019-01-14 09:27
 * @description: POST请求管理器
 **/
public class FormPostRequestManager extends RequestManager implements PostRequestBody {

    public FormPostRequestManager(PostRequestUrl requestUrl, UserAgent userAgent) {
        super(requestUrl, userAgent);
    }

    @Override
    public Request getRequest() {
        return this.getRequest(null);
    }

    public Request getRequest(Map<String, String> head) {
        Request request = null;
        Request.Builder requestBuilder = null;
        RequestBody requestBody = this.buildRequestBody(this.requestUrl);

        if (Objects.nonNull(userAgent)) {
            requestBuilder = new Request.Builder()
                    .url(requestUrl.getUrl())
                    .post(requestBody)
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, userAgent.getUserAgent());
        } else {
            requestBuilder = new Request.Builder()
                    .url(requestUrl.getUrl())
                    .post(requestBody);
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
        return this.getNextPageRequest(null);
    }

    @Override
    public Request getNextPageRequest(Map<String, String> dict) {
        Request request = null;

        if (MapUtils.isEmpty(dict)) {
            this.requestUrl.pageTurn();
        } else {
            this.requestUrl.pageTurn(dict);
        }

        RequestBody requestBody = this.buildRequestBody(this.requestUrl);

        if (Objects.nonNull(this.userAgent)) {
            request = new Request.Builder()
                    .url(requestUrl.getUrl())
                    .post(requestBody)
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, userAgent.getUserAgent())
                    .build();
        } else {
            request = new Request.Builder()
                    .url(requestUrl.getUrl())
                    .post(requestBody)
                    .build();
        }

        return request;
    }

    @Override
    public RequestBody buildRequestBody(Map<Serializable, Serializable> bodyParams) {
        FormBody.Builder builder = new FormBody.Builder();

        if (MapUtils.isNotEmpty(bodyParams)) {
            bodyParams.forEach((k, v) -> {
                String key = String.valueOf(k);
                String value = String.valueOf(v);
                builder.add(key, value);
            });
        }

        FormBody body = builder.build();
        return body;
    }
}
