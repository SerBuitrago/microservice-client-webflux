package co.com.pragma.api.point.handler;

import co.com.pragma.api.point.dto.ImageDto;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

import java.util.Base64;

public class FileHandler {

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
        return fileToImage(file, null);
    }

    public Mono<ImageDto> fileToImage(FilePart file, String id){
        return Mono.just(new ImageDto())
                .flatMap(imageDto -> {
                    byte [] imageByte = fileToByte(file).toProcessor().block();
                    imageDto.setId(id);
                    imageDto.setContent(Base64.getEncoder().encodeToString(imageByte));
                    imageDto.setFilename(file.filename());
                    imageDto.setContentType(file.headers().getContentType().toString());
                    return Mono.just(imageDto);
                });
    }
}
