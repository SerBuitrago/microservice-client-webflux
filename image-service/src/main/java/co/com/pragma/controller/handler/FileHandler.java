package co.com.pragma.controller.handler;


import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

public class FileHandler {

	public static final String REGEX_RULES = "^01\\d{9}$|^1\\d{9}|^d{0}$";

	public Mono<byte[]> getLines(FilePart file) {
		System.out.println("Entrooooo");
		System.out.println(file.filename());
		System.out.println(file.content().collectList().block().toString());
		System.out.println(file.content().collectList().block().size());
		return DataBufferUtils.join(file.content())
				.map(data -> {
                	System.out.println("\n\nEntrooooo");
                    byte[] bytes = new byte[data.readableByteCount()];
                    data.read(bytes);
                    DataBufferUtils.release(data);
                    return bytes;
                });
	}
}
