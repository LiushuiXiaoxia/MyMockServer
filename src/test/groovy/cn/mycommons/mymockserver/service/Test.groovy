package cn.mycommons.mymockserver.service

/**
 * Test <br/>
 * Created by Leon on 2017-08-27.
 */
import cn.mycommons.mymockserver.bean.Mock

class Global {
    static def result = new ArrayList<>()
}

static def mock(Closure closure) {
    def mock = new Mock()
    closure.delegate = mock
    closure()
    Global.result.add(mock)
}

mock {
    desc "no 1"
    request {
    }
    response {
    }
}
mock {
    desc "no 2"
    request {
    }
    response {
    }
}

println "result = " + Global.result
return Global.result