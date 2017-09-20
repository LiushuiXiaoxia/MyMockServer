package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.app.Const;
import cn.mycommons.mymockserver.util.HttpsCheck;
import cn.mycommons.mymockserver.util.MsgUtil;
import cn.mycommons.mymockserver.util.SampleUtil;
import com.google.common.base.Joiner;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * CmdMain <br/>
 * Created by Leon on 2017-08-30.
 */
public class CmdMain {

    private static final Logger LOGGER = Logger.getLogger(CmdMain.class);

    private static final String OPTION_H = "h";
    private static final String OPTION_HELP = "help";

    private static final String OPTION_C = "c";
    private static final String OPTION_CONFIG = "config";

    private static final String OPTION_I = "i";
    private static final String OPTION_INIT = "init";

    private static final String OPTION_P = "p";
    private static final String OPTION_PORT = "port";

    private static final String OPTION_L = "l";
    private static final String OPTION_LEVEL = "level";

    private static final List<String> LOGS = Arrays.asList(
            "ALL", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF", "TRACE"
    );


    public void execute(String[] args) {
        // System.out.println(Joiner.on(" ").join(args));

        Options options = new Options();
        options.addOption(OPTION_I, OPTION_INIT, false, "init some path as mock workspace");
        options.addOption(OPTION_P, OPTION_PORT, true, "mock server port, default is " + Const.DEFAULT_PORT);
        options.addOption(OPTION_C, OPTION_CONFIG, true, "mock server path, default is current directory");
        options.addOption(OPTION_H, OPTION_HELP, false, "output usage information");
        options.addOption(OPTION_L, OPTION_LEVEL, true, "log level " + Joiner.on(",").join(LOGS));

        CommandLineParser parser = new PosixParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            // log
            configLog(cmd);

            if (cmd.hasOption(OPTION_H)) {
                runHelp(options);
            } else if (cmd.hasOption(OPTION_I)) {
                String path = Const.DEFAULT_PATH;
                if (cmd.hasOption(OPTION_C)) {
                    path = cmd.getOptionValue(OPTION_C);
                }
                runInit(path);
            } else if (cmd.hasOption(OPTION_C)) {
                String path = cmd.getOptionValue(OPTION_C);

                String port = Const.DEFAULT_STRING_PORT;
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
                    String path = Const.DEFAULT_PATH;
                    if (cmd.hasOption(OPTION_C)) {
                        path = cmd.getOptionValue(OPTION_C);
                    }

                    runStartServer(intPort, path);
                } catch (Exception e) {
                    showHelp("port = " + port + " error", options);
                }
            } else {
                runStartServer(Const.DEFAULT_PORT, Const.DEFAULT_PATH);
            }
        } catch (ParseException e) {
            showHelp(e.getMessage(), options);
        }
    }

    private void configLog(CommandLine cmd) {
        String logLevel = Const.DEFAULT_LOG_LEVEL;
        if (cmd.hasOption(OPTION_L)) {
            logLevel = cmd.getOptionValue(OPTION_L);
        }
        Level level = Level.toLevel(logLevel, Level.INFO);
        LogManager.getRootLogger().setLevel(level);

        System.out.println("Log level : " + level);
    }

    private void showHelp(String errorMsg, Options options) {
        if (errorMsg != null && errorMsg.length() > 0) {
            System.err.println(errorMsg);
        }
        new HelpFormatter().printHelp("mms [options]", options);
    }

    private void runStartServer(int port, String configPath) {
        MsgUtil.msg();

        String authorityPath = HttpsCheck.initHttpsFiles();

        MyMockServer server = new MyMockServer();
        server.start(configPath, port, authorityPath);
    }

    private void runHelp(Options options) {
        showHelp("", options);
    }

    private void runInit(String path) {
        SampleUtil.init(path);
    }
}
