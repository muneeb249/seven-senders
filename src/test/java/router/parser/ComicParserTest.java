package router.parser;

import de.seven.senders.challenge.parser.ComicParser;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.stream.Stream;

public class ComicParserTest {

    @Test
    public void verifyComicJsonParsing() {
        var comicParser = new ComicParser();

        String response = "{\n" +
                "    \"month\": \"4\",\n" +
                "    \"num\": 2448,\n" +
                "    \"link\": \"\",\n" +
                "    \"year\": \"2021\",\n" +
                "    \"news\": \"\",\n" +
                "    \"safe_title\": \"Eradication\",\n" +
                "    \"transcript\": \"\",\n" +
                "    \"alt\": \"When you get to hell, tell smallpox we say hello.\",\n" +
                "    \"img\": \"https://imgs.xkcd.com/comics/eradication.png\",\n" +
                "    \"title\": \"Eradication\",\n" +
                "    \"day\": \"9\"\n" +
                "}" + ":URL:testURL";

        var result = Stream.of(response).map(comicParser).findFirst().get();

        Assert.assertEquals("PictureURL is correct", "https://imgs.xkcd.com/comics/eradication.png", result.getPictureUrl());
        Assert.assertEquals("Title is correct", "Eradication", result.getTitle());
        Assert.assertEquals("WebURL is correct", "testURL", result.getWebUrl());
        Assert.assertEquals("Publishing Date is correct", 0, result.getPublishingDate().compareTo(LocalDate.of(2021, 4, 9)));
    }
}
