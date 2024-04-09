package hello.world.virtualthread.purejava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class Pinning {

    // -Djdk.tracePinnedThreads=full  or -Djdk.tracePinnedThreads=short 를 통해  detect
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            synchronized (this) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("2) run. thread: " + Thread.currentThread());
        }
    };


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        log.info("1) main. thread: " + Thread.currentThread());

//        platform();
        virtual();

        log.info("2) main. time: " + (System.currentTimeMillis()-startTime) + " , thread: " + Thread.currentThread());
    }

    private static void virtual() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 20; i++) {
                Pinning pinning = new Pinning();
                executorService.submit(pinning.runnable);
            }
        }
    }

    private static void platform() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 20; i++) {
                Pinning pinning = new Pinning();
                executorService.submit(pinning.runnable);
            }
        }
    }

}
