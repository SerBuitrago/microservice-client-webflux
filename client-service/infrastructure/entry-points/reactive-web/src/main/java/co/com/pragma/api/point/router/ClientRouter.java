package co.com.pragma.api.point.router;

import co.com.pragma.api.point.handler.ClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ClientRouter {

    public static final String PATH = "/api/client";

    public static final String FIND_BY_ID = PATH.concat("/{id}");
    public static final String FIND_BY_TYPE_DOCUMENT_AND_DOCUMENT = PATH.concat("/find/type/{type}/document/{document}");
    public static final String FIND_ALL = PATH;
    public static final String FIND_BY_AGE_ALL = PATH.concat("/all/find/age/{age}");
    public static final String SAVE = PATH;
    public static final String UPDATE_BY_ID = PATH.concat("/{id}");
    public static final String DELETE_BY_ID = PATH.concat("/{id}");

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ClientHandler handler) {
        return route(GET(FIND_BY_ID), handler::findById)
                .andRoute(GET(FIND_BY_TYPE_DOCUMENT_AND_DOCUMENT), handler::findByTypeDocumentAndDocument)
                .andRoute(GET(FIND_ALL), handler::findAll)
                .andRoute(GET(FIND_BY_AGE_ALL), handler::findByAgeAll)
                .andRoute(POST(SAVE), handler::save)
                .andRoute(PUT(UPDATE_BY_ID), handler::updateById)
                .andRoute(DELETE(DELETE_BY_ID), handler::deleteById);
    }
}
