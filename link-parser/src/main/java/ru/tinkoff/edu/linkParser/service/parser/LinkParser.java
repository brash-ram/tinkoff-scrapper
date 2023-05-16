package ru.tinkoff.edu.linkParser.service.parser;

import java.net.URI;
import lombok.Setter;
import ru.tinkoff.edu.linkParser.dto.LinkData;

@Setter
public abstract class LinkParser {
    protected LinkParser nextParser;

    public abstract LinkData parseUrl(URI url);
}
