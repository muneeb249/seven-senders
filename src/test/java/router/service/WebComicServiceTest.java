package router.service;

import de.seven.senders.challenge.client.HttpClientExecutor;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.service.WebcomicServiceImpl;
import org.apache.http.client.fluent.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WebComicServiceTest {

    @Mock
    private Environment environment;

    @Mock
    private HttpClientExecutor httpClientExecutor;

    private WebcomicServiceImpl webcomicService;

    @Before
    public void init() {
        Mockito.when(this.environment.getProperty("webcomic.service-uri")).thenReturn("url");
        Mockito.when(this.environment.getProperty("webcomic.prefix")).thenReturn("prefix");
        Mockito.when(this.environment.getProperty("webcomic.max-records")).thenReturn("2");

        webcomicService = new WebcomicServiceImpl(httpClientExecutor, environment);
    }

    @Test
    public void testSuccessScenario() throws IOException {
        String json = "{\n" +
                "    \"month\": \"4\",\n" +
                "    \"num\": 2,\n" +
                "    \"link\": \"\",\n" +
                "    \"year\": \"2021\",\n" +
                "    \"news\": \"\",\n" +
                "    \"safe_title\": \"Eradication\",\n" +
                "    \"transcript\": \"\",\n" +
                "    \"alt\": \"When you get to hell, tell smallpox we say hello.\",\n" +
                "    \"img\": \"https://imgs.xkcd.com/comics/eradication.png\",\n" +
                "    \"title\": \"Eradication\",\n" +
                "    \"day\": \"9\"\n" +
                "}";

        Mockito.when(httpClientExecutor.execute(Mockito.any(Request.class))).thenReturn(json);


        List<Comic> comics = webcomicService.getComics();

        Assert.assertEquals("Size is equal to 2" , 2, comics.size());

        var comic1 = comics.get(0);
        var comic2 = comics.get(1);


        Assertions.assertAll("Comic 1 Obj",
                () -> Assert.assertEquals("ImgURL is equal for entry 1", comic1.getPictureUrl(), "https://imgs.xkcd.com/comics/eradication.png"),
                () -> Assert.assertEquals("ImgURL is equal for entry 2", comic1.getWebUrl(), "url/prefix")
        );

        Assertions.assertAll("Comic 2 Obj",
                () -> Assert.assertEquals("ImgURL is equal for entry 1", comic2.getPictureUrl(), "https://imgs.xkcd.com/comics/eradication.png"),
                () -> Assert.assertEquals("ImgURL is equal for entry 2", comic2.getWebUrl(), "url/1/prefix")
        );
    }
}
