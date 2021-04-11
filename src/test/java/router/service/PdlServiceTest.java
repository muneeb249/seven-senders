package router.service;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.exception.ComicParseException;
import de.seven.senders.challenge.exception.ImageParseException;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.rssfeed.RssFeedExecutor;
import de.seven.senders.challenge.service.PdlServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import router.util.ComicTestData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PdlServiceTest {

    @Mock
    private RssFeedExecutor rssFeedExecutor;

    @Mock
    private Environment environment;


    private PdlServiceImpl pdlService;


    @Before
    public void init() {

        Mockito.when(this.environment.getProperty("pdl.service-uri")).thenReturn("url");
        Mockito.when(this.environment.getProperty("pdl.max-records")).thenReturn("1");

        pdlService = new PdlServiceImpl(this.environment, this.rssFeedExecutor);
    }


    @Test
    public void testSuccessScenario() throws IOException, FeedException {

        List<SyndEntry> syndEntries = ComicTestData.getSyndEntry(1);


        Mockito.when(rssFeedExecutor.getRssFeedResponse(Mockito.anyString())).thenReturn(syndEntries);
        List<Comic> comics = pdlService.getComics();

        Assert.assertEquals("Size is equal", 1, comics.size());
        Assert.assertEquals("Title is equal", comics.get(0).getTitle(), syndEntries.get(0).getTitle());
        Assert.assertEquals("URI is equal", comics.get(0).getWebUrl(), syndEntries.get(0).getUri());
    }

    @Test(expected = ImageParseException.class)
    public void testImageFailScenario() throws IOException, FeedException {

        List<SyndEntry> syndEntries = ComicTestData.getSyndEntry(1);
        SyndContent syndContent = new SyndContentImpl();
        syndContent.setValue("noImageTagPresent");
        syndEntries.get(0).setContents(Collections.singletonList(syndContent));


        Mockito.when(rssFeedExecutor.getRssFeedResponse(Mockito.anyString())).thenReturn(syndEntries);
        pdlService.getComics();

    }
}
