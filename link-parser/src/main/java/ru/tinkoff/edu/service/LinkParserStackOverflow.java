package ru.tinkoff.edu.service;

import ru.tinkoff.edu.dto.LinkData;
import ru.tinkoff.edu.dto.LinkDataStackOverflow;
import ru.tinkoff.edu.enums.Site;

import java.net.URL;

class LinkParserStackOverflow extends LinkParser {

    private static LinkParserStackOverflow instance;

    public static LinkParserStackOverflow getInstance() {
        if (instance == null) {
            synchronized (LinkParserStackOverflow.class) {
                instance = new LinkParserStackOverflow();
            }
        }
        return instance;
    }

    @Override
    public LinkData parseUrl(URL url) {
        if (url.getHost().equals(Site.STACK_OVERFLOW.getHost())) {
            String[] stackOverflowPath = url.getPath().replaceFirst("/", "").split("/");
            String QUESTIONS_PATH = "questions";

            if (stackOverflowPath.length != 0 && stackOverflowPath[0].equals(QUESTIONS_PATH)) {
                return new LinkDataStackOverflow(
                        url,
                        Site.STACK_OVERFLOW,
                        Long.valueOf(stackOverflowPath[1])
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