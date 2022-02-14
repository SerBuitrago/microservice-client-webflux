package co.com.pragma.mongo.repository;

import co.com.pragma.model.TypeDocument;
import co.com.pragma.mongo.document.ClientDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientMongoDBRepository
        extends ReactiveMongoRepository<ClientDocument, String>,
                ReactiveQueryByExampleExecutor<ClientDocument> {

    Mono<ClientDocument> findByTypeDocumentAndDocument(TypeDocument typeDocument, Long document);

    Flux<ClientDocument> findByAge(Integer age);
}
