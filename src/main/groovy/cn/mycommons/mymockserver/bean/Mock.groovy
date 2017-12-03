package cn.mycommons.mymockserver.bean

import cn.mycommons.mymockserver.bean.base.IBean
import cn.mycommons.mymockserver.bean.http.Request
import cn.mycommons.mymockserver.bean.http.Response
import com.google.gson.GsonBuilder

class Mock implements IBean {

    boolean enable = true

    String mockFile

    // mock config file index
    int index

    String desc

    Request request

    Response response

    Control control

    Callback callback

    @Override
    String toString() {
        new GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}