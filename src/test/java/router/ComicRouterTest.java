package router;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.controller.ComicHandler;
import de.seven.senders.challenge.controller.ComicsRouter;
import de.seven.senders.challenge.model.Comic;
import de.seven.senders.challenge.service.ComicServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import router.util.ComicTestData;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ComicsRouter.class, ComicHandler.class})
@WebFluxTest
public class ComicRouterTest {


    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @MockBean
    private ComicServiceImpl comicService;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testGetComics() throws IOException, FeedException {
        List<Comic> comics = ComicTestData.getComics(2);

        Mockito.when(comicService.getAndSortComics()).thenReturn(Flux.just(comics.get(0), comics.get(1)));

        webTestClient.get()
                .uri("")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comic.class)
                .value(comicResponse -> {
                            Assertions.assertThat(comicResponse.get(0).getUuid()).isEqualTo(comics.get(0).getUuid());
                    Assertions.assertThat(comicResponse.get(1).getUuid()).isEqualTo(comics.get(1).getUuid());
                        }
                );
    }


}
