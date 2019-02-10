package com.crawler.framework.agent;

import com.crawler.config.UserAgentConf;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-15 14:47
 * @description:
 **/
public class DefaultUserAgent implements UserAgent {

    @Override
    public String getUserAgent() {
        List<String> userAgent = UserAgentConf.USER_AGENT;
        int min = 0;
        int max = userAgent.size() - 1;
        int index = min + (int) (Math.random() * (max - min + 1));
        return userAgent.get(index);
    }
}
