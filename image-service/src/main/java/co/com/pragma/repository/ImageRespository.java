package co.com.pragma.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import co.com.pragma.models.document.ImageDocument;

public interface ImageRespository extends ReactiveMongoRepository<ImageDocument, String> {

}
