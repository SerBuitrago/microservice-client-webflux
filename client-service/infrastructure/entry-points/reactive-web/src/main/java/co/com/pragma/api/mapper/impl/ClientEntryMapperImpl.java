package co.com.pragma.api.mapper.impl;

import co.com.pragma.api.point.dto.ClientDto;
import co.com.pragma.api.mapper.ClientEntryMapper;
import co.com.pragma.model.client.Client;
import reactor.core.publisher.Mono;

public class ClientEntryMapperImpl implements ClientEntryMapper {
    @Override
    public Mono<Client> toDocument(ClientDto clientDto) {
        return Mono.just(clientDto)
                .flatMap(clientDtoMap ->{
                    if(clientDtoMap == null)
                        return Mono.empty();
                    Client client = new Client();
                    client.setId(clientDtoMap.getId());
                    client.setName(clientDtoMap.getName());
                    client.setLastName(clientDtoMap.getLastName());
                    client.setDocument(clientDtoMap.getDocument());
                    client.setTypeDocument(clientDtoMap.getTypeDocument());
                    client.setAge(clientDtoMap.getAge());
                    client.setBirthCity(clientDtoMap.getBirthCity());
                    return Mono.just(client);
                });
    }

    @Override
    public Mono<ClientDto> toDto(Client client) {
        return  Mono.just(client)
                .flatMap(clientMap ->{
                    if(clientMap == null)
                        return Mono.empty();
                    ClientDto clientDto = new ClientDto();
                    clientDto.setId(clientMap.getId());
                    clientDto.setName(clientMap.getName());
                    clientDto.setLastName(clientMap.getLastName());
                    clientDto.setDocument(clientMap.getDocument());
                    clientDto.setTypeDocument(clientMap.getTypeDocument());
                    clientDto.setAge(clientMap.getAge());
                    clientDto.setBirthCity(clientMap.getBirthCity());
                    return Mono.just(clientDto);
                });
    }
}
