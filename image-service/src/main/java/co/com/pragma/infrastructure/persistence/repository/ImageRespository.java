package co.com.pragma.infrastructure.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import co.com.pragma.infrastructure.persistence.document.ImageDocument;

public interface ImageRespository extends ReactiveMongoRepository<ImageDocument, String> {

}
