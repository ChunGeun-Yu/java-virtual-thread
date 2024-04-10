package hello.world.virtualthread.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Slf4j
public class ExecutorConfig {

    @Primary
    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(SimpleAsyncTaskExecutorBuilder builder) {
        return builder.build();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setThreadNamePrefix("myThreadPool-");
        // TODO: more setting...
        return threadPoolTaskExecutor;
    }

}
