package de.seven.senders.challenge.service;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Flux<Comic> getAndSortComics() throws IOException, FeedException {

        List<Comic> webcomicComics = webcomService.getComics();
        List<Comic> pdlComics = pdlService.getComics();

        List<Comic> comics = Stream.of(webcomicComics, pdlComics)
                .flatMap(Collection::stream).sorted(Comparator.comparing(Comic::getPublishingDate).reversed())
                .collect(Collectors.toList());

        return Flux.fromIterable(comics);



    }
}
