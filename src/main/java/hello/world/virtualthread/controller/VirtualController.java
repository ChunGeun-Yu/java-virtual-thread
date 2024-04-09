package hello.world.virtualthread.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class VirtualController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sleep")
    public void sleep() throws InterruptedException {
        log.info("1) counter: {}", counter.incrementAndGet());
        Thread.sleep(5000);
        log.info("2) counter");
    }

}
