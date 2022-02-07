package co.com.pragma.api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ClientRouterRest {

    public static final String pathClient = "/api/client";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ClientHandler handler) {
        return route(GET(pathClient.concat("/{id}")), handler::findById)
                .andRoute(GET(pathClient.concat("/find/type/{type}/document/{document}")), handler::findByTypeDocumentAndDocument)
                .andRoute(GET(pathClient), handler::findAll)
                .andRoute(GET(pathClient.concat("/all/find/age/{age}")), handler::findByAgeAll)
                .andRoute(POST(pathClient), handler::save)
                .andRoute(PUT(pathClient), handler::update)
                .andRoute(DELETE(pathClient.concat("/{id}")), handler::deleteById);
    }
}
