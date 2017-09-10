package cn.mycommons.mymockserver.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * WatchFileService <br/>
 * Created by Leon on 2017-08-27.
 */
public class WatchFileService {

    private static final Logger LOGGER = Logger.getLogger(WatchFileService.class);

    private WatchService service;
    private final String watchPath;
    private OnPathChangeCallback onPathChangeCallback;

    public WatchFileService(String watchPath) {
        this.watchPath = watchPath;
    }

    public void setOnPathChangeCallback(OnPathChangeCallback onPathChangeCallback) {
        this.onPathChangeCallback = onPathChangeCallback;
    }

    public void startWatch() {
        stopWatch();

        new Thread(() -> {
            try {
                service = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(watchPath);
                path.register(service, ENTRY_MODIFY, ENTRY_DELETE, ENTRY_CREATE);

                while (true) {
                    WatchKey watchKey = service.take();
                    List<WatchEvent<?>> events = watchKey.pollEvents();
                    events.forEach(watchEvent -> {
                        Path p = (Path) watchEvent.context();
                        LOGGER.info(String.format("%s has happened change %s", p, watchEvent.kind().name()));
                    });
                    if (onPathChangeCallback != null) {
                        onPathChangeCallback.onChange();
                    }
                    if (!watchKey.reset()) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopWatch() {
        try {
            if (service != null) {
                service.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnPathChangeCallback {
        void onChange();
    }
}