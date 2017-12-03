package cn.mycommons.mymockserver.exception

/**
 * MockParseException <br/>
 * Created by Leon on 2017-08-27.
 */
class MockParseException extends Exception {

    MockParseException() {
    }

    MockParseException(String var1) {
        super(var1)
    }

    MockParseException(String var1, Throwable var2) {
        super(var1, var2)
    }

    MockParseException(Throwable var1) {
        super(var1)
    }

    MockParseException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}