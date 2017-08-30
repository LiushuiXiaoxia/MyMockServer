package cn.mycommons.mymockserver.bean.http

import cn.mycommons.mymockserver.bean.base.IBean

class Response implements IBean {

    int code = 200

    String version = "HTTP/1.1"

    Headers headers

    Body body
}