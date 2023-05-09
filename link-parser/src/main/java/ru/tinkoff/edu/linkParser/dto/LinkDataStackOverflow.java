package ru.tinkoff.edu.linkParser.dto;

import java.net.URI;
import lombok.Data;
import ru.tinkoff.edu.linkParser.enums.Site;

@Data
public final class LinkDataStackOverflow extends LinkData {
    private Long id;

    public LinkDataStackOverflow(URI url, Site site, Long id) {
        super(url, site);
        this.id = id;
    }
}
