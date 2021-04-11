package de.seven.senders.challenge.parser;

import de.seven.senders.challenge.exception.ComicNumberException;
import org.json.JSONObject;

import java.util.function.Function;

public class NumberParser implements Function<String, Integer> {

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
    @Override
    public Integer apply(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.getInt("num");
        } catch (Exception ex) {
            throw new ComicNumberException(ex.getMessage());
        }
    }
}
