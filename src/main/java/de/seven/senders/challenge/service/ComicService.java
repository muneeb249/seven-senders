package de.seven.senders.challenge.service;

import de.seven.senders.challenge.model.Comic;
import reactor.core.publisher.Flux;

public interface ComicService {

    Flux<Comic> getAndSortComics();
}
