package cn.mycommons.mymockserver.bean.base

import cn.mycommons.mymockserver.util.DslUtil;

/**
 * IBean <br/>
 * Created by Leon on 2017-08-27.
 */
trait IBean {

    def methodMissing(String name, def args) {
        return DslUtil.globalMethodMissing(this, name, args)
    }
}