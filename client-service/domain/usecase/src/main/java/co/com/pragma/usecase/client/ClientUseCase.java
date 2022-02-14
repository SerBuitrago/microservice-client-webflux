package co.com.pragma.usecase.client;

import co.com.pragma.model.TypeDocument;
import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClientUseCase {

    private final ClientGateway clientGateway;

    public Mono<Client> findById(String id) {
        return clientGateway.findById(id);
    }

    public Mono<Client> findByTypeDocumentAndDocument(TypeDocument typeDocument, Long document) {
        return clientGateway.findByTypeDocumentAndDocument(typeDocument, document);
    }

    public Flux<Client> findAll() {
        return clientGateway.findAll();
    }

    public Flux<Client> findByAgeAll(Integer age) {
        return clientGateway.findByAgeAll(age);
    }

    public Mono<Client> save(Client client) {
        return clientGateway.save(client);
    }

    public Mono<Void> deleteById(String id) {
        return clientGateway.deleteById(id);
    }
}
