package de.seven.senders.challenge.rssfeed;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class RssFeedExecutor {

    /**
     * This method will call the RSS Feed and creates a SyncFeed from where the response is parsed into Comic object
     * @return
     * @throws IOException
     * @throws FeedException
     */
    public List getRssFeedResponse(String url) throws IOException, FeedException {
        URL feedSource = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(feedSource)).getEntries();
    }
}
