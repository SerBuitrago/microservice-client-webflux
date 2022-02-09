package co.com.pragma.mapper.entry;

import co.com.pragma.models.dto.ImageDto;
import co.com.pragma.models.model.Image;
import reactor.core.publisher.Mono;

public interface ImageEntryMapper {
	
	Mono<Image> toDomain(ImageDto imageDto);

    Mono<ImageDto> toDto(Image image);
}
