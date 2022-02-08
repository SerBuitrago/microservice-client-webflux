package co.com.pragma.infrastructure.driven.mapper.impl;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.driven.mapper.ImageDrivenMapper;
import co.com.pragma.infrastructure.persistence.document.ImageDocument;
import reactor.core.publisher.Mono;

public class ImageDrivenMapperImpl implements ImageDrivenMapper{

	@Override
	public Mono<Image> toDomain(ImageDocument imageDocument) {
		if(imageDocument == null)
			return Mono.empty();
		return Mono.just(new Image())
				.flatMap(image-> {
					image.setId(imageDocument.getId());
					image.setFilename(imageDocument.getFilename());
					image.setContentType(imageDocument.getContentType());
					image.setContent(imageDocument.getContent());
					return Mono.just(image);
				});
	}

	@Override
	public Mono<ImageDocument> toDocument(Image image) {
		if(image == null)
			return Mono.empty();
		return Mono.just(new ImageDocument())
				.flatMap(imageDocument-> {
					imageDocument.setId(image.getId());
					imageDocument.setFilename(image.getFilename());
					imageDocument.setContentType(image.getContentType());
					imageDocument.setContent(image.getContent());
					return Mono.just(imageDocument);
				});
	}

}