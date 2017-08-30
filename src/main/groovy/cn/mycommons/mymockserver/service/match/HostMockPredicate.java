package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.service.ProxyHttpFilters;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * HostMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class HostMockPredicate implements Predicate<Mock> {

    private final URI uri;

    public HostMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        String host = mock.getRequest().getHost();
        String host2 = uri.getHost();

        ProxyHttpFilters.LOGGER.debug("host = " + host);
        ProxyHttpFilters.LOGGER.debug("host2 = " + host2);

        return host == null || Objects.equals(host, host2);
    }
}