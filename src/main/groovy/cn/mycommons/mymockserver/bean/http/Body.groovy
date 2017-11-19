package cn.mycommons.mymockserver.bean.http

import cn.mycommons.mymockserver.MyMockServer
import cn.mycommons.mymockserver.bean.base.IBean
import cn.mycommons.mymockserver.exception.MockParaeException

/**
 * Body <br/>
 * Created by Leon on 2017-08-27.
 */
class Body implements IBean {

    String text
    File textFile

    String json
    File jsonFile

    String xml
    File xmlFile

    String html
    File htmlFile

    byte[] bytes
    File bytesFile

    File file

    void text(String text) {
        this.text = text
    }

    void textFile(String file) {
        this.textFile = checkFile(file)
    }

    void json(String json) {
        this.json = json
    }

    void jsonFile(String file) {
        this.jsonFile = checkFile(file)
    }

    void xml(String xml) {
        this.xml = xml
    }

    void xmlFile(String file) {
        this.xmlFile = checkFile(file)
    }

    void bytes(byte[] bytes) {
        this.bytes = bytes
    }

    void bytesFile(String file) {
        this.bytesFile = checkFile(file)
    }

    void file(String file) {
        this.file = checkFile(file)
    }

    void html(String html) {
        this.html = html
    }

    void htmlFile(String file) {
        this.htmlFile = checkFile(file)
    }

    private static File checkFile(String file) {
        File f = new File(MyMockServer.instance.workspacePath, file)

        if (f.exists()) {
            return f
        } else {
            f = new File(file)
            if (f.exists()) {
                return f
            } else {
                throw new MockParaeException("${f.getAbsolutePath()} is not exists")
            }
        }
    }
}