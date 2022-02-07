package co.com.pragma.usecase.client;

import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClientUseCase{

    private final ClientRepository clientRepository;

    public Mono<Client> findById(String id) {
        return clientRepository.findById(id);
    }

    public Mono<Client> findByTypeDocumentAndDocument(String typeDocument, Long document) {
        return clientRepository.findByTypeDocumentAndDocument(typeDocument, document);
    }

    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    public Flux<Client> findByAgeAll(Integer age) {
        return clientRepository.findByAgeAll(age);
    }

    public Mono<Client> save(Client client) {
        return clientRepository.save(client);
    }

    public Mono<Client> update(Client client) {
        return clientRepository.update(client);
    }

    public Mono<Void> deleteById(String id) {
        return clientRepository.deleteById(id);
    }
}
