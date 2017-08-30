package cn.mycommons.mymockserver.service;

import cn.mycommons.mymockserver.MyMockServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;

/**
 * ProxyHttpFiltersSourceAdapter <br/>
 * Created by Leon on 2017-08-29.
 */
public class ProxyHttpFiltersSourceAdapter extends HttpFiltersSourceAdapter {

    private MyMockServer myMockServer;

    public ProxyHttpFiltersSourceAdapter(MyMockServer myMockServer) {
        this.myMockServer = myMockServer;
    }

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        return new ProxyHttpFilters(originalRequest, ctx, myMockServer);
    }
}