package co.com.pragma.models.service;

import co.com.pragma.models.model.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageGateway {

    Mono<Image> findById(String id);

    Flux<Image> findAll();

    Mono<Image> save(Image image);

    Mono<Void> deleteById(String id);
}
