package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.log4j.Logger;

import java.util.function.Predicate;

/**
 * MethodMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class MethodMockPredicate implements Predicate<Mock> {

    private static final Logger LOGGER = Logger.getLogger(MethodMockPredicate.class);

    private HttpRequest originalRequest;

    public MethodMockPredicate(HttpRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public boolean test(Mock mock) {
        String method = mock.getRequest().getMethod();
        String method2 = originalRequest.getMethod().name();

        LOGGER.debug("method = " + method);
        LOGGER.debug("method2 = " + method2);
        return method != null && (method.equalsIgnoreCase("*") || method.equalsIgnoreCase(method2));
    }
}
