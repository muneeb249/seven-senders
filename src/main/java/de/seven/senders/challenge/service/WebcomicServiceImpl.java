package de.seven.senders.challenge.service;

import de.seven.senders.challenge.client.HttpClientExecutor;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.parser.ComicParser;
import de.seven.senders.challenge.parser.NumberParser;
import org.apache.http.client.fluent.Request;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    private final ComicParser comicParser;
    private final NumberParser numberParser;


    public WebcomicServiceImpl(
            HttpClientExecutor httpClientExecutor,
            Environment environment
    ) {

        this.httpClientExecutor = httpClientExecutor;
        this.webcomicUrl = environment.getProperty("webcomic.service-uri");
        this.webcomPrefix = environment.getProperty("webcomic.prefix");
        this.maxRecords = Integer.valueOf(Objects.requireNonNull(environment.getProperty("webcomic.max-records")));
        this.comicParser = new ComicParser();
        this.numberParser = new NumberParser();
    }

    @Override
    public List<Comic> getComics() throws IOException {

        var endpoint = URI.create(webcomicUrl + "/" + webcomPrefix);
        String responseBody = httpClientExecutor.execute(Request.Get(endpoint));
        int latestComicNumber = Stream.of(responseBody).map(numberParser).findFirst().get();

        var comic = Stream.of(responseBody + ":URL:" + endpoint.toString()).map(comicParser).findFirst().get();
        return fetchRecentComics(latestComicNumber, comic);
    }

    /**
     * This method will iterate over the rest of the remaining maxRecords -1 to call the endpoint
     * https://xkcd.com/num/info.0.json in a decremnting way unless the full maxRecords are recieved and parsed
     * @param num
     * @param comic
     * @return
     * @throws IOException
     */
    private List<Comic> fetchRecentComics(int num, Comic comic) throws IOException {
        List<Comic> comics = new ArrayList<>();
        comics.add(comic);

        for(int i =0; i < maxRecords - 1; i++) {
            num--;
            var uri = webcomicUrl + "/" + num + "/" + webcomPrefix;
            var responseBody = httpClientExecutor.execute(Request.Get(URI.create(uri)));
            var parsedComic = Stream.of(responseBody + ":URL:" + uri).map(comicParser).findFirst().get();
            comics.add(parsedComic);
        }

        return comics;
    }

}
