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
        return Mono.just(new Image())
                .flatMap(image-> {
                    image.setId(imageDto.getId());
                    image.setFilename(imageDto.getFilename());
                    image.setContentType(imageDto.getContentType());
                    image.setContent(imageDto.getContent());
                    return Mono.just(image);
                });
    }

    @Override
    public Mono<ImageDto> toDto(Image image) {
        if(image == null)
            return Mono.empty();
        return Mono.just(new ImageDto())
                .flatMap(imageDto-> {
                    imageDto.setId(image.getId());
                    imageDto.setFilename(image.getFilename());
                    imageDto.setContentType(image.getContentType());
                    imageDto.setContent(image.getContent());
                    return Mono.just(imageDto);
                });
    }
}
