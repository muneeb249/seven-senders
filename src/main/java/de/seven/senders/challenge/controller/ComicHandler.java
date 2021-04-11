package de.seven.senders.challenge.controller;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.service.ComicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author muneeb
 *
 * This class is the Handler. This handler acts like a Functional Programming paradigm
 */
@Component
public class ComicHandler {

    Logger logger = LoggerFactory.getLogger(ComicHandler.class);

    @Autowired
    ComicService comicService;

    public Mono<ServerResponse> getComics(ServerRequest serverRequest) {
        try {
            logger.info("URL invoked" + serverRequest.uri());
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(comicService.getAndSortComics(), Comic.class);
        } catch (IOException | FeedException ex) {
            return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body(ex.getMessage(), String.class);
        }

    }

}
