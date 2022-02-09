package co.com.pragma.infrastructure.entry.point.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.infrastructure.entry.point.handler.ImageHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ImageRouter {

	public static final String path = "/api/image"; 
 
	@Bean
	public RouterFunction<ServerResponse> router(ImageHandler handler) {
		 return route(GET(path.concat("/{id}")), handler::findById) 
	                .andRoute(GET(path), handler::findAll)
	                .andRoute(POST(path), handler::save)
	                .andRoute(DELETE(path.concat("/{id}")), handler::deleteById);
	}
}