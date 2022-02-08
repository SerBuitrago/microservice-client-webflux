package co.com.pragma.infrastructure.persistence.repository;

import co.com.pragma.infrastructure.persistence.document.ImageDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ImageRespository extends ReactiveMongoRepository<ImageDocument, String> {
}
