package co.com.pragma.api.client;

import co.com.pragma.data.mapper.ClientHandlerMapper;
import co.com.pragma.usecase.client.ClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientUseCase clientUseCase;

    private final ClientHandlerMapper clientHandlerMapper;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return clientUseCase
                .findById(serverRequest.pathVariable("id"))
                .flatMap(clientHandlerMapper::toDto)
                .flatMap(client ->
                    ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(fromObject(client))
                            .switchIfEmpty(ServerResponse.notFound().build())
                );
    }
}
