package de.seven.senders.challenge.service;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface ComicService {

    Flux<Comic> getAndSortComics() throws IOException, FeedException;
}
