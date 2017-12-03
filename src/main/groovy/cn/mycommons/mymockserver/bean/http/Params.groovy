package cn.mycommons.mymockserver.bean.http

import cn.mycommons.mymockserver.bean.base.IBean

/**
 * Params <br/>
 * Created by Leon on 2017-08-27.
 */
class Params implements IBean {

    private Map<String, Object> param = new LinkedHashMap()

    def param(String key, Object value) {
        param.put(key, value)
        return this
    }

    Map<String, Object> getParam() {
        return param
    }
}