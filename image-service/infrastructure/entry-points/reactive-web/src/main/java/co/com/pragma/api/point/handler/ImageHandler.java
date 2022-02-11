package co.com.pragma.api.point.handler;

import co.com.pragma.api.mapper.ImageEntryMapper;
import co.com.pragma.usecase.image.ImageUseCase;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.net.URI;

import static co.com.pragma.api.point.router.ImageRouter.PATH;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@RequiredArgsConstructor
@Component
public class ImageHandler {

    private final ImageUseCase imageUseCase;
    private final ImageEntryMapper hanlderMapper;

    public final static String FILE_NAME = "file";
    public final static String ID_IMAGE = "id";

    public final static Logger LOGGER = LoggerFactory.getLogger(ImageHandler.class);

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return imageUseCase
                .findById(serverRequest.pathVariable("id"))
                .flatMap(hanlderMapper::toDto)
                .flatMap(image -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(image)))
                .onErrorResume(error -> ServerResponse.notFound().build());
    }

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return imageUseCase
                .findAll()
                .flatMap(hanlderMapper::toDto)
                .collectList()
                .flatMap(images -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(images)));
    }

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return saveOrUpdate(serverRequest, null);
    }

    @SuppressWarnings("deprecation")
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return saveOrUpdate(serverRequest, serverRequest.pathVariable(ID_IMAGE));
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        return imageUseCase
                .deleteById(serverRequest.pathVariable("id"))
                .flatMap(response -> ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.notFound().build());
    }

    protected Mono<ServerResponse> saveOrUpdate(ServerRequest serverRequest, String id){
        return  serverRequest.multipartData()
                .flatMap(multipart -> {
                    Part part = multipart.toSingleValueMap().get(FILE_NAME);
                    if(part == null || part.content() == null)
                        return Mono.error(new Exception("No se ha recibido la imagen."));
                    return Mono.just(part)
                            .cast(FilePart.class)
                            .flatMap(file -> {
                                FileHandler fileHandler = new FileHandler();
                                return fileHandler.fileToImage(file, id);
                            })
                            .flatMap(hanlderMapper::toDomain)
                            .flatMap(imageDomain ->
                                    imageUseCase.save(imageDomain)
                                            .flatMap(hanlderMapper::toDto)
                                            .flatMap(imageSave -> {
                                                if(id == null)
                                                    return ServerResponse
                                                            .created(URI.create(PATH.concat("/").concat(imageSave.getId())))
                                                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                            .body(fromObject(imageSave));
                                                return ServerResponse
                                                        .ok()
                                                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                        .body(fromObject(imageSave));
                                            }));

                }) .onErrorResume(error -> {
                    LOGGER.error(error.getMessage());
                    return ServerResponse.badRequest().body(fromObject(error));
                });
         }
}