package de.seven.senders.challenge.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author muneeb
 *
 * This class is the Router to define the endpoints.
 * This class has a GET Endoint which is invoked to get the latest comics
 */
@Configuration
public class ComicsRouter {

    @Bean
    public RouterFunction<ServerResponse> comicRouter(ComicHandler comicHandler)
    {

        return RouterFunctions
                .route(GET("").and(accept(MediaType.APPLICATION_JSON))
                        , comicHandler::getComics);
    }

}
