package co.com.pragma.api.mapper.impl;

import co.com.pragma.api.point.dto.ClientDto;
import co.com.pragma.api.mapper.ClientEntryMapper;
import co.com.pragma.model.client.Client;
import reactor.core.publisher.Mono;

public class ClientEntryMapperImpl implements ClientEntryMapper {
    @Override
    public Mono<Client> toDocument(ClientDto clientDto) {
        if(clientDto == null)
            return Mono.empty();
        return Mono.just(
                    Client.builder()
                            .id(clientDto.getId())
                            .name(clientDto.getName())
                            .lastName(clientDto.getLastName())
                            .document(clientDto.getDocument())
                            .typeDocument(clientDto.getTypeDocument())
                            .age(clientDto.getAge())
                            .birthCity(clientDto.getBirthCity())
                            .idImage(clientDto.getIdImage())
                            .build()
                );
    }

    @Override
    public Mono<ClientDto> toDto(Client client) {
        if(client == null)
            return Mono.empty();
        return Mono.just(
                ClientDto.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .lastName(client.getLastName())
                        .document(client.getDocument())
                        .typeDocument(client.getTypeDocument())
                        .age(client.getAge())
                        .birthCity(client.getBirthCity())
                        .idImage(client.getIdImage())
                        .build()
        );
    }
}
