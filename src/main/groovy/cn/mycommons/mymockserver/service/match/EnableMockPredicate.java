package cn.mycommons.mymockserver.service.match;

import cn.mycommons.mymockserver.bean.Mock;
import org.apache.log4j.Logger;

import java.util.function.Predicate;

/**
 * EnableMockPredicate <br/>
 * Created by Leon on 2017-09-08.
 */
public class EnableMockPredicate implements Predicate<Mock> {

    private static final Logger LOGGER = Logger.getLogger(EnableMockPredicate.class);

    @Override
    public boolean test(Mock mock) {
        boolean enable = mock.getEnable();

        LOGGER.debug("enable = " + enable);

        return enable;
    }
}