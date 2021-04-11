package de.seven.senders.challenge.service;

import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.rssfeed.RssFeedExecutor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PdlServiceImpl implements PdlService {

    private final String pdlUrl;
    private final Integer maxRecords;
    private final RssFeedExecutor rssFeedExecutor;

    public PdlServiceImpl(Environment environment,
                          RssFeedExecutor rssFeedExecutor) {
        this.pdlUrl = environment.getProperty("pdl.service-uri");
        this.maxRecords = Integer.valueOf(Objects.requireNonNull(environment.getProperty("pdl.max-records")));
        this.rssFeedExecutor = rssFeedExecutor;
    }

    @Override
    public List<Comic> getComics() {
        return null;
    }
}
