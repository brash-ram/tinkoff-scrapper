package ru.tinkoff.edu.linkParser.dto;

import java.net.URI;
import lombok.Data;
import ru.tinkoff.edu.linkParser.enums.Site;

@Data
public final class LinkDataGithub extends LinkData {
    private String user;
    private String repository;

    public LinkDataGithub(URI url, Site site, String user, String repository) {
        super(url, site);
        this.user = user;
        this.repository = repository;
    }
}
