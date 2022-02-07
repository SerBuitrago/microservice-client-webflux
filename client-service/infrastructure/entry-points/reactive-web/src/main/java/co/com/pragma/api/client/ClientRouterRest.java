package co.com.pragma.api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ClientRouterRest {

    private final String pathClient = "/api/client";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ClientHandler handler) {
        return route(GET(pathClient.concat("/{id}")), handler::findById);
    }
}
