package co.com.pragma.mapper.entry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.pragma.mapper.entry.impl.ImageEntryMapperImpl;

@Configuration
public class ImageEntryMapperConfig {

	@Bean
	public ImageEntryMapper imageEntryMapper() {
		return new ImageEntryMapperImpl();
	}
}
