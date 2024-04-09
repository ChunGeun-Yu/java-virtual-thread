package hello.world.virtualthread.purejava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class PerformanceTest {

    private static final Runnable ioBoundRunnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: " + Thread.currentThread());
        }
    };

    private static final Runnable cpuBoundRunnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            long sum = 0;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sum = sum + i;
            }
            log.info("2) run. sum: " + sum + " , thread: " + Thread.currentThread());
        }
    };

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        log.info("1) main. thread: " + Thread.currentThread());

//        virtualThreadWithIoBound();
//        platformThreadWithIoBound();

//        platformThreadWithCpuBound();
//        virtualThreadWithCpuBound();

        log.info("2) main. time: " + (System.currentTimeMillis()-startTime) + " , thread: " + Thread.currentThread());
    }

    private static void platformThreadWithIoBound() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(10000)) {
            for (int i = 0; i < 10000; i++) {
                executorService.submit(ioBoundRunnable);
            }
        }
    }

    private static void virtualThreadWithIoBound() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 10000; i++) {
                executorService.submit(ioBoundRunnable);
            }
        }
    }

    private static void platformThreadWithCpuBound() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < 100; i++) {
                executorService.submit(cpuBoundRunnable);
            }
        }
    }

    private static void virtualThreadWithCpuBound() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 100; i++) {
                executorService.submit(cpuBoundRunnable);
            }
        }
    }
}
