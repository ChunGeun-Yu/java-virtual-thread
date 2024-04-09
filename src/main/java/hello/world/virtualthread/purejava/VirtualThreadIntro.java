package hello.world.virtualthread.purejava;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
@Slf4j
public class VirtualThreadIntro {

    private static final AtomicLong counter = new AtomicLong();

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: " + Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. count: " + counter.incrementAndGet() + " , thread: " + Thread.currentThread());
        }
    };

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        log.info("1) main. thread: " + Thread.currentThread());

//        try (ExecutorService executorService = Executors.newFixedThreadPool(10_000)) {
//            IntStream.range(0, 10_000).forEach(value -> {
//                executorService.submit(runnable);
//            });
//        }

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(value -> {
                executorService.submit(runnable);
            });
        }


        log.info("2) main. time: " + (System.currentTimeMillis()-startTime) + ". thread: " + Thread.currentThread());
    }
}
