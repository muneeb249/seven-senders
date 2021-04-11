package de.seven.senders.challenge.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.function.Function;

public class ImageParser implements Function<String, String> {

    /**
     * In order to get the img url from the response, this method is called with the help of Jsoup, we extract the
     *  first img url
     * @param html
     * @return
     */
    @Override
    public String apply(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element imageElement = doc.select("img").first();
        return imageElement.attr("src");
    }
}
