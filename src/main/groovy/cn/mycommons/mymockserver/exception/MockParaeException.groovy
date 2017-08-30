package cn.mycommons.mymockserver.exception

/**
 * MockParaeException <br/>
 * Created by Leon on 2017-08-27.
 */
class MockParaeException extends Exception {

    MockParaeException() {
    }

    MockParaeException(String var1) {
        super(var1)
    }

    MockParaeException(String var1, Throwable var2) {
        super(var1, var2)
    }

    MockParaeException(Throwable var1) {
        super(var1)
    }

    MockParaeException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}