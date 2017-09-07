package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * HostMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class HostMockPredicate implements Predicate<Mock> {

    private static final Logger LOGGER = Logger.getLogger(HostMockPredicate.class);

    private final URI uri;

    public HostMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        String host = mock.getRequest().getHost();
        String host2 = uri.getHost();

        LOGGER.debug("host = " + host);
        LOGGER.debug("host2 = " + host2);

        return host == null || Objects.equals(host, host2);
    }
}