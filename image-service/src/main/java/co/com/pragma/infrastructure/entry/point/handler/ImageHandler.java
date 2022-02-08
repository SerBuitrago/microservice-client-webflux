package co.com.pragma.infrastructure.entry.point.handler;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.application.usecase.ImageUseCase;
import co.com.pragma.infrastructure.entry.mapper.ImageHanlderMapper;
import co.com.pragma.infrastructure.entry.point.dto.ImageDto;

import static co.com.pragma.infrastructure.entry.point.router.ImageRouter.path;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class ImageHandler {

	private final ImageUseCase imageUseCase;
	private final ImageHanlderMapper hanlderMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(ImageHandler.class);

	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> findById(ServerRequest serverRequest) {
		return imageUseCase
				.findById(serverRequest.pathVariable("id"))
				.flatMap(hanlderMapper::toDto)
				.flatMap(image -> ServerResponse
									.ok()
									.contentType(MediaType.APPLICATION_JSON_UTF8)
									.body(fromObject(image))
				).switchIfEmpty(ServerResponse.notFound().build());
	}

	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
		return imageUseCase.findAll()
				.flatMap(hanlderMapper::toDto)
				.collectList()
				.flatMap(images -> ServerResponse
                					.ok()
                					.contentType(MediaType.APPLICATION_JSON_UTF8)
                					.body(fromObject(images))
                ).switchIfEmpty(ServerResponse
                					.notFound()
                					.build());
	}

	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> save(ServerRequest serverRequest) {
		return serverRequest
				.multipartData()
				.map(multipart -> multipart.toSingleValueMap().get("file"))
				.cast(MultipartFile.class)
				.flatMap(file -> {
					if(file.isEmpty())
						return ServerResponse.notFound().build();
					return Mono.just(new ImageDto())
						.flatMap(image -> {
							byte[] imageBytes = null;
							
							try {imageBytes = file.getBytes();}catch (IOException e) {logger.error(e.getMessage());}
							
							if(imageBytes == null)
								return ServerResponse.notFound().build();
							
							image.setFilename(file.getOriginalFilename());
							image.setContentType(file.getContentType());
							image.setContent(Base64.getEncoder().encodeToString(imageBytes));
							
							return Mono.just(image)
									.flatMap(hanlderMapper::toDomain)
									.flatMap(imageDomain ->
										imageUseCase
											.save(imageDomain)
											.flatMap(hanlderMapper::toDto)
											.flatMap(imageSave -> ServerResponse
																	.created(URI.create(path.concat("/").concat(imageSave.getId())))
																	.contentType(MediaType.APPLICATION_JSON_UTF8)
																	.body(fromObject(imageSave))
											).switchIfEmpty(ServerResponse
																.notFound()
																.build())
							);
						});	 
				});
	}

	public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
		return imageUseCase
				.deleteById(serverRequest.pathVariable("id"))
				.flatMap(response -> ServerResponse
                        				.noContent()
                        				.build())
				.onErrorResume(error -> ServerResponse
											.notFound()
											.build());
	}
}
