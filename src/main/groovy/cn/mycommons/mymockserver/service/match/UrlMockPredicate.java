package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import io.netty.handler.codec.http.HttpRequest;

import java.util.function.Predicate;

/**
 * UrlMockPredicate <br/>
 * Created by Leon on 2017-11-19.
 */
public class UrlMockPredicate implements Predicate<Mock> {
    private HttpRequest originalRequest;

    public UrlMockPredicate(HttpRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public boolean test(Mock mock) {
        String uri = originalRequest.uri();
        String matchUrl = mock.getRequest().getUrl();
        if (matchUrl != null && matchUrl.length() != 0) {
            if (matchUrl.contains("*")) {
                matchUrl = matchUrl.replaceAll("\\*", ".+");
                return uri.matches(matchUrl);
            } else {
                return uri.contains(matchUrl);
            }
        }
        return false;
    }
}
