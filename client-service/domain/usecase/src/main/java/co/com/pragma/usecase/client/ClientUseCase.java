package co.com.pragma.usecase.client;

import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClientUseCase implements ClientRepository {

    private final ClientRepository clientRepositoryGateway;

    @Override
    public Mono<Client> findById(String id) {
        return clientRepositoryGateway.findById(id);
    }

    @Override
    public Mono<Client> findByTypeDocumentAndDocument(String typeDocument, Long document) {
        return clientRepositoryGateway.findByTypeDocumentAndDocument(typeDocument, document);
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepositoryGateway.findAll();
    }

    @Override
    public Flux<Client> findByAgeAll(Integer age) {
        return clientRepositoryGateway.findByAgeAll(age);
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientRepositoryGateway.save(client);
    }

    @Override
    public Mono<Client> update(Client client) {
        return clientRepositoryGateway.update(client);
    }

    @Override
    public Mono<Void> deleteById(Client client) {
        return clientRepositoryGateway.deleteById(client);
    }
}
