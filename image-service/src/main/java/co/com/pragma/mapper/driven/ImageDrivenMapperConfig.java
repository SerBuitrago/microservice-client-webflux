package co.com.pragma.mapper.driven;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.pragma.mapper.driven.impl.ImageDrivenMapperImpl;

@Configuration
public class ImageDrivenMapperConfig {
	
	@Bean
	public ImageDrivenMapper imageDrivenMapper() {
		return new ImageDrivenMapperImpl();
	}

}
