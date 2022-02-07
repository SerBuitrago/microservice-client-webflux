package co.com.pragma.data.config;

import co.com.pragma.data.mapper.ClientHandlerMapper;
import co.com.pragma.data.mapper.impl.ClientHandlerMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientHandlerMapperConfig {

    @Bean
    public ClientHandlerMapper clientHandlerMapper(){
        return new ClientHandlerMapperImpl();
    }
}
