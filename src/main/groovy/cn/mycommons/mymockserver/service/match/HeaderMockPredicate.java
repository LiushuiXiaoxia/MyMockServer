package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.bean.http.Headers;
import cn.mycommons.mymockserver.bean.http.Request;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * HeaderMockPredicate <br/>
 * Created by Leon on 2017-11-19.
 */
public class HeaderMockPredicate implements Predicate<Mock> {

    private final HttpRequest originalRequest;

    public HeaderMockPredicate(HttpRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public boolean test(Mock mock) {
        Request request = mock.getRequest();
        if (request != null) {
            Headers headers = request.getHeaders();
            if (headers != null) {
                Map<String, Object> mockHeader = headers.getHeader();
                for (Map.Entry<String, Object> entry : mockHeader.entrySet()) {
                    String value = originalRequest.headers().get(entry.getKey());
                    if (!Objects.equals(value, entry.getValue())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}