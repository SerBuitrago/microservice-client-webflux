package co.com.pragma.infrastructure.driven.mapper;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.persistence.document.ImageDocument;

import org.mapstruct.Mapper;

import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface ImageAdapterMapper {

    Mono<Image> toDomain(ImageDocument imageDocument);

    Mono<ImageDocument> toDocument(Image image);
}
