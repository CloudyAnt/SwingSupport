package pri.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MExecutor {
    private MExecutor() {}

    private static ExecutorService service;

    public static ExecutorService getExecutor() {
        if (service == null || service.isShutdown()) {
            service = Executors.newCachedThreadPool();
        }
        return service;
    }

    public static void execute(Runnable task) {
        getExecutor().execute(task);
    }

    // todo How to make supported program not-sense with shutdown ?
    public static void shutdown() {
        if (service != null) {
            service.shutdown();
        }
    }
}