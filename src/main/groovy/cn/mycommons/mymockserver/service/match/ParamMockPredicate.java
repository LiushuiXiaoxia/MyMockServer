package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.bean.http.Params;
import cn.mycommons.mymockserver.bean.http.Request;
import io.netty.handler.codec.http.HttpRequest;

import java.net.URI;
import java.util.Map;
import java.util.function.Predicate;

/**
 * ParamMockPredicate <br/>
 * Created by Leon on 2017-11-19.
 */
public class ParamMockPredicate implements Predicate<Mock> {

    private final HttpRequest originalRequest;

    public ParamMockPredicate(HttpRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public boolean test(Mock mock) {
        Request request = mock.getRequest();
        if (request != null) {
            Params params = request.getParams();
            if (params != null) {
                URI uri = URI.create(originalRequest.uri());
                String query = uri.getQuery();
                if (query != null && query.length() != 0) {

                    Map<String, Object> mockParam = params.getParam();
                    for (Map.Entry<String, Object> entry : mockParam.entrySet()) {
                        String p = entry.getKey() + "=" + entry.getValue();
                        if (!query.contains(p)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}