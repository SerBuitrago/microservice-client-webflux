package co.com.pragma.api.point.handler;

import co.com.pragma.api.point.dto.ClientDto;
import co.com.pragma.api.mapper.ClientEntryMapper;
import co.com.pragma.model.TypeDocument;
import co.com.pragma.usecase.client.ClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static co.com.pragma.api.point.router.ClientRouter.pathClient;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientUseCase clientUseCase;

    private final ClientEntryMapper clientHandlerMapper;

    private final Validator validator;

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

    public Mono<ServerResponse> findByTypeDocumentAndDocument(ServerRequest serverRequest) {
        return clientUseCase
                .findByTypeDocumentAndDocument(TypeDocument.valueOf(serverRequest.pathVariable("typeDocument")), Long.parseLong(serverRequest.pathVariable("document")))
                .flatMap(clientHandlerMapper::toDto)
                .flatMap(client ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .body(fromObject(client))
                                .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest){
        return clientUseCase.findAll()
                .flatMap(clientHandlerMapper::toDto)
                .collectList()
                .flatMap(clients ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .body(fromObject(clients))
                                .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> findByAgeAll(ServerRequest serverRequest){
        return clientUseCase.findByAgeAll(Integer.parseInt(serverRequest.pathVariable("age")))
                .flatMap(clientHandlerMapper::toDto)
                .collectList()
                .flatMap(clients ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .body(fromObject(clients))
                                .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        return serverRequest
                .bodyToMono(ClientDto.class)
                .flatMap(client ->{
                    Errors errors = new BeanPropertyBindingResult(client, ClientDto.class.getName());
                    validator.validate(client, errors);
                    if(errors.hasErrors())
                        return Flux.fromIterable(errors.getFieldErrors())
                                .map(FieldError::getDefaultMessage)
                                .collectList()
                                .flatMap(list -> ServerResponse
                                                    .badRequest()
                                                    .body(fromObject(list)));
                    return clientUseCase
                            .save(clientHandlerMapper
                                    .toDocument(client)
                                    .block())
                            .flatMap(clientSave -> ServerResponse
                                                        .created(URI.create(pathClient.concat("/").concat(clientSave.getId())))
                                                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                        .body(fromObject(clientSave)));
                });
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest){
        return serverRequest
                .bodyToMono(ClientDto.class)
                .flatMap(client ->{
                    Errors errors = new BeanPropertyBindingResult(client, ClientDto.class.getName());
                    validator.validate(client, errors);
                    if(errors.hasErrors())
                        return Flux.fromIterable(errors.getFieldErrors())
                                .map(FieldError::getDefaultMessage)
                                .collectList()
                                .flatMap(list -> ServerResponse
                                                .badRequest()
                                                .body(fromObject(list)));
                    return clientUseCase.save(clientHandlerMapper
                                                .toDocument(client)
                                                .block()
                            ).flatMap(clientUpdate -> ServerResponse
                                                    .ok()
                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                    .body(fromObject(clientUpdate)));
                });
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        return clientUseCase.deleteById(serverRequest.pathVariable("id"))
                .flatMap(response -> ServerResponse
                                     .noContent()
                                     .build());

    }
}
