package co.com.pragma.api.mapper.impl;

import co.com.pragma.api.mapper.ImageErrorEntryMapper;
import co.com.pragma.api.point.dto.ErrorDto;

import reactor.core.publisher.Mono;

public class ImageErrorEntryMapperImpl implements ImageErrorEntryMapper {

    private final Integer indexStackTrace = 0;
    private final Integer code = 400;

    @Override
    public Mono<ErrorDto> toDto(Throwable exception) {
        return Mono
                .just(exception.getStackTrace()[indexStackTrace])
                .flatMap(stackTraceElement ->
                        Mono.just(
                                ErrorDto.builder()
                                        .message( exception.getMessage())
                                        .code(code)
                                        .lineNumber(stackTraceElement.getLineNumber())
                                        .fileName(stackTraceElement.getFileName())
                                        .methodName(stackTraceElement.getMethodName())
                                        .className(stackTraceElement.getClassName())
                                        .build()
                        )
                );
    }
}
