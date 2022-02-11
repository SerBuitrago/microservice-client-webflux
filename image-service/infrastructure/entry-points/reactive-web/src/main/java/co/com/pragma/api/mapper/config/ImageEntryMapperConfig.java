package co.com.pragma.api.mapper.config;

import co.com.pragma.api.mapper.ImageEntryMapper;
import co.com.pragma.api.mapper.impl.ImageEntryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageEntryMapperConfig {

    @Bean
    public ImageEntryMapper imageEntryMapper(){
        return new ImageEntryMapperImpl();
    }
}
