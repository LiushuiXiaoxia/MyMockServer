package cn.mycommons.mymockserver.util

import cn.mycommons.mymockserver.exception.MockParaeException
import org.apache.log4j.Logger
/**
 * DslUtil <br/>
 * Created by Leon on 2017-08-27.
 */
class DslUtil {

    private static final Logger LOGGER = Logger.getLogger(DslUtil.class)

    static def globalMethodMissing(Object obj, String name, def args) {
        def property = obj.hasProperty(name)
        if (isMatchMethodToField(obj, name, args)) {
            LOGGER.debug("name = " + name)
            def arg = args[0]
            if (arg instanceof Closure) {
                LOGGER.debug("property.type = ${property.type}")

                def value = property.type.newInstance()
                arg.delegate = value
                arg.resolveStrategy = Closure.DELEGATE_FIRST
                arg.call()
                obj.setProperty(name, value)
            } else {
                obj.setProperty(name, arg)
            }
        } else {
            throw new MockParaeException("${name} can not execute")
        }
    }

    static boolean isMatchMethodToField(obj, String name, args) {
        def property = obj.hasProperty(name)
        property != null && args.class.isArray()
    }
}