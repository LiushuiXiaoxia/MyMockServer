package cn.mycommons.mymockserver.util

import org.apache.log4j.Logger

/**
 * SampleUtil <br/>
 * Created by Leon on 2017-08-30.
 */
class HttpsCheck {

    private static final Logger LOGGER = Logger.getLogger(HttpsCheck.class)

    public static final String[] HTTPS_FILES = [
            "littleproxy-mitm-localhost-cert.pem",
            "littleproxy-mitm-localhost-key.pem",
            "littleproxy-mitm-wrong_name-cert.pem",
            "littleproxy-mitm-wrong_name-key.pem",
            "littleproxy-mitm.p12",
            "littleproxy-mitm.pem"
    ]

    static String initHttpsFiles() {
        def workspace = new File(".").getAbsolutePath()
        if (check(workspace)) {
            return workspace
        }

        String s = HttpsCheck.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm()
        s = s.replace("file:", "")

        File execPath = new File(s)
        if (!execPath.isDirectory()) {
            execPath = execPath.getParentFile().getParentFile()
        }

        workspace = execPath.getAbsolutePath()
        if (!check(workspace)) {
            copyFile(execPath.getAbsolutePath())
        }

        return workspace
    }

    static boolean check(String path) {
        for (String s : HTTPS_FILES) {
            if (!new File(path, s).exists()) {
                return false
            }
        }
        return true
    }

    private static void copyFile(String path) {
        def loader = HttpsCheck.class.classLoader

        for (String s : HTTPS_FILES) {
            def file = new File(path, s)
            if (!file.exists()) {
                LOGGER.info("create file: " + file.getAbsolutePath())
                file.bytes = loader.getResourceAsStream(s).bytes
            }
        }
    }
}