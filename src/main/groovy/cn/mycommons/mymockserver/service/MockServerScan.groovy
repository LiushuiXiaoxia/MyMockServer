package cn.mycommons.mymockserver.service

import cn.mycommons.mymockserver.bean.Mock
import cn.mycommons.mymockserver.exception.MockException
import cn.mycommons.mymockserver.util.GroovyUtil

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
                def value = GroovyUtil.evaluate(it)
                list.addAll(value)
            }
        }

        if (onMockFileChange != null) {
            onMockFileChange.onChange(list)
        }
    }

    private File[] getAllMockFiles() {
        return new File(mockPath).listFiles(new FilenameFilter() {
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