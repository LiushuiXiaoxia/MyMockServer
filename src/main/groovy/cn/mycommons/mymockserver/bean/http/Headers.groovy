package cn.mycommons.mymockserver.bean.http

import cn.mycommons.mymockserver.bean.base.IBean

/**
 * Headers <br/>
 * Created by Leon on 2017-08-27.
 */
class Headers implements IBean {

    final Map<String, Object> header = new LinkedHashMap()

    def header(String key, Object value) {
        header.put(key, value)
        return this
    }

    def getHeaderValue(String key) {
        return header.get(key)
    }

    Map<String, Object> getHeader() {
        return header
    }
}