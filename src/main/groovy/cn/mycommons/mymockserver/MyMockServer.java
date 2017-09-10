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
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;

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

                LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                configMock.forEach(mock -> {
                    Request r = mock.getRequest();

                    StringBuilder builder = new StringBuilder();
                    String str = r.getScheme().isEmpty() ? "[scheme]://" :
                            String.format("[%s]://", Joiner.on("/").join(r.getScheme()));

                    builder.append(str)
                            .append(r.getHost() == null ? "[host]" : r.getHost())
                            .append(r.getPort() == 80 || r.getPort() <= 0 ? "/" : String.format(":%d/", r.getPort()))
                            .append(r.getPath() == null ? "[path]/" : r.getPath());

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
        watchPath.startWatch();

        try {
//            File file = new File("./keystore.p12");
//            ImpersonatingMitmManager manager;
//            if (file.exists()) {
//                CertificateAndKeySource existingCertificateSource =
//                        new KeyStoreFileCertificateSource("PKCS12", file, "privateKeyAlias", "password");
//
//                // configure the MitmManager to use the custom KeyStore source
//                manager = ImpersonatingMitmManager.builder()
//                        .rootCertificateSource(existingCertificateSource)
//                        .build();
//            } else {
//                // create a CA Root Certificate using default settings
//                RootCertificateGenerator rootCertificateGenerator = RootCertificateGenerator.builder().build();
//
//                // save the newly-generated Root Certificate and Private Key -- the .cer file can be imported
//                // directly into a browser
//                rootCertificateGenerator.saveRootCertificateAsPemFile(new File("./certificate.cer"));
//                rootCertificateGenerator.savePrivateKeyAsPemFile(new File("./private-key.pem"), "password");
//
//                // or save the certificate and private key as a PKCS12 keystore, for later use
//                rootCertificateGenerator.saveRootCertificateAndKey("PKCS12", file, "privateKeyAlias", "password");
//
//                // tell the ImpersonatingMitmManager  use the RootCertificateGenerator we just configured
//                manager = ImpersonatingMitmManager.builder().rootCertificateSource(rootCertificateGenerator)
//                        .rootCertificateSource(rootCertificateGenerator)
//                        .build();
//            }

            httpProxyServer = DefaultHttpProxyServer.bootstrap()
                    .withPort(port)
                    .withManInTheMiddle(new CertificateSniffingMitmManager())
                    .withFiltersSource(new ProxyHttpFiltersSourceAdapter(this))
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Mock> getConfigMock() {
        return configMock;
    }

    public void stop() {
        httpProxyServer.stop();
        watchPath.stopWatch();
    }
}