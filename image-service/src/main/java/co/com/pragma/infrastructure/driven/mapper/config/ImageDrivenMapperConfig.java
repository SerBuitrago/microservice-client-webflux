package co.com.pragma.infrastructure.driven.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.pragma.infrastructure.driven.mapper.ImageDrivenMapper;
import co.com.pragma.infrastructure.driven.mapper.impl.ImageDrivenMapperImpl;

@Configuration
public class ImageDrivenMapperConfig {
	
	@Bean
	public ImageDrivenMapper imageDrivenMapper() {
		return new ImageDrivenMapperImpl();
	}

}
