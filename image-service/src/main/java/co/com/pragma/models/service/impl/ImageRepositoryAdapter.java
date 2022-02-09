package co.com.pragma.models.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.mapper.driven.ImageDrivenMapper;
import co.com.pragma.models.document.ImageDocument;
import co.com.pragma.models.model.Image;
import co.com.pragma.models.service.ImageGateway;
import co.com.pragma.repository.ImageRespository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ImageRepositoryAdapter implements ImageGateway {
	
	@Autowired
	private ImageRespository imageRespository;
	
	@Autowired
	private ImageDrivenMapper imageDrivenMapper;
		
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
                    return Mono.just(imageDocument).flatMap(imageDrivenMapper::toDomain);
                });
    }

    @Override
    public Flux<Image> findAll() {
        return imageRespository
                .findAll()
                .doOnNext(image -> logger.info(image.toString()))
                .flatMap(imageDrivenMapper::toDomain);
    }

    @Override
    public Mono<Image> save(Image image) {
        return Mono.just(image)
                .flatMap(imageDrivenMapper::toDocument)
                .flatMap(imageRespository::save)
                .defaultIfEmpty(new ImageDocument())
                .flatMap(imageSave -> {
                    if(imageSave.getId() == null)
                        return Mono.error(new Exception("No se ha ".concat(" ").concat(" imagen.")));
                    return Mono.just(imageSave)
                            .flatMap(imageDrivenMapper::toDomain);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return findById(id)
                .then(imageRespository.deleteById(id).then())
                .onErrorResume(Mono::error);
    }

}
