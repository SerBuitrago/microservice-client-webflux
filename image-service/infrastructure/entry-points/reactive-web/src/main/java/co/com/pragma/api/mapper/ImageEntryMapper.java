package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ImageDto;
import co.com.pragma.model.image.Image;

import reactor.core.publisher.Mono;

public interface ImageEntryMapper {

    Mono<Image> toDomain(ImageDto imageDto);

    Mono<ImageDto> toDto(Image image);
}
