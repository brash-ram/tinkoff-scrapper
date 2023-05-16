package ru.tinkoff.edu.scrapper.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import ru.tinkoff.edu.scrapper.data.entity.Link;

public interface LinkService {

    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    List<Link> listAll(long tgChatId);

    List<Link> getAll();

    List<Link> getAllBefore(Timestamp borderTime);

    void updateTimeUpdate(Long linkId, Timestamp timeUpdate);

}
