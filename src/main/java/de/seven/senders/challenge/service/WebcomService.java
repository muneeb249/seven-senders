package de.seven.senders.challenge.service;

import de.seven.senders.challenge.model.Comic;

import java.io.IOException;
import java.util.List;

public interface WebcomService {

    /**
     * This method is responsible for calling the Webcom service and retunrs the latest comics.
     * @return
     * @throws IOException
     */
    List<Comic> getComics() throws IOException;
}
