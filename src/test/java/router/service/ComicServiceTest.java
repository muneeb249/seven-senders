package router.service;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.service.ComicServiceImpl;
import de.seven.senders.challenge.service.PdlService;
import de.seven.senders.challenge.service.WebcomService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import router.util.ComicTestData;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ComicServiceTest {

    @Mock
    private PdlService pdlService;

    @Mock
    private WebcomService webcomService;

    @Autowired
    private ComicServiceImpl comicService;

    @Before
    public void init() {
        comicService = new ComicServiceImpl(pdlService, webcomService);
    }

    @Test
    public void testSortedRecords() throws IOException, FeedException {
        List<Comic> webComics = ComicTestData.getComics(2);
        List<Comic> pdlComics = ComicTestData.getComics(2);

        List<Comic> comics = Stream.of(webComics, pdlComics)
                .flatMap(Collection::stream).sorted(Comparator.comparing(Comic::getPublishingDate).reversed())
                .collect(Collectors.toList());

        Mockito.when(webcomService.getComics()).thenReturn(webComics);
        Mockito.when(pdlService.getComics()).thenReturn(pdlComics);

        var result = comicService.getAndSortComics();
        List<Comic> actualComics = result.collectList().block();

        assert actualComics != null;
        Assert.assertEquals("Size Should be 4", actualComics.size(), 4);

        for(int i = 0; i < 4; i++) {
            assertEquals("Objects are equal", actualComics.get(i), comics.get(i));
        }
    }

}
