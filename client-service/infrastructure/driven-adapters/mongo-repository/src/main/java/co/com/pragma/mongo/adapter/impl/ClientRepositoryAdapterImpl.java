package co.com.pragma.mongo.adapter.impl;

import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientRepository;
import co.com.pragma.mongo.adapter.ClientRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapterImpl implements ClientRepository {

    private final ClientRepositoryAdapter clientRepositoryAdapter;

    private static final Logger logger = LoggerFactory.getLogger(ClientRepositoryAdapterImpl.class);

    @Override
    public Mono<Client> findById(String id) {
        return clientRepositoryAdapter.findById(id);
    }

    @Override
    public Mono<Client> findByTypeDocumentAndDocument(String typeDocument, Long document) {
        return null;
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepositoryAdapter.findAll()
                .doOnNext(client -> logger.info(client.toString()));
    }

    @Override
    public Flux<Client> findByAgeAll(Integer age) {
        return null;
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientRepositoryAdapter.save(client);
    }

    @Override
    public Mono<Client> update(Client client) {
        return clientRepositoryAdapter.save(client);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return findById(id)
                .defaultIfEmpty(new Client())
                .flatMap(client -> {
                    if(client.getId() == null)
                        return Mono.error(new Exception("No existe el usuario"));
                    clientRepositoryAdapter.deleteById(client.getId())
                            .subscribe(value -> logger.info("Cliente eliminado ".concat(client.toString())));
                    return Mono.empty();
                });
    }
}
