package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.util.SampleUtil;
import org.apache.commons.cli.*;

/**
 * CmdMain <br/>
 * Created by Leon on 2017-08-30.
 */
public class CmdMain {

    private static final String OPTION_H = "h";
    private static final String OPTION_HELP = "help";

    private static final String OPTION_C = "c";
    private static final String OPTION_CONFIG = "config";

    private static final String OPTION_I = "i";
    private static final String OPTION_INIT = "init";

    private static final String OPTION_P = "p";
    private static final String OPTION_PORT = "port";

    public static final int DEFAULT_PORT = 8001;
    public static final String DEFAULT_STRING_PORT = DEFAULT_PORT + "";
    public static final String DEFAULT_PATH = "./";


    public void execute(String[] args) {
        // System.out.println(Joiner.on(" ").join(args));

        Options options = new Options();
        options.addOption(OPTION_I, OPTION_INIT, false, "init some path as mock workspace");
        options.addOption(OPTION_P, OPTION_PORT, true, "mock server port, default is 8001");
        options.addOption(OPTION_C, OPTION_CONFIG, true, "mock server path, default is current directory");
        options.addOption(OPTION_H, OPTION_HELP, false, "output usage information");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption(OPTION_H)) {
                runHelp(options);
            } else if (cmd.hasOption(OPTION_I)) {
                String path = DEFAULT_PATH;
                if (cmd.hasOption(OPTION_C)) {
                    path = cmd.getOptionValue(OPTION_C);
                }
                runInit(path);
            } else if (cmd.hasOption(OPTION_C)) {
                String path = cmd.getOptionValue(OPTION_C);

                String port = DEFAULT_STRING_PORT;
                if (cmd.hasOption(OPTION_P)) {
                    port = cmd.getOptionValue(OPTION_P);
                }
                int intPort;
                try {
                    intPort = Integer.parseInt(port);
                    runStartServer(intPort, path);
                } catch (Exception e) {
                    showHelp("port = " + port + " error", options);
                }
            } else if (cmd.hasOption(OPTION_P)) {
                String port = cmd.getOptionValue(OPTION_P);
                int intPort;
                try {
                    intPort = Integer.parseInt(port);
                    String path = DEFAULT_PATH;
                    if (cmd.hasOption(OPTION_C)) {
                        path = cmd.getOptionValue(OPTION_C);
                    }

                    runStartServer(intPort, path);
                } catch (Exception e) {
                    showHelp("port = " + port + " error", options);
                }
            } else {
                runStartServer(DEFAULT_PORT, DEFAULT_PATH);
            }
        } catch (ParseException e) {
            showHelp(e.getMessage(), options);
        }
    }

    private void showHelp(String errorMsg, Options options) {
        if (errorMsg != null && errorMsg.length() > 0) {
            System.err.println(errorMsg);
        }
        new HelpFormatter().printHelp("mms [options]", options);
    }

    private void runStartServer(int port, String configPath) {
        MyMockServer server = new MyMockServer();
        server.start(configPath, port);
    }

    private void runHelp(Options options) {
        showHelp("", options);
    }

    private void runInit(String path) {
        SampleUtil.init(path);
    }
}
