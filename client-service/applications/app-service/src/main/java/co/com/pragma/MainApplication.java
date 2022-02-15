package co.com.pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableEurekaClient
@EnableReactiveFeignClients
@SpringBootApplication
public class MainApplication {
    public static void main(String ... args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
