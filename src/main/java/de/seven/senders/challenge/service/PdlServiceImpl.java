package de.seven.senders.challenge.service;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.rssfeed.RssFeedExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
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
    public List<Comic> getComics() throws IOException, FeedException {
        var feedEnteries = rssFeedExecutor.getRssFeedResponse(pdlUrl);
        return parseFeedResponse(feedEnteries);
    }



    private List<Comic>  parseFeedResponse(List feedEnteries) {
        List<Comic> comics = new ArrayList<>();

        for (Object entry : feedEnteries) {

            if(comics.size() < maxRecords) {
                if (entry instanceof SyndEntry) {
                    SyndEntry syndEntry = (SyndEntry) entry;
                    List contents = syndEntry.getContents();
                    String imgUrl = "";
                    if (contents != null && contents.size() > 0) {
                        if (contents.get(0) instanceof SyndContent) {
                            SyndContent content = (SyndContent) contents.get(0);
                            imgUrl = extractImg(content.getValue());
                        }
                    }

                    var comic = new Comic(
                            imgUrl,
                            syndEntry.getTitle(),
                            syndEntry.getUri(),
                            syndEntry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    );

                    comics.add(comic);

                }
            }else
                break;


        }


        return comics;
    }

    /**
     * In order to get the img url from the response, this method is called with the help of Jsoup, we extract the
     *  first img url
     * @param html
     * @return
     */
    private static String extractImg(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element imageElement = doc.select("img").first();
        return imageElement.attr("src");

    }
}
