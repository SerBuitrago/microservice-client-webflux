package co.com.pragma.mongo.repository;

import co.com.pragma.mongo.document.ClientDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ClientMongoDBRepository
        extends ReactiveMongoRepository<ClientDocument, String>,
                ReactiveQueryByExampleExecutor<ClientDocument> {
}
