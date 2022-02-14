package co.com.pragma.api.point.handler;

import co.com.pragma.api.mapper.ClientErrorEntryMapper;
import co.com.pragma.api.point.dto.ClientDto;
import co.com.pragma.api.mapper.ClientEntryMapper;
import co.com.pragma.api.point.dto.ClientTypeDocumentAndDocumentDto;
import co.com.pragma.api.point.dto.ErrorDto;
import co.com.pragma.model.TypeDocument;
import co.com.pragma.usecase.client.ClientUseCase;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.com.pragma.api.point.router.ClientRouter.PATH;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientUseCase clientUseCase;
    private final ClientEntryMapper clientMapper;
    private final ClientErrorEntryMapper clientErrorMapper;

    private final Validator validator;

    private final String nameIdClient = "id";
    private final String nameTypeDocumentClient = "typeDocument";
    private final String nameDocumentClient = "document";
    private final String nameAgeClient= "age";
    private final String nameError = "error";
    private final Integer indexError = 0;

    public final static Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return clientUseCase
                .findById(serverRequest.pathVariable(nameIdClient))
                .flatMap(clientMapper::toDto)
                .flatMap(client -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(client)))
                .onErrorResume(error -> {
                    ErrorDto errorDto = clientErrorMapper.toDto(error).toProcessor().block();
                    LOGGER.error(errorDto.toString());
                    return ServerResponse.badRequest().body(fromObject(errorDto));
                });
    }

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> findByTypeDocumentAndDocument(ServerRequest serverRequest) {
        return Mono.just(ClientTypeDocumentAndDocumentDto.builder().typeDocument(TypeDocument.valueOf(serverRequest.pathVariable(nameTypeDocumentClient))).document(Long.parseLong(serverRequest.pathVariable(nameDocumentClient))).build())
                .flatMap(clientTypeDocumentAndDocumentDto -> {
                    Map<String, Mono<ServerResponse>> contains= containsErrors(clientTypeDocumentAndDocumentDto, new BeanPropertyBindingResult(clientTypeDocumentAndDocumentDto, ClientTypeDocumentAndDocumentDto.class.getName()));
                    if(!contains.isEmpty())
                        return contains.get(indexError);
                    return Mono.just(clientUseCase.findByTypeDocumentAndDocument(clientTypeDocumentAndDocumentDto.getTypeDocument(),clientTypeDocumentAndDocumentDto.getDocument()))
                               .flatMap(client -> clientMapper.toDto(client.block()))
                               .flatMap(client -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(client)));
                }).onErrorResume(error -> {
                    ErrorDto errorDto = clientErrorMapper.toDto(error).toProcessor().block();
                    LOGGER.error(errorDto.toString());
                    return ServerResponse.badRequest().body(fromObject(errorDto));
                });
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest){
        return clientUseCase
                .findAll()
                .flatMap(clientMapper::toDto)
                .collectList()
                .flatMap(clients -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(clients)));
    }

    public Mono<ServerResponse> findByAgeAll(ServerRequest serverRequest){
        return clientUseCase
                .findByAgeAll(Integer.parseInt(serverRequest.pathVariable(nameAgeClient)))
                .flatMap(clientMapper::toDto)
                .collectList()
                .flatMap(clients -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(clients)));
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        return serverRequest
                .bodyToMono(ClientDto.class)
                .flatMap(client ->{
                    Map<String, Mono<ServerResponse>> errors = containsErrors(client, new BeanPropertyBindingResult(client, ClientDto.class.getName()));
                    if(!errors.isEmpty())
                        return errors.get(indexError);
                    return clientUseCase
                            .save(clientMapper.toDocument(client).block())
                            .flatMap(clientSave -> ServerResponse.created(URI.create(PATH.concat("/").concat(clientSave.getId()))).contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(clientSave)));
                });
    }

    public Mono<ServerResponse> updateById(ServerRequest serverRequest){
        return Mono.empty();
    }

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        return clientUseCase
                .deleteById(serverRequest.pathVariable(nameIdClient))
                .flatMap(response -> ServerResponse.noContent().build())
                .onErrorResume(error -> {
                    ErrorDto errorDto = clientErrorMapper.toDto(error).toProcessor().block();
                    LOGGER.error(errorDto.toString());
                    return ServerResponse.badRequest().body(fromObject(errorDto));
                });
    }

    protected <A> Map<String, Mono<ServerResponse>> containsErrors(A entity, Errors errors){
        Map<String, Mono<ServerResponse>> map = new HashMap<>();
        validator.validate(entity, errors);
        if(errors.hasErrors())
            map.put(nameError, Flux.fromIterable(errors.getFieldErrors())
                                 .map(FieldError::getDefaultMessage)
                                 .collectList()
                                 .flatMap(list -> ServerResponse.badRequest().body(fromObject(list)))
            );
        return map;
    }
}
