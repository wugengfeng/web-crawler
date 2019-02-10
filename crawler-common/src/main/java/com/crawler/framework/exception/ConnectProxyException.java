package com.crawler.framework.exception;

import java.net.SocketAddress;
import java.net.URI;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-09 17:44
 * @description: IP代理连接异常
 **/
public class ConnectProxyException extends RuntimeException{

    private String errorMsg;
    private URI uri;
    private SocketAddress sa;

    public ConnectProxyException() {
    }

    public ConnectProxyException(String errorMsg, URI uri, SocketAddress sa) {
        this.errorMsg = errorMsg;
        this.uri = uri;
        this.sa = sa;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public SocketAddress getSa() {
        return sa;
    }

    public void setSa(SocketAddress sa) {
        this.sa = sa;
    }
}
