package cn.mycommons.mymockserver.service

import cn.mycommons.mymockserver.MmsMain
import cn.mycommons.mymockserver.bean.Mock
import cn.mycommons.mymockserver.exception.MockException
import cn.mycommons.mymockserver.exception.MockParaeException

import java.util.function.Consumer


/**
 * MockServerScan <br/>
 * Created by Leon on 2017-08-27.
 */
class MockServerScan {

    static interface OnMockFileChange {

        void onChange(List<Mock> list)

        void onStop()
    }


    private String mockPath

    private int logLevel

    private OnMockFileChange onMockFileChange

    MockServerScan(String mockPath = "./", int logLevel) {
        if (!new File(mockPath).exists()) {
            throw new MockException("${mockPath} is not exists")
        }
        if (!new File(mockPath).isDirectory()) {
            throw new MockException("${mockPath} is not directory")
        }

        this.mockPath = mockPath
        this.logLevel = logLevel
    }

    void setOnMockFileChange(OnMockFileChange onMockFileChange) {
        this.onMockFileChange = onMockFileChange
    }

    void start() {
        scan()
    }

    void reload() {
        scan()
    }

    private void scan() {
        List<Mock> list = new ArrayList<>()
        File[] files = getAllMockFiles()
        Arrays.asList(files).sort().each {
            if (it.isFile()) {
                try {
                    def propertyName = "result"
                    Binding binding = new Binding()
                    binding.setProperty(propertyName, new ArrayList())
                    GroovyShell shell = new GroovyShell(MmsMain.classLoader, binding)
                    def script = """
import cn.mycommons.mymockserver.bean.Mock

def mock(Closure closure) {
    def mock = new Mock()
    closure.delegate = mock
    closure()
    result.add(mock)
}

${it.text}"""
                    shell.evaluate(script)
                    List<Mock> object = binding.getProperty(propertyName)
                    object.forEach(new Consumer<Mock>() {
                        @Override
                        void accept(Mock mock) {
                            mock.mockFile = it.getAbsolutePath()
                        }
                    })
                    list.addAll(object)
                } catch (MissingMethodException e) {
                    throw new MockParaeException("${e.getMethod()} can not execute")
                } catch (Exception e2) {
                    throw new MockParaeException(e2)
                }
            }
        }

        if (onMockFileChange != null) {
            onMockFileChange.onChange(list)
        }
    }

    private File[] getAllMockFiles() {
        return new File(mockPath).listFiles(
                new FilenameFilter() {
                    @Override
                    boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".groovy")
                    }
                })
    }

    void stop() {
        if (onMockFileChange != null) {
            onMockFileChange.onStop()
        }
    }
}