package hello.world.virtualthread.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VirtualService {

    @Async
    public void async() {
        log.info("1) async. thread: {}", Thread.currentThread());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("2) async. thread: {}", Thread.currentThread());
    }
}
