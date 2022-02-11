package co.com.pragma.api.mapper;

import co.com.pragma.api.point.dto.ClientDto;
import co.com.pragma.model.client.Client;

import reactor.core.publisher.Mono;

public interface ClientEntryMapper {

    Mono<Client> toDocument(ClientDto clientDto);

    Mono<ClientDto> toDto(Client client);
}
