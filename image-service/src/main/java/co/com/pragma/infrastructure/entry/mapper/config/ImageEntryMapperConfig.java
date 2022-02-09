package co.com.pragma.infrastructure.entry.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.pragma.infrastructure.entry.mapper.ImageEntryMapper;
import co.com.pragma.infrastructure.entry.mapper.impl.ImageEntryMapperImpl;

@Configuration
public class ImageEntryMapperConfig {

	@Bean
	public ImageEntryMapper imageEntryMapper() {
		return new ImageEntryMapperImpl();
	}
}
