package co.com.pragma.mongo.adapter.impl;

import co.com.pragma.model.image.Image;
import co.com.pragma.model.image.gateways.ImageGateway;
import co.com.pragma.mongo.document.ImageDocument;
import co.com.pragma.mongo.adapter.ImageMongoRepositoryAdapter;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageMongoRepositoryAdapterImpl implements ImageGateway {

    private final ImageMongoRepositoryAdapter imageMongoRepositoryAdapter;

    public final static Logger LOGGER = LoggerFactory.getLogger(ImageMongoRepositoryAdapterImpl.class);

    @Override
    public Mono<Image> findById(String id) {
        return imageMongoRepositoryAdapter
                .findById(id)
                .defaultIfEmpty(new Image())
                .doOnNext(image -> LOGGER.info(image.toString()))
                .flatMap(image -> {
                    if(image.getId() == null)
                        return Mono.error(new Exception("No existe ninguna imagen con el id ".concat(id)));
                    return Mono.just(image);
                });
    }

    @Override
    public Flux<Image> findAll() {
        return  imageMongoRepositoryAdapter
                .findAll()
                .doOnNext(image -> LOGGER.info(image.toString()));
    }

    @Override
    public Mono<Image> save(Image image) {
        return Mono.just(image)
                .flatMap(imageMongoRepositoryAdapter::save)
                .defaultIfEmpty(new Image())
                .flatMap(imageSave -> {
                    if(imageSave.getId() == null)
                        return Mono.error(new Exception("No se ha ".concat(" ").concat(" imagen.")));
                    return Mono.just(imageSave);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return findById(id)
                .then(imageMongoRepositoryAdapter.deleteById(id).then())
                .onErrorResume(Mono::error);
    }
}
