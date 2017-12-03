package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.app.Const;
import cn.mycommons.mymockserver.exception.MockException;
import cn.mycommons.mymockserver.util.HttpsCheck;
import cn.mycommons.mymockserver.util.MsgUtil;
import cn.mycommons.mymockserver.util.SampleUtil;
import com.google.common.base.Joiner;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import java.util.Arrays;
import java.util.List;

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

    private static final String OPTION_L = "l";
    private static final String OPTION_LEVEL = "level";

    private static final String OPTION_S = "s";
    private static final String OPTION_SSL = "ssl";

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
        options.addOption(OPTION_S, OPTION_SSL, false, "whether to enable ssl");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            // log
            configLog(cmd);

            // ssl
            boolean ssl = cmd.hasOption(OPTION_SSL);

            // port
            int intPort = parsePort(cmd);

            // path
            String path = parsePath(cmd);

            if (cmd.hasOption(OPTION_H)) {
                runHelp(options);
            } else if (cmd.hasOption(OPTION_I)) {
                runInit(path);
            } else {
                runStartServer(intPort, path, ssl);
            }
        } catch (ParseException | MockException e) {
            showHelp(e.getMessage(), options);
        }
    }

    private String parsePath(CommandLine cmd) {
        String path = Const.DEFAULT_PATH;
        if (cmd.hasOption(OPTION_C)) {
            path = cmd.getOptionValue(OPTION_C);
        }
        return path;
    }

    private int parsePort(CommandLine cmd) throws MockException {
        int intPort;
        String port = Const.DEFAULT_STRING_PORT;
        try {
            if (cmd.hasOption(OPTION_P)) {
                port = cmd.getOptionValue(OPTION_P);
            }
            intPort = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new MockException("port = " + port + " error");
        }
        return intPort;
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

    private void runStartServer(int port, String configPath, boolean ssl) {
        MsgUtil.msg();

        String authorityPath = HttpsCheck.initHttpsFiles();

        MyMockServer server = MyMockServer.getInstance();
        server.start(configPath, port, authorityPath, ssl);
    }

    private void runHelp(Options options) {
        showHelp("", options);
    }

    private void runInit(String path) {
        SampleUtil.init(path);
    }
}