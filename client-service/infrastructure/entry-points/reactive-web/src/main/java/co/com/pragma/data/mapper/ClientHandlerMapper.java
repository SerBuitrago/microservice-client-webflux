package co.com.pragma.data.mapper;

import co.com.pragma.data.dto.ClientDto;
import co.com.pragma.model.client.Client;

import reactor.core.publisher.Mono;

public interface ClientHandlerMapper {

    Mono<Client> toDocument(ClientDto clientDto);

    Mono<ClientDto> toDto(Client client);
}
