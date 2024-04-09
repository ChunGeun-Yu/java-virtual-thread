package hello.world.virtualthread.purejava;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class ReviewPlatfomrThread {

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

//        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
//        thread.start();
//        thread.join();


        // TODO: thread factory


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(runnable);
//        executorService.close();

        log.info("2) main. thread: " + Thread.currentThread());
    }
}
