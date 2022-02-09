package co.com.pragma.controller.handler;

import java.net.URI;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.mapper.entry.ImageEntryMapper;
import co.com.pragma.models.dto.ImageDto;
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

	
	private final static Logger logger = LoggerFactory.getLogger(ImageHandler.class);

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
				.map(multipart -> multipart.toSingleValueMap().get("file"))
				.cast(FilePart.class)
				.flatMap(file -> {
					if(file == null) return Mono.error(new Exception("No se ha recibido la imagen"));
					return Mono
							.just(new ImageDto())
							.flatMap(imageDto -> {	
								FileHandler fileHandler = new FileHandler();
								imageDto.setContent(Base64.getEncoder().encodeToString(fileHandler.getLines(file).toProcessor().block()));
							 	imageDto.setFilename(file.filename());
							 	imageDto.setContentType(file.headers().getContentType().toString());
							 	return Mono.just(imageDto);
							});	
				}).flatMap(hanlderMapper::toDomain)
				.flatMap(imageDomain -> 
					imageUseCase.save(imageDomain)
					 .flatMap(hanlderMapper::toDto)
					 .flatMap(imageSave -> 
							ServerResponse.created(URI.create(path.concat("/").concat(imageSave.getId())))
							 .contentType(MediaType.APPLICATION_JSON_UTF8)
							 .body(fromObject(imageSave))
					)
				).onErrorResume(error -> {
					logger.error(error.getMessage());
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
