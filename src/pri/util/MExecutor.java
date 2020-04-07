package pri.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MExecutor {
    private MExecutor() {}

    private static ExecutorService service = Executors.newCachedThreadPool();
    public static void execute(Runnable task) {
        service.execute(task);
    }
}