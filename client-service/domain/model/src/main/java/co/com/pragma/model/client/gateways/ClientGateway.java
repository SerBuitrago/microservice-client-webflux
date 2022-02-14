package co.com.pragma.model.client.gateways;

import co.com.pragma.model.TypeDocument;
import co.com.pragma.model.client.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientGateway {

    Mono<Client> findById(String id);

    Mono<Client> findByTypeDocumentAndDocument(TypeDocument typeDocument, Long document);

    Flux<Client> findAll();

    Flux<Client> findByAgeAll(Integer age);

    Mono<Client> save(Client client);

    Mono<Void> deleteById(String id);

}
