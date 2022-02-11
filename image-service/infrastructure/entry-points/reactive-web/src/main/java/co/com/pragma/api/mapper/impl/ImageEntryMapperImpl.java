package co.com.pragma.api.mapper.impl;

import co.com.pragma.api.point.dto.ImageDto;
import co.com.pragma.api.mapper.ImageEntryMapper;
import co.com.pragma.model.image.Image;
import reactor.core.publisher.Mono;

public class ImageEntryMapperImpl implements ImageEntryMapper {

    @Override
    public Mono<Image> toDomain(ImageDto imageDto) {
        if(imageDto == null)
            return Mono.empty();
        return Mono.just(
                    Image.builder()
                            .id(imageDto.getId())
                            .filename(imageDto.getFilename())
                            .contentType(imageDto.getContentType())
                            .content(imageDto.getContent())
                            .build()
                );
    }

    @Override
    public Mono<ImageDto> toDto(Image image) {
        if(image == null)
            return Mono.empty();
        return Mono.just(
                    ImageDto.builder()
                                .id(image.getId())
                                .filename(image.getFilename())
                                .contentType(image.getContentType())
                                .content(image.getContent())
                                .build()
                );
    }
}
