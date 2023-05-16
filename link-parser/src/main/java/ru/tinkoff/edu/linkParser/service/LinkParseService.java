package ru.tinkoff.edu.linkParser.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.linkParser.dto.LinkData;
import ru.tinkoff.edu.linkParser.service.parser.LinkParser;

@Service
@RequiredArgsConstructor
public class LinkParseService {
    private final LinkParser linkParser;

    public LinkData parseLink(URI url) {
        return linkParser.parseUrl(url);
    }

}
