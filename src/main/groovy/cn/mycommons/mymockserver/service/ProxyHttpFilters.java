package cn.mycommons.mymockserver.service;

import cn.mycommons.mymockserver.MyMockServer;
import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.service.match.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.net.URI;
import java.util.Optional;

/**
 * ProxyHttpFilters <br/>
 * Created by Leon on 2017-08-29.
 */
public class ProxyHttpFilters extends HttpFiltersAdapter {

    public static final Logger LOGGER = Logger.getLogger(ProxyHttpFilters.class);

    private MyMockServer myMockServer;

    ProxyHttpFilters(HttpRequest originalRequest, ChannelHandlerContext ctx, MyMockServer myMockServer) {
        super(originalRequest, ctx);
        this.myMockServer = myMockServer;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        String uri = originalRequest.uri();
        HttpMethod method = originalRequest.method();
        LOGGER.info(String.format("proxy -> %s:%s begin", method, uri));

        try {
            HttpResponse mock = doCheck(method.name());
            if (mock != null) {
                LOGGER.info(String.format("proxy -> %s:%s success", method, uri));
                return mock;
            }
        } catch (Exception e) {
            LOGGER.info(String.format("proxy -> %s:%s fail, e = %s", method, uri, e.getMessage()));
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info(String.format("proxy -> %s:%s skip", method, uri));
        return null;
    }

    private HttpResponse doCheck(String method) {
        URI uri = URI.create(originalRequest.uri());
        // scheme,port,host,path,query,header,body
        Optional<Mock> first = myMockServer.getConfigMock()
                .stream()
                // enable
                .filter(new EnableMockPredicate())
                // scheme
                .filter(new SchemeMockPredicate(uri))
                // port
                .filter(new PortMockPredicate(uri))
                // host
                .filter(new HostMockPredicate(uri))
                // path
                .filter(new PathMockPredicate(uri))
                // method
                .filter(new MethodMockPredicate(originalRequest))
                // param
                .filter(new ParamMockPredicate(originalRequest))
                // header
                .filter(new HeaderMockPredicate(originalRequest))
                .findFirst();

        if (first != null && first.isPresent()) {
            Mock mock = first.get();
            LOGGER.debug(String.format("proxy -> %s:%s mock = %s", method, uri, mock));
            if (mock.getResponse() != null) {
                return MockResponseGenerator.gen(mock);
            }
        }
        return null;
    }
}