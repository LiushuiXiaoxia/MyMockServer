package cn.mycommons.mymockserver;

import org.apache.commons.cli.*;

/**
 * CmdSample <br/>
 * Created by Leon on 2017-09-05.
 */
public class CmdSample {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("i", "init", false, "init some path as mock workspace");
        options.addOption("p", "port", false, "init some path as mock workspace");
        options.addOption("c", "config", false, "init some path as mock workspace");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd;

        try {
            String s = "mms -p 8001 -c mock -i";
            System.out.println(s);
            cmd = parser.parse(options, s.split(" "));
            System.out.println(cmd);
            System.out.println(cmd.hasOption("c"));
            System.out.println(cmd.hasOption("config"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}