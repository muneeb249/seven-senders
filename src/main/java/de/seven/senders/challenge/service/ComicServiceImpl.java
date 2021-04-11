package de.seven.senders.challenge.service;

import de.seven.senders.challenge.model.Comic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ComicServiceImpl implements ComicService {

    private final PdlService pdlService;

    private final WebcomService webcomService;

    public ComicServiceImpl(PdlService pdlService,
                            WebcomService webcomService) {
        this.pdlService = pdlService;
        this.webcomService = webcomService;
    }

    @Override
    public Flux<Comic> getAndSortComics() {
        return null;



    }
}
