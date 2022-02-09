package co.com.pragma.application.usecase;

import co.com.pragma.application.gateway.ImageGateway;
import co.com.pragma.domain.Image;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ImageUseCase implements ImageGateway{
	
	private final ImageGateway imageGateway;

	@Override
	public Mono<Image> findById(String id) {
		return imageGateway.findById(id);
	}

	@Override
	public Flux<Image> findAll() {
		return imageGateway.findAll();
	}

	@Override
	public Mono<Image> save(Image image) {
		return imageGateway.save(image);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return imageGateway.deleteById(id);
	}
}
