package co.com.pragma.infrastructure.driven.adapter;

import co.com.pragma.application.gateway.ImageGateway;
import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.driven.mapper.ImageAdapterMapper;
import co.com.pragma.infrastructure.persistence.document.ImageDocument;
import co.com.pragma.infrastructure.persistence.repository.ImageRespository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageRepositoryAdapter implements ImageGateway {

    private final ImageRespository imageRespository;
    private final ImageAdapterMapper imageAdapterMapper;

    private final static Logger logger = LoggerFactory.getLogger(ImageRepositoryAdapter.class);

    @Override
    public Mono<Image> findById(String id) {
        return imageRespository
                .findById(id)
                .defaultIfEmpty(new ImageDocument())
                .doOnNext(image -> logger.info(image.toString()))
                .flatMap(imageDocument -> {
                    if(imageDocument.getId() == null)
                        return Mono.error(new Exception("No existe ninguna imagen con el id ".concat(id)));
                    return Mono.just(imageDocument)
                            .flatMap(imageAdapterMapper::toDomain);
                });
    }

    @Override
    public Flux<Image> findAll() {
        return imageRespository
                .findAll()
                .flatMap(imageAdapterMapper::toDomain);
    }

    @Override
    public Mono<Image> save(Image image) {
        return Mono.just(image)
                .flatMap(imageAdapterMapper::toDocument)
                .flatMap(imageDocument -> imageRespository.save(imageDocument))
                .defaultIfEmpty(new ImageDocument())
                .flatMap(imageSave -> {
                    if(imageSave.getId() == null)
                        return Mono.error(new Exception("No se ha ".concat(" ").concat(" imagen.")));
                    return Mono.just(imageSave)
                            .flatMap(imageAdapterMapper::toDomain);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return findById(id)
                .then(imageRespository.deleteById(id).then())
                .onErrorResume(error -> Mono.empty());
    }
}
