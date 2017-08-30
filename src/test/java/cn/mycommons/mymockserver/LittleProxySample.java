package cn.mycommons.mymockserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * LittleProxyTest <br/>
 * Created by Leon on 2017-08-29.
 */
public class LittleProxySample {

    private static final int PORT = 8001;
    private static final Logger LOGGER = Logger.getLogger(LittleProxySample.class);

    public static void main(String[] args) {
        HttpProxyServer server = DefaultHttpProxyServer.bootstrap()
                .withPort(PORT)
                .withFiltersSource(new HttpFiltersSourceAdapter() {
                    @Override
                    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        LOGGER.info("filterRequest originalRequest = " + originalRequest.getUri());
                        LOGGER.info("filterRequest ctx = " + ctx);
                        return new HttpFiltersAdapter(originalRequest, ctx) {
                            @Override
                            public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                LOGGER.info("clientToProxyRequest");
                                ByteBuf buffer = Unpooled.wrappedBuffer("Hello World!".getBytes());
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
                                HttpHeaders.setContentLength(response, buffer.readableBytes());
                                HttpHeaders.setHeader(response, HttpHeaders.Names.CONTENT_TYPE, "text/html");
                                return response;
                            }

                            @Override
                            public HttpResponse proxyToServerRequest(HttpObject httpObject) {
                                LOGGER.info("proxyToServerRequest");
                                return null;
                            }
                        };
                    }

                    @Override
                    public int getMaximumRequestBufferSizeInBytes() {
                        return 10 * 1024 * 1024;
                    }

                    @Override
                    public int getMaximumResponseBufferSizeInBytes() {
                        return 10 * 1024 * 1024;
                    }
                })
                .start();
    }
}