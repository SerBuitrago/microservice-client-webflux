package co.com.pragma.controller.handler;


import java.util.Base64;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;

import co.com.pragma.models.dto.ImageDto;
import reactor.core.publisher.Mono;

public class FileHandler {

	public static final String REGEX_RULES = "^01\\d{9}$|^1\\d{9}|^d{0}$";

	public Mono<byte[]> fileToByte(FilePart file) {
		return DataBufferUtils.join(file.content())
				.map(data -> {
                    byte[] bytes = new byte[data.readableByteCount()];
                    data.read(bytes);
                    DataBufferUtils.release(data);
                    return bytes;
                });
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ImageDto> fileToImage(FilePart file){
		return Mono.just(new ImageDto())
				.flatMap(imageDto -> {
					byte [] imageByte = fileToByte(file).toProcessor().block();
					imageDto.setContent(Base64.getEncoder().encodeToString(imageByte));
					imageDto.setFilename(file.filename());
					imageDto.setContentType(file.headers().getContentType().toString());
					return Mono.just(imageDto);
				});
	}
}
