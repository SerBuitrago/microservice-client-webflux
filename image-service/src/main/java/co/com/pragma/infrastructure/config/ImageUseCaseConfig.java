package co.com.pragma.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.pragma.application.gateway.ImageGateway;
import co.com.pragma.application.usecase.ImageUseCase;

@Configuration
public class ImageUseCaseConfig {
	
	@Bean
	public ImageUseCase imageUseCase(ImageGateway imageGateway) {
		return new ImageUseCase(imageGateway);
	}
	
}
