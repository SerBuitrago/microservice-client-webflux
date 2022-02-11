package co.com.pragma.api.mapper.config;

import co.com.pragma.api.mapper.ImageErrorEntryMapper;
import co.com.pragma.api.mapper.impl.ImageErrorEntryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageErrorEntryMapperConfig {

    @Bean
    public ImageErrorEntryMapper imageErrorEntryMapper(){
        return new ImageErrorEntryMapperImpl();
    }
}
