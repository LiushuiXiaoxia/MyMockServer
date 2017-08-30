package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.util.SampleUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

/**
 * CmdMainTest <br/>
 * Created by Leon on 2017-08-30.
 */
@Test(sequential = true)
public class CmdMainTest {

    @BeforeMethod
    public void before() {
        System.out.println("-----------------------------------------");
    }

    @Test
    public void testStart2() {
        // start with port & config
        String cmd = "mms -p 8001 -c mock";
        new CmdMain().execute(cmd.split(" "));
    }

    @Test
    public void testStart() {
        // start with port
        String cmd = "mms -p abc";
        new CmdMain().execute(cmd.split(" "));

        String cmd2 = "mms -p 8001";
        new CmdMain().execute(cmd2.split(" "));
    }

    @Test
    public void testInit() {
        // init
        String cmd = "mms -i";
        new CmdMain().execute(cmd.split(" "));
        new File(SampleUtil.TEMPLATE_FILE_NAME).deleteOnExit();

        String cmd2 = "mms -i -c mock";
        new CmdMain().execute(cmd2.split(" "));
    }

    @Test
    public void testHelp() {
        // help
        String cmd = "mms -h";
        new CmdMain().execute(cmd.split(" "));
    }

    @Test
    public void testError() {
        // error
        String cmd = "mms -s";
        new CmdMain().execute(cmd.split(" "));
    }
}