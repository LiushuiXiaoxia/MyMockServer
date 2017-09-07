package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

/**
 * SchemeMockPredicate <br/>
 * Created by Leon on 2017-08-29.
 */
public class SchemeMockPredicate implements Predicate<Mock> {

    private static final Logger LOGGER = Logger.getLogger(SchemeMockPredicate.class);

    private final URI uri;

    public SchemeMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        List<String> list = mock.getRequest().getScheme();
        String scheme = uri.getScheme();

        LOGGER.debug("scheme = " + list);
        LOGGER.debug("scheme2 = " + scheme);

        return list == null || list.isEmpty() || list.contains(scheme);
    }
}
