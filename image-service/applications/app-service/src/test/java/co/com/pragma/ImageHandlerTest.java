package co.com.pragma;

import co.com.pragma.api.mapper.ImageErrorEntryMapper;
import co.com.pragma.api.mapper.impl.ImageEntryMapperImpl;
import co.com.pragma.api.point.handler.ImageHandler;
import co.com.pragma.api.point.router.ImageRouter;
import co.com.pragma.model.image.gateways.ImageGateway;
import co.com.pragma.mongo.adapter.ImageMongoRepositoryAdapter;
import co.com.pragma.mongo.adapter.impl.ImageMongoRepositoryAdapterImpl;
import co.com.pragma.mongo.repository.ImageMongoDBRepository;
import co.com.pragma.usecase.image.ImageUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static co.com.pragma.api.point.router.ImageRouter.FIND_ALL;

@ContextConfiguration(classes = {ImageRouter.class, ImageHandler.class})
@WebFluxTest
class ImageHandlerTest {

    @MockBean
    ImageUseCase imageUseCase;
    @MockBean
    ImageEntryMapperImpl imageEntryMapper;
    @MockBean
    ImageErrorEntryMapper imageErrorEntryMapper;

    @Autowired
    private WebTestClient client;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
       client.get()
                .uri(FIND_ALL);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void saveOrUpdate() {
    }
}