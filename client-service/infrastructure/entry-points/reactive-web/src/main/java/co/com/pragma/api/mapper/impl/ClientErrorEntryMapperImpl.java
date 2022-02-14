package co.com.pragma.api.mapper.impl;

import co.com.pragma.api.mapper.ClientErrorEntryMapper;
import co.com.pragma.api.point.dto.ErrorDto;
import reactor.core.publisher.Mono;

public class ClientErrorEntryMapperImpl implements ClientErrorEntryMapper {

    @Override
    public Mono<ErrorDto> toDto(Throwable exception) {
        return Mono
                .just(exception.getStackTrace()[0])
                .flatMap(stackTraceElement ->
                        Mono.just(
                                ErrorDto.builder()
                                        .message( exception.getMessage())
                                        .code(400)
                                        .lineNumber(stackTraceElement.getLineNumber())
                                        .fileName(stackTraceElement.getFileName())
                                        .methodName(stackTraceElement.getMethodName())
                                        .className(stackTraceElement.getClassName())
                                        .build()
                        )
                );
    }
}
