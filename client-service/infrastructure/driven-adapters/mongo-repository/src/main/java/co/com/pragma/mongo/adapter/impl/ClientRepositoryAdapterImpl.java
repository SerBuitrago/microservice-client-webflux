package co.com.pragma.mongo.adapter.impl;

import co.com.pragma.model.TypeDocument;
import co.com.pragma.model.client.Client;
import co.com.pragma.model.client.gateways.ClientGateway;
import co.com.pragma.mongo.adapter.ClientRepositoryAdapter;
import co.com.pragma.mongo.feign.ImageFeignClient;
import co.com.pragma.mongo.feign.dto.ImageDto;
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
    private final ImageFeignClient imageFeignClient;

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
                    return findImageClient(client);
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
                    return findImageClient(client);
                });
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepositoryAdapter
                .findAll()
                .flatMap(this::findImageClient);
    }

    @Override
    public Flux<Client> findByAgeAll(Integer age) {
        return clientRepositoryAdapter
                .findByAgeAll(age)
                .flatMap(this::findImageClient);
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
                .flatMap(client -> imageFeignClient
                                        .deleteById(client.getIdImage())
                                        .then(clientRepositoryAdapter.deleteById(client.getId()).then())
                                        .onErrorResume(Mono::error)
                ).onErrorResume(Mono::error);
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

    protected Mono<ImageDto> findImageById(String id){
        if(id == null)
            return Mono.empty();
        return imageFeignClient
                .findById(id)
                .defaultIfEmpty(new ImageDto());
    }

    protected Mono<Client> findImageClient(Client client){
        return Mono.just(client)
                .defaultIfEmpty(new Client())
                .flatMap(clientValidate -> {
                    if(clientValidate.getIdImage() == null)
                        return Mono.just(clientValidate);
                    return findImageById(clientValidate.getIdImage())
                            .flatMap(image -> {
                                clientValidate.setIdImage(image.getId());
                                return Mono.just(clientValidate);
                            });
                });
    }
}
