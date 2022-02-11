package co.com.pragma.api.mapper.config;

import co.com.pragma.api.mapper.ClientEntryMapper;
import co.com.pragma.api.mapper.impl.ClientEntryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientEntryMapperConfig {

    @Bean
    public ClientEntryMapper clientHandlerMapper(){
        return new ClientEntryMapperImpl();
    }
}
