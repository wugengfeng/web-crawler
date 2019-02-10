package com.crawler.framework.request;

import okhttp3.RequestBody;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-21 11:04
 * @description:
 **/
public interface PostRequestBody {

    /**
     * 构造RequestBody
     *
     * @param param 请求参数
     * @return
     */
    RequestBody buildRequestBody(Map<Serializable, Serializable> param);
}
