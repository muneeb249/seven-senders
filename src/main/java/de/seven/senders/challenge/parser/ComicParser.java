package de.seven.senders.challenge.parser;

import de.seven.senders.challenge.exception.ComicParseException;
import de.seven.senders.challenge.model.Comic;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.function.Function;

public class ComicParser implements Function<String, Comic> {

    /**
     * This method will parse the json along with uri to create the Comic Object
     * @param body:URL:url
     * @return
     */
    @Override
    public Comic apply(String body) {
        try {
            String [] response = body.split(":URL:");
            JSONObject obj = new JSONObject(response[0]);
            int month = obj.getInt("month");
            int date = obj.getInt("day");
            int year = obj.getInt("year");
            String imgUrl = obj.getString("img");
            String title = obj.getString("title");

            return new Comic(
                    imgUrl,
                    title,
                    response[1],
                    LocalDate.of(year, month, date)
            );
        } catch (Exception ex) {
            throw new ComicParseException(ex.getMessage());
        }
    }
}
