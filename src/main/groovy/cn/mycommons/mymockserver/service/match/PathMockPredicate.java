package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.service.ProxyHttpFilters;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * PathMockPredicate <br/>
 * Created by Leon on 2017-08-30.
 */
public class PathMockPredicate implements Predicate<Mock> {

    private final URI uri;

    public PathMockPredicate(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean test(Mock mock) {
        String path = mock.getRequest().getPath();
        String path2 = uri.getPath();

        ProxyHttpFilters.LOGGER.debug("path = " + path);
        ProxyHttpFilters.LOGGER.debug("path2 = " + path2);

        return path == null || Objects.equals(path, path2);
    }
}
