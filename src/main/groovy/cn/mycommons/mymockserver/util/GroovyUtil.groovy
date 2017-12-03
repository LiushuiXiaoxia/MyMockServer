package cn.mycommons.mymockserver.util

import cn.mycommons.mymockserver.MmsMain
import cn.mycommons.mymockserver.bean.Mock
import cn.mycommons.mymockserver.exception.MockParseException

import java.util.function.Consumer

/**
 * GroovyUtil <br/>
 * Created by Leon on 2017-12-03.
 */
class GroovyUtil {

    private static final String SCRIPT = """
import cn.mycommons.mymockserver.bean.Mock

def mock(Closure closure) {
    def mock = new Mock()
    closure.delegate = mock
    closure()
    result.add(mock)
}

"""

    static evaluate = { File file ->
        List<Mock> list = new ArrayList<>()
        try {
            def propertyName = "result"
            Binding binding = new Binding()
            binding.setProperty(propertyName, new ArrayList())
            GroovyShell shell = new GroovyShell(MmsMain.classLoader, binding)

            shell.evaluate(SCRIPT + file.text)

            def value = binding.getProperty(propertyName)
            if (value instanceof List) {
                int index = 0
                value.forEach(new Consumer<Mock>() {
                    @Override
                    void accept(Mock mock) {
                        mock.index = index++
                        mock.mockFile = file.getAbsolutePath()
                    }
                })
                list.addAll(value)
            }
        } catch (MissingMethodException e) {
            throw new MockParseException("${e.getMethod()} can not execute")
        } catch (Exception e2) {
            throw new MockParseException(e2)
        }
        return list
    }
}