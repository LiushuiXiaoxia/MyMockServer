package cn.mycommons.mymockserver.util;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * SampleUtilTest <br/>
 * Created by Leon on 2017-08-30.
 */
public class SampleUtilTest {

    @Test
    public void init() {
        String path = "./build";
        SampleUtil.init(path);

        File file = new File(path, SampleUtil.TEMPLATE_FILE_NAME);

        System.out.println("file = " + file.getAbsoluteFile());
        Assert.assertTrue(file.exists());

        file.deleteOnExit();
        file.getAbsoluteFile().getParentFile().deleteOnExit();
    }
}