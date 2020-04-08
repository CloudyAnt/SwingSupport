package pri.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MExecutor {
    private MExecutor() {}

    private static ExecutorService service;
    public static void execute(Runnable task) {
        if (service == null || service.isShutdown()) {
            service = Executors.newCachedThreadPool();
        }
        service.execute(task);
    }

    public static void shutdown() {
        service.shutdown();
    }
}