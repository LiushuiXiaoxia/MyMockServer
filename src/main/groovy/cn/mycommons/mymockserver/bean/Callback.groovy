package cn.mycommons.mymockserver.bean

import cn.mycommons.mymockserver.util.DslUtil

/**
 * Callback <br/>
 * Created by Leon on 2017-08-27.
 */
class Callback {

    def methodMissing(String name, def args) {
        return DslUtil.globalMethodMissing(this, name, args)
    }
}