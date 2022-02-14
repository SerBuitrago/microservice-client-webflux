package co.com.pragma.api.point.router;

import co.com.pragma.api.point.handler.ImageHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class ImageRouter {

    public static final String PATH = "/api/image";

    public static final String FIND_BY_ID = PATH.concat("/{id}");
    public static final String FIND_ALL = PATH;
    public static final String SAVE = PATH;
    public static final String UPDATE_BY_ID = PATH.concat("/{id}");
    public static final String DELETE_BY_ID = PATH.concat("/{id}");

    @Bean
    public RouterFunction<ServerResponse> router(ImageHandler handler) {
        return route(GET(FIND_BY_ID), handler::findById)
                .andRoute(GET(FIND_ALL), handler::findAll)
                .andRoute(POST(SAVE), handler::save)
                .andRoute(PUT(UPDATE_BY_ID), handler::updateById)
                .andRoute(DELETE(DELETE_BY_ID), handler::deleteById);
    }
}