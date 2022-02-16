package co.com.pragma.mongo.feign.config;

import co.com.pragma.mongo.feign.ImageFeignClient;

import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactivefeign.ReactiveOptions;
import reactivefeign.client.log.DefaultReactiveLogger;
import reactivefeign.client.log.ReactiveLoggerListener;
import reactivefeign.webclient.WebReactiveOptions;

import java.time.Clock;

@Configuration
public class FeignConfig {

    @Bean
    public ReactiveOptions reactiveOptions() {
        return new WebReactiveOptions.Builder()
                    .setReadTimeoutMillis(2000)
                    .setWriteTimeoutMillis(2000)
                    .setConnectTimeoutMillis(2000)
                    .build();
    }

    @Bean
    public ReactiveLoggerListener loggerListener() {
        return new DefaultReactiveLogger(Clock.systemUTC());
    }
}
