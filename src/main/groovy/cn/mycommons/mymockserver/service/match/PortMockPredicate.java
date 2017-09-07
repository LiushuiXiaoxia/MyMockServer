package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.function.Predicate;

/**
 * PortMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class PortMockPredicate implements Predicate<Mock> {

    private static final Logger LOGGER = Logger.getLogger(PortMockPredicate.class);

    private final URI uri;

    public PortMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        int port = mock.getRequest().getPort();
        LOGGER.debug("part = " + port);
        if (port > 0) {
            int port2 = uri.getPort();
            if (port2 == -1) {
                port2 = 80;
            }
            LOGGER.debug("port2 = " + port2);

            return port == port2;
        }
        return true;
    }
}