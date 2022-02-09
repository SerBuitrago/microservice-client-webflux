package co.com.pragma.mapper.driven;

import co.com.pragma.models.document.ImageDocument;
import co.com.pragma.models.model.Image;
import reactor.core.publisher.Mono;

public interface ImageDrivenMapper {
	
	Mono<Image> toDomain(ImageDocument imageDocument);

    Mono<ImageDocument> toDocument(Image image);
}
