package co.com.pragma.mongo.adapter;

import co.com.pragma.model.client.Client;
import co.com.pragma.mongo.repository.ClientMongoDBRepository;
import co.com.pragma.mongo.document.ClientDocument;
import co.com.pragma.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryAdapter
        extends AdapterOperations<Client, ClientDocument, String, ClientMongoDBRepository> {

    public ClientRepositoryAdapter(ClientMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Client.class));
    }
}
