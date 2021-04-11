package router.util;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import de.seven.senders.challenge.model.Comic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ComicTestData {

    public static List<Comic> getComics(int num) {
        List<Comic> comics = new ArrayList<>();

        var localDate = LocalDate.now();


        for(int i =0; i < num; i++) {
            var comic = new Comic(
                    "pictureUrl" + i,
                    "title" + i,
                    "webUrl + i",
                    localDate);
            comics.add(comic);
            localDate = localDate.plusDays(1);
        }
        return comics;
    }

    public static List<SyndEntry> getSyndEntry(int num) {
        List<SyndEntry> syndEntries = new ArrayList<>();
        var localDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        for(int i =0; i < num; i++) {
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            syndEntries.add(
                    getSyndEntry("uri" + i, "img" + i, date, "title" + i)
            );

            localDate = localDate.plusDays(1);

        }

        return syndEntries;

    }

    private static SyndEntry getSyndEntry(String uri, String imgUrl, Date publishDate, String title) {
        SyndContent syndContent = new SyndContentImpl();
        syndContent.setValue("<img src=\"" + imgUrl + "\" border=\"0\"></img>");
        var syndEntry = new SyndEntryImpl();
        syndEntry.setTitle(title);
        syndEntry.setUri(uri);
        syndEntry.setPublishedDate(publishDate);
        syndEntry.setContents(Collections.singletonList(syndContent));

        return syndEntry;

    }
}
