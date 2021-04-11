package de.seven.senders.challenge.service;

import de.seven.senders.challenge.client.HttpClientExecutor;
import de.seven.senders.challenge.model.Comic;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author muneeb
 * This class is reponsible for calling the webcomicUrl server and get the latest comics.
 * Firstly https://xkcd.com/info.0.json endpoint is called to get the latest comic,
 * Afterwards the variable "num" parsed, which tells the id of the latest comic.
 * From there https://xkcd.com/<num>/info.0.json is called in a decrementing way to get the other maxRecords -1
 * records and parse it into Comic.class
 */
@Service
public class WebcomicServiceImpl implements WebcomService {

    private final HttpClientExecutor httpClientExecutor;
    private final String webcomicUrl;
    private final String webcomPrefix;
    private final Integer maxRecords;


    public WebcomicServiceImpl(
            HttpClientExecutor httpClientExecutor,
            Environment environment
    ) {

        this.httpClientExecutor = httpClientExecutor;
        this.webcomicUrl = environment.getProperty("webcomic.service-uri");
        this.webcomPrefix = environment.getProperty("webcomic.prefix");
        this.maxRecords = Integer.valueOf(Objects.requireNonNull(environment.getProperty("webcomic.max-records")));
    }

    @Override
    public List<Comic> getComics() throws IOException {
        return null;
    }
}
