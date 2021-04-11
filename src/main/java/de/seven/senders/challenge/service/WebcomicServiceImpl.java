package de.seven.senders.challenge.service;

import de.seven.senders.challenge.client.HttpClientExecutor;
import de.seven.senders.challenge.model.Comic;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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
        var endpoint = URI.create(webcomicUrl + "/" + webcomPrefix);
        String responseBody = httpClientExecutor.execute(Request.Get(endpoint));
        int latestComicNumber = getLatestComicNumber(responseBody);

        return fetchRecentComics(latestComicNumber, Comic.parseJsonToComic(responseBody, endpoint.toString()));
    }

    /**
     * This method will iterate over the rest of the remaining maxRecords -1 to call the endpoint
     * https://xkcd.com/num/info.0.json in a decremnting way unless the full maxRecords are recieved and parsed
     * @param num
     * @param comic
     * @return
     * @throws IOException
     */
    private List<Comic> fetchRecentComics(int num, Comic comic) throws IOException{
        List<Comic> comics = new ArrayList<>();
        comics.add(comic);

        for(int i =0; i < maxRecords - 1; i++) {
            num--;
            var uri = webcomicUrl + "/" + num + "/" + webcomPrefix;
            var responseBody = httpClientExecutor.execute(Request.Get(URI.create(uri)));
            var parsedComic = Comic.parseJsonToComic(responseBody, uri);
            comics.add(parsedComic);
        }

        return comics;
    }


    /**
     * The method will parse the response and extract the latest num from the json body. The json body looks like this
     * <code>
     *     {
     *   "month": "4",
     *   "num": 2448,
     *   "link": "",
     *   "year": "2021",
     *   "news": "",
     *   "safe_title": "Eradication",
     *   "transcript": "",
     *   "alt": "When you get to hell, tell smallpox we say hello.",
     *   "img": "https://imgs.xkcd.com/comics/eradication.png",
     *   "title": "Eradication",
     *   "day": "9"
     * }
     *     <code/>
     * @param json
     * @return
     */
    private int getLatestComicNumber(String json) {
        JSONObject obj = new JSONObject(json);
        return obj.getInt("num");
    }
}
