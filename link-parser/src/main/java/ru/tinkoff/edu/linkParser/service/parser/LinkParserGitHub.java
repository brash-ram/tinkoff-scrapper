package ru.tinkoff.edu.linkParser.service.parser;

import java.net.URI;
import ru.tinkoff.edu.linkParser.dto.LinkData;
import ru.tinkoff.edu.linkParser.dto.LinkDataGithub;
import ru.tinkoff.edu.linkParser.enums.Site;

final class LinkParserGitHub extends LinkParser {

    @Override
    public LinkData parseUrl(URI url) {
        if (url.getHost().equals(Site.GITHUB.getHost())) {
            String[] githubPath = url.getPath().replaceFirst("/", "").split("/");
            if (githubPath.length == 2) {
                return new LinkDataGithub(
                        url,
                        Site.GITHUB,
                        githubPath[0],
                        githubPath[1]
                );
            } else {
                return null;
            }
        } else if (nextParser != null) {
            return nextParser.parseUrl(url);
        }
        return null;
    }
}
