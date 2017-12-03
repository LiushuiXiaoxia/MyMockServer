package cn.mycommons.mymockserver.bean.http

import cn.mycommons.mymockserver.bean.base.IBean
import cn.mycommons.mymockserver.exception.MockParseException

class Request implements IBean {
    static final String HTTP = "http"
    static final String HTTPS = "https"

    private List<String> scheme = [HTTP, HTTPS]
    private String host
    private int port = 80
    private String method
    private String path
    private Params params
    private String url
    private Headers headers
    // private Body body

    List<String> getScheme() {
        return scheme
    }

    void setScheme(List<String> scheme) {
        this.scheme = scheme
    }

    String getHost() {
        return host
    }

    void setHost(String host) {
        this.host = host
    }

    int getPort() {
        return port
    }

    void setPort(int port) {
        this.port = port
    }

    String getMethod() {
        return method
    }

    void setMethod(String method) {
        this.method = method
    }

    String getPath() {
        return path
    }

    void setPath(String path) {
        this.path = path
    }

    Params getParams() {
        return params
    }

    void setParams(Params params) {
        this.params = params
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    Headers getHeaders() {
        return headers
    }

    void setHeaders(Headers headers) {
        this.headers = headers
    }

//    Body getBody() {
//        return body
//    }
//
//    void setBody(Body body) {
//        this.body = body
//    }

    def scheme(String string) {
        if (string != null) {
            scheme.clear()

            if (string.length() == 0) {
                scheme.add(HTTP)
                scheme.add(HTTPS)
            } else {
                string.split(",").each {
                    if (HTTP == it || HTTPS == it) {
                        scheme.add(it)
                    } else {
                        throw new MockParseException("scheme($it) must be ${HTTP} 、 ${HTTPS}")
                    }
                }
            }
        }
    }
}