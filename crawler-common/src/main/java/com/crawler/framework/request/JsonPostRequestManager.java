package com.crawler.framework.request;

import com.alibaba.fastjson.JSON;
import com.crawler.framework.agent.UserAgent;
import com.crawler.framework.request.url.PostRequestUrl;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wgf
 * POST JSON格式请求管理器
 */
public class JsonPostRequestManager extends FormPostRequestManager {

    public JsonPostRequestManager(PostRequestUrl requestUrl, UserAgent userAgent) {
        super(requestUrl, userAgent);
    }

    @Override
    public RequestBody buildRequestBody(Map<Serializable, Serializable> bodyParams) {
        String jsonParam = null;

        if (MapUtils.isNotEmpty(bodyParams)) {
            jsonParam = JSON.toJSONString(bodyParams);
        }
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParam);
        return requestBody;
    }
}
