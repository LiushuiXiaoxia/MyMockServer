package cn.mycommons.mymockserver.util
/**
 * SampleUtil <br/>
 * Created by Leon on 2017-08-30.
 */
class SampleUtil {

    public static final String TEMPLATE_FILE_NAME = "template.groovy"

    static void init(String path) {
        def dir = new File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        def loader = SampleUtil.class.classLoader
        new File(dir, TEMPLATE_FILE_NAME).text = loader.getResourceAsStream(TEMPLATE_FILE_NAME).text
    }
}