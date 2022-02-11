package co.com.pragma.api.mapper;

import co.com.pragma.api.point.dto.ErrorDto;
import reactor.core.publisher.Mono;

public interface ImageErrorEntryMapper {

    Mono<ErrorDto> toDto(Throwable exception);
}
