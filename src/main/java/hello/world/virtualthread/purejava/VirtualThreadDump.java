package hello.world.virtualthread.purejava;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
@Slf4j
// jcmd <PID> Thread.dump_to_file -format=text <file>
public class VirtualThreadDump {

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: " + Thread.currentThread());
        }
    };

    public static void main(String[] args) throws InterruptedException {
        log.info("1) main. thread: " + Thread.currentThread());

        taskExecutor();

        log.info("2) main. thread: " + Thread.currentThread());
    }

    private static void taskExecutor() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 33; i++) {
                executorService.submit(runnable);
            }
        }
    }


}
