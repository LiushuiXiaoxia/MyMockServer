package cn.mycommons.mymockserver.exception;

/**
 * MockException <br/>
 * Created by Leon on 2017-08-27.
 */
class MockException extends Exception {

    MockException() {
    }

    MockException(String var1) {
        super(var1)
    }

    MockException(String var1, Throwable var2) {
        super(var1, var2)
    }

    MockException(Throwable var1) {
        super(var1)
    }

    MockException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}