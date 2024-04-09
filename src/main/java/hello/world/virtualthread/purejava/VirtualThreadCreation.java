package hello.world.virtualthread.purejava;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualThreadCreation {

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: {}", Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: {}", Thread.currentThread());
        }
    };

    public static void main(String[] args) throws InterruptedException {
        log.info("1) main. thread: " + Thread.currentThread());

        Thread thread = createVirtualThreadStarted();
//        Thread thread = createVirtualThreadUnstarted();

        thread.join();
        log.info("2) main. thread: " + Thread.currentThread());
    }

    private static Thread createVirtualThreadUnstarted() {
        Thread thread = Thread.ofVirtual().name("myVirtual1").unstarted(runnable);
        thread.start();
        return thread;
    }

    private static Thread createVirtualThreadStarted() {
        Thread thread = Thread.ofVirtual().name("myVirtual").start(runnable);
        return thread;
    }
}
