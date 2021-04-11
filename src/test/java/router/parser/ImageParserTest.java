package router.parser;

import de.seven.senders.challenge.parser.ImageParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class ImageParserTest {

    @Test
    public void testImageParser() {
        var html = "\n" +
                "<figure class=\"wp-block-image size-full is-resized\"><a href=\"https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest.png\"><img loading=\"lazy\" src=\"https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest.png\" alt=\"\" class=\"wp-image-7981\" width=\"810\" height=\"528\" srcset=\"https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest.png 1080w, https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest-300x196.png 300w, https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest-1024x667.png 1024w, https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest-768x501.png 768w\" sizes=\"(max-width: 810px) 100vw, 810px\" /></a></figure>\n" +
                "<div class=\"feedflare\">\n" +
                "<a href=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?a=3yUH7W0KYtg:GPpFlOOoZgU:yIl2AUoC8zA\"><img src=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?d=yIl2AUoC8zA\" border=\"0\"></img></a> <a href=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?a=3yUH7W0KYtg:GPpFlOOoZgU:qj6IDK7rITs\"><img src=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?d=qj6IDK7rITs\" border=\"0\"></img></a> <a href=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?a=3yUH7W0KYtg:GPpFlOOoZgU:gIN9vFwOqvQ\"><img src=\"http://feeds.feedburner.com/~ff/PoorlyDrawnLines?i=3yUH7W0KYtg:GPpFlOOoZgU:gIN9vFwOqvQ\" border=\"0\"></img></a>\n" +
                "</div><img src=\"http://feeds.feedburner.com/~r/PoorlyDrawnLines/~4/3yUH7W0KYtg\" height=\"1\" width=\"1\" alt=\"\"/>";
        var imageParser = new ImageParser();

        var imgUrl = Stream.of(html).map(imageParser).findFirst().get();

        Assert.assertEquals("Parsed Image Url is correct",
                "https://poorlydrawnlines.com/wp-content/uploads/2021/04/the-greatest.png", imgUrl);


    }
}
