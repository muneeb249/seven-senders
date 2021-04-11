package de.seven.senders.challenge.model;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Comic {
    private final UUID uuid;
    private final String pictureUrl;
    private final String title;
    private final String webUrl;
    private final LocalDate publishingDate;

    public Comic(UUID uuid, String pictureUrl, String title, String webUrl, LocalDate publishingDate) {
        this.uuid = uuid;
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.webUrl = webUrl;
        this.publishingDate = publishingDate;
    }

    private Comic() {
        this.uuid = null;
        this.pictureUrl = null;
        this.title = null;
        this.webUrl = null;
        this.publishingDate = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public Comic(String pictureUrl, String title, String webUrl, LocalDate publishingDate) {
        uuid = UUID.randomUUID();
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.webUrl = webUrl;
        this.publishingDate = publishingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comic)) return false;
        Comic comic = (Comic) o;
        return Objects.equals(uuid, comic.uuid) &&
                Objects.equals(pictureUrl, comic.pictureUrl) &&
                Objects.equals(title, comic.title) &&
                Objects.equals(webUrl, comic.webUrl) &&
                Objects.equals(publishingDate, comic.publishingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, pictureUrl, title, webUrl, publishingDate);
    }
}
