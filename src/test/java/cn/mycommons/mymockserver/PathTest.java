package cn.mycommons.mymockserver;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * PathTest <br/>
 * Created by Leon on 2017-09-10.
 */
public class PathTest {

    @Test
    public void test() {
        try {
            System.out.println(new File(".").getCanonicalFile());
            System.out.println(new File("./").getCanonicalFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new File(".").getAbsoluteFile());
        System.out.println(System.getProperty("user.dir"));

        String s = PathTest.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        System.out.println(s);
    }
}