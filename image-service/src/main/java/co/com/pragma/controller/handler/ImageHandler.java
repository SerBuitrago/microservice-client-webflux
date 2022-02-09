package co.com.pragma.controller.handler;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.mapper.entry.ImageEntryMapper;
import co.com.pragma.models.service.impl.ImageRepositoryAdapter;
import reactor.core.publisher.Mono;

import static co.com.pragma.controller.router.ImageRouter.path;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ImageHandler {

	@Autowired
	private ImageRepositoryAdapter imageUseCase;
	
	@Autowired
	private ImageEntryMapper hanlderMapper;
	
	public final static String FILE_NAME = "file";
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
		return serverRequest
				.multipartData()
				.flatMap(multipart -> {
					 Part part = multipart.toSingleValueMap().get(FILE_NAME);
					 if(part == null || part.content() == null)
						  return Mono.error(new Exception("No se ha recibido la imagen."));
					 return Mono.just(part)
							 	.cast(FilePart.class)
							 	.flatMap(file -> {
							 		FileHandler fileHandler = new FileHandler();
									return fileHandler.fileToImage(file);
							 	})
							 	.flatMap(hanlderMapper::toDomain)
							 	.flatMap(imageDomain -> 
							 			imageUseCase.save(imageDomain)
							 			            .flatMap(hanlderMapper::toDto)
							 			            .flatMap(imageSave -> ServerResponse.created(URI.create(path.concat("/").concat(imageSave.getId()))).contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(imageSave))));
				}).onErrorResume(error -> {
					LOGGER.error(error.getMessage());
					return ServerResponse.notFound().build();
				});
	}

	public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
		return imageUseCase
				.deleteById(serverRequest.pathVariable("id"))
				.flatMap(response -> ServerResponse.noContent().build())
				.onErrorResume(error -> ServerResponse.notFound().build());
	}
}
