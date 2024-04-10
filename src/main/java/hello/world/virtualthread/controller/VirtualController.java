package hello.world.virtualthread.controller;

import hello.world.virtualthread.service.VirtualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VirtualController {

    private final VirtualService virtualService;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sleep")
    public void sleep() throws InterruptedException {
        log.info("1) counter: {}, thread: {}", counter.incrementAndGet(), Thread.currentThread());
        Thread.sleep(5000);
        log.info("2) counter");
    }

    @GetMapping("/async")
    public void async() {
        log.info("async. thread: {}", Thread.currentThread());
        virtualService.async();
    }
}
