package co.com.pragma.api.mapper.config;

import co.com.pragma.api.mapper.ClientErrorEntryMapper;
import co.com.pragma.api.mapper.impl.ClientErrorEntryMapperImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientErrorEntryMapperConfig {

    @Bean
    public ClientErrorEntryMapper imageErrorEntryMapper(){
        return new ClientErrorEntryMapperImpl();
    }
}
