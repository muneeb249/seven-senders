package de.seven.senders.challenge.service;

import com.sun.syndication.io.FeedException;
import de.seven.senders.challenge.model.Comic;

import java.io.IOException;
import java.util.List;

/**
 * @author muneeb
 * This class is responsible for calling the RSS Feed of PDL endpint and collects the latest records and parse it into
 * Comic DTO
 */
public interface PdlService {

    List<Comic> getComics() throws IOException, FeedException;
}
