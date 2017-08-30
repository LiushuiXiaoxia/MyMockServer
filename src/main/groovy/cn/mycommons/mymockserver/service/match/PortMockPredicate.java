package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.service.ProxyHttpFilters;

import java.net.URI;
import java.util.function.Predicate;

/**
 * PortMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class PortMockPredicate implements Predicate<Mock> {
    private final URI uri;

    public PortMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        int port = mock.getRequest().getPort();
        ProxyHttpFilters.LOGGER.debug("part = " + port);
        if (port > 0) {
            int port2 = uri.getPort();
            if (port2 == -1) {
                port2 = 80;
            }
            ProxyHttpFilters.LOGGER.debug("port2 = " + port2);

            return port == port2;
        }
        return true;
    }
}