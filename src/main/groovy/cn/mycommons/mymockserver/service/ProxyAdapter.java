package cn.mycommons.mymockserver.service;

import cn.mycommons.mymockserver.MyMockServer;
import cn.mycommons.mymockserver.app.Const;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.AttributeKey;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;

/**
 * ProxyHttpFiltersSourceAdapter <br/>
 * Created by Leon on 2017-08-29.
 */
public class ProxyAdapter extends HttpFiltersSourceAdapter {

    private static final int M_10 = 10 * 1024 * 1024;
    private static final Logger LOGGER = Logger.getLogger(ProxyAdapter.class);
    private static final AttributeKey<String> CONNECTED_URL = AttributeKey.valueOf("connected_url");

    private MyMockServer myMockServer;

    public ProxyAdapter(MyMockServer myMockServer) {
        this.myMockServer = myMockServer;
    }

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext clientCtx) {
        String uri = originalRequest.uri();
        HttpMethod method = originalRequest.method();

        LOGGER.info(method.name() + ": " + uri);
        if (Const.HTTPS) {
            if (method == HttpMethod.CONNECT) {
                if (clientCtx != null) {
                    String prefix = "https://" + uri.replaceFirst(":443$", "");
                    clientCtx.channel().attr(CONNECTED_URL).set(prefix);
                }
                return new HttpFiltersAdapter(originalRequest, clientCtx);
            }
            String connectedUrl = clientCtx.channel().attr(CONNECTED_URL).get();
            if (connectedUrl != null) {
                originalRequest.setUri(connectedUrl + uri);
            }
        }

        return new ProxyHttpFilters(originalRequest, clientCtx, myMockServer);
    }

    @Override
    public int getMaximumRequestBufferSizeInBytes() {
        return M_10;
    }

    @Override
    public int getMaximumResponseBufferSizeInBytes() {
        return M_10;
    }
}