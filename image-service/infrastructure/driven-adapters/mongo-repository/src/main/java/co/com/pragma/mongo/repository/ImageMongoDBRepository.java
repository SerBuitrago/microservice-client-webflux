package co.com.pragma.mongo.repository;

import co.com.pragma.mongo.document.ImageDocument;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ImageMongoDBRepository
        extends ReactiveMongoRepository<ImageDocument, String>,
                ReactiveQueryByExampleExecutor<ImageDocument> {
}
