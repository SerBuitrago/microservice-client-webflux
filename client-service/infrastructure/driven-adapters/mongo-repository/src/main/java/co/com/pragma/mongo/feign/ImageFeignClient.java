package co.com.pragma.mongo.feign;

import co.com.pragma.mongo.feign.config.FeignConfig;
import co.com.pragma.mongo.feign.dto.ImageDto;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import static co.com.pragma.mongo.feign.config.FeignUtil.*;


@ReactiveFeignClient(name = IMAGE_SERVICE, configuration = FeignConfig.class)
public interface ImageFeignClient {

    @GetMapping(value = IMAGE_ENDPOINT_FIND_BY_ID)
    Mono<ImageDto> findById(@PathVariable("id") String id);

    @PostMapping(value = IMAGE_ENDPOINT_SAVE)
    Mono<ImageDto> save(FilePart filePart);

    @DeleteMapping(value = IMAGE_ENDPOINT_DELETE_BY_ID)
    Mono<Void> deleteById(@PathVariable("id") String id);
}
