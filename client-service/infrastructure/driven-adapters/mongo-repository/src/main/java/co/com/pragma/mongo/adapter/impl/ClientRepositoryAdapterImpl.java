package co.com.pragma.mongo.adapter.impl;

import co.com.pragma.model.TypeDocument;
import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientGateway;
import co.com.pragma.mongo.adapter.ClientRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapterImpl implements ClientGateway {

    private final ClientRepositoryAdapter clientRepositoryAdapter;

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientRepositoryAdapterImpl.class);

    @Override
    public Mono<Client> findById(String id) {
        return clientRepositoryAdapter
                .findById(id)
                .defaultIfEmpty(new Client())
                .doOnNext(client -> LOGGER.info(client.toString()))
                .flatMap(client -> {
                    if(client.getId() == null)
                        return Mono.error(new Exception(String.format("No existe ningun cliente con el id {0}.", id)));
                    return Mono.just(client);
                });
    }

    @Override
    public Mono<Client> findByTypeDocumentAndDocument(TypeDocument typeDocument, Long document) {
        return clientRepositoryAdapter
                .findByTypeDocumentAndDocument(typeDocument, document)
                .defaultIfEmpty(new Client())
                .doOnNext(client -> LOGGER.info(client.toString()))
                .flatMap(client -> {
                    if(client.getId() == null)
                        return Mono.error(new Exception(
                                String.format("No existe ningun cliente con el tipo de documento {0} y documento {1}.", typeDocument.name(), document))
                        );
                    return Mono.just(client);
                });
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepositoryAdapter
                .findAll();
    }

    @Override
    public Flux<Client> findByAgeAll(Integer age) {
        return clientRepositoryAdapter
                .findByAgeAll(age);
    }

    @Override
    public Mono<Client> save(Client client) {
        Mono.just(client)
                .flatMap(this::update)
                .flatMap(clientRepositoryAdapter::save)
                .defaultIfEmpty(new Client())
                .flatMap(clientAction -> {
                   if(clientAction.getId() == null)
                       return Mono.error(new Exception(String.format("No se ha {0} el cliente.", messageSaveOrUpdate(client).block())));
                   return Mono.just(clientAction);
                });
        return clientRepositoryAdapter.save(client);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return findById(id)
                .then(clientRepositoryAdapter.deleteById(id).then());
    }

    protected Mono<Client> update(Client client) {
        return Mono.just(client)
                .defaultIfEmpty(new Client())
                .flatMap(clientUpdate -> {
                    if(client.getId() == null)
                        return Mono.just(clientUpdate);
                    return findById(client.getId()).map(clientFindById -> client);
                });
    }

    protected Mono<String> messageSaveOrUpdate(Client client){
        return Mono.just(client)
                .defaultIfEmpty(new Client())
                .map(clientValidate -> clientValidate.getId() != null ? "actualizado" : "regsitrado");
    }
}
