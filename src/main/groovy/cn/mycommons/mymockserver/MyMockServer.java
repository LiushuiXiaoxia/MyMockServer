package cn.mycommons.mymockserver;

import cn.mycommons.mymockserver.bean.Mock;
import cn.mycommons.mymockserver.bean.http.Request;
import cn.mycommons.mymockserver.service.MockServerScan;
import cn.mycommons.mymockserver.service.ProxyHttpFiltersSourceAdapter;
import cn.mycommons.mymockserver.service.WatchFileService;
import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import java.util.ArrayList;
import java.util.List;

/**
 * MyMockServer <br/>
 * Created by Leon on 2017-08-29.
 */
public class MyMockServer {

    private static final Logger LOGGER = Logger.getLogger(MyMockServer.class);

    private final List<Mock> configMock = new ArrayList<>();

    private WatchFileService watchPath;
    private MockServerScan mockServerScan;
    private HttpProxyServer httpProxyServer;

    public void start(String path, int port) {
        watchPath = new WatchFileService(path);
        mockServerScan = new MockServerScan(path, 1);

        watchPath.setOnPathChangeCallback(() -> mockServerScan.reload());
        mockServerScan.setOnMockFileChange(new MockServerScan.OnMockFileChange() {

            @Override
            public void onChange(List<Mock> list) {
                configMock.clear();
                configMock.addAll(list);

                LOGGER.info("=======================mock config change=======================");
                configMock.forEach(mock -> {
                    Request r = mock.getRequest();

                    StringBuilder builder = new StringBuilder();
                    builder.append(r.getScheme().isEmpty() ? "[scheme]://" : String.format("[%s]://", Joiner.on("/").join(r.getScheme())))
                            .append(r.getHost() == null ? "[host]" : r.getHost())
                            .append(r.getPort() == 80 || r.getPort() <= 0 ? "/" : String.format(":%d/", r.getPort()))
                            .append(r.getPath() == null ? "[path]/" : r.getPath());

                    String format = "Parse %s : %s \t\t %s -> %s";
                    String method = r.getMethod() == null ? "X" : r.getMethod();
                    LOGGER.info(String.format(format, method, "[ " + builder + " ]", mock.getMockFile(), mock.getDesc()));
                });
            }

            @Override
            public void onStop() {
                mockServerScan.stop();
            }
        });

        mockServerScan.start();
        watchPath.startWatch();

        httpProxyServer = DefaultHttpProxyServer.bootstrap()
                .withPort(port)
                .withFiltersSource(new ProxyHttpFiltersSourceAdapter(this))
                .start();
    }

    public List<Mock> getConfigMock() {
        return configMock;
    }

    public void stop() {
        httpProxyServer.stop();
        watchPath.stopWatch();
    }
}