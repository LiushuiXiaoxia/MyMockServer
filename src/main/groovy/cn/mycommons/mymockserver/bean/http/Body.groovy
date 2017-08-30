package cn.mycommons.mymockserver.bean.http

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
        checkFile(file)
        this.textFile = new File(file)
    }

    void json(String json) {
        this.json = json
    }

    void jsonFile(String file) {
        checkFile(file)
        this.jsonFile = new File(file)
    }

    void xml(String xml) {
        this.xml = xml
    }

    void xmlFile(String file) {
        checkFile(file)
        this.xmlFile = new File(file)
    }

    void bytes(byte[] bytes) {
        this.bytes = bytes
    }

    void bytesFile(String file) {
        this.bytesFile = new File(file)
    }

    void file(String file) {
        checkFile(file)
        this.file = new File(file)
    }

    void html(String html) {
        this.html = html
    }

    void htmlFile(String file) {
        checkFile(file)
        this.htmlFile = new File(file)
    }

    private static void checkFile(String file) {
        File f = new File(file)
        if (!f.exists()) {
            throw new MockParaeException("${f.getAbsolutePath()} is not exists")
        }
    }
}