package co.com.pragma.infrastructure.driven.mapper;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.persistence.document.ImageDocument;
import reactor.core.publisher.Mono;

public interface ImageDrivenMapper {
	
	Mono<Image> toDomain(ImageDocument imageDocument);

    Mono<ImageDocument> toDocument(Image image);
}
