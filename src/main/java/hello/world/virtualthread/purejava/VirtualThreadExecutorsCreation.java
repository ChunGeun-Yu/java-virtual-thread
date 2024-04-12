package hello.world.virtualthread.purejava;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
@Slf4j
public class VirtualThreadExecutorsCreation {

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: " + Thread.currentThread());
        }
    };

    public static void main(String[] args) throws InterruptedException {
        log.info("1) main. thread: " + Thread.currentThread());

        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 100; i++) {
                executorService.submit(runnable);
            }
        }

//        taskExecutor1();
//        taskExecutor2();
        antiPattern1();
//        antiPattern2();


        log.info("2) main. thread: " + Thread.currentThread());
    }

    private static void taskExecutor1() {
        //        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }
        // close 없으면 wait 하지 않음.
    }

    private static void taskExecutor2() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }
    }

    private static void antiPattern1() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newFixedThreadPool(1, factory)) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }
    }

    private static void antiPattern2() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newSingleThreadExecutor(factory)) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }
    }

}
