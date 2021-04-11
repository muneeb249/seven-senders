package router.parser;

import de.seven.senders.challenge.parser.NumberParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class NumberParserTest {

    @Test
    public void testNumberFormat() {
        var json = "{\n" +
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
                "}";

        var numberParser = new NumberParser();

        var result = Stream.of(json).map(numberParser).findFirst().get();

        Assert.assertEquals("Number must be equal", 2448, result.intValue());


    }
}
