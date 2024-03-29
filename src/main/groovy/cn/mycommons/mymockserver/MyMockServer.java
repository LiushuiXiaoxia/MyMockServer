package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.bean.http.Request;
import cn.mycommons.mymockserver.service.MockServerScan;
import cn.mycommons.mymockserver.service.ProxyAdapter;
import cn.mycommons.mymockserver.service.WatchFileService;
import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.mitm.Authority;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyMockServer <br/>
 * Created by Leon on 2017-08-29.
 */
public class MyMockServer {

    private static final Logger LOGGER = Logger.getLogger(MyMockServer.class);

    private static final MyMockServer INSTANCE = new MyMockServer();

    public static MyMockServer getInstance() {
        return INSTANCE;
    }

    private final List<Mock> configMock = new ArrayList<>();

    private String workspacePath;
    private int serverPort;
    private boolean ssl;

    private WatchFileService watchPathService;
    private MockServerScan mockServerScan;
    private HttpProxyServer httpProxyServer;

    private MyMockServer() {
    }

    void start(String path, int port, String authorityPath, boolean ssl) {
        this.workspacePath = path;
        this.serverPort = port;
        this.ssl = ssl;
        // this.authorityPath = authorityPath;

        watchPathService = new WatchFileService(path);
        mockServerScan = new MockServerScan(path, 1);

        watchPathService.setOnPathChangeCallback(() -> mockServerScan.reload());
        mockServerScan.setOnMockFileChange(new MockServerScan.OnMockFileChange() {

            @Override
            public void onChange(List<Mock> list) {
                configMock.clear();
                configMock.addAll(list);

                LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                configMock.forEach(mock -> {
                    Request r = mock.getRequest();

                    StringBuilder builder = new StringBuilder();
                    String str = r.getScheme().isEmpty() ? "[scheme]://" :
                            String.format("[%s]://", Joiner.on("/").join(r.getScheme()));

                    if (r.getUrl() != null) {
                        builder.append(r.getUrl());
                    } else {
                        builder.append(str)
                                .append(r.getHost() == null ? "{host}" : r.getHost())
                                .append(r.getPort() == 80 || r.getPort() <= 0 ? "/" : String.format(":%d/", r.getPort()))
                                .append(r.getPath() == null ? "{path}" : r.getPath());
                    }

                    String format = "Parse %s : %s \t\t %s -> %s";
                    String method = r.getMethod() == null ? "X" : r.getMethod();
                    LOGGER.info(String.format(format, method, "[ " + builder + " ]", mock.getMockFile(), mock.getDesc()));
                });
                LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }

            @Override
            public void onStop() {
                mockServerScan.stop();
            }
        });

        mockServerScan.start();
        watchPathService.startWatch();

        try {
            String organization = "LittleProxy-mitm";
            Authority authority = new Authority(
                    new File(authorityPath),
                    "littleproxy-mitm",
                    "Be Your Own Lantern".toCharArray(),
                    organization,
                    organization + ", describe proxy here",
                    "Certificate Authority",
                    organization,
                    organization + ", describe proxy purpose here, since Man-In-The-Middle is bad normally."
            );

            HttpProxyServerBootstrap bootstrap = DefaultHttpProxyServer.bootstrap()
                    .withPort(port)
                    .withAllowLocalOnly(false)
                    .withFiltersSource(new ProxyAdapter(this));

            if (ssl) {
                bootstrap.withManInTheMiddle(new CertificateSniffingMitmManager(authority));
            }

            httpProxyServer = bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Mock> getConfigMock() {
        return configMock;
    }

    public void stop() {
        httpProxyServer.stop();
        watchPathService.stopWatch();
    }

    public String getWorkspacePath() {
        return workspacePath;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isSsl() {
        return ssl;
    }
}