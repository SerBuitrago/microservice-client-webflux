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

    public Mono<ImageDto> fileToImage(FilePart file, String id){
        return Mono.just(ImageDto.builder()
                        .id(id)
                        .content(Base64.getEncoder().encodeToString(fileToByte(file).toProcessor().block()))
                        .filename(file.filename())
                        .contentType(file.headers().getContentType().toString())
                        .build());
    }
}
