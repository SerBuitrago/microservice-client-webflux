package co.com.pragma.infrastructure.entry.mapper;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.entry.point.dto.ImageDto;

import reactor.core.publisher.Mono;

public interface ImageHanlderMapper {

    Mono<Image> toDomain(ImageDto imageDto);

    Mono<ImageDto> toDto(Image image);
}
