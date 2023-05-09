package ru.tinkoff.edu.linkParser.service.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfiguration {

    @Bean
    public LinkParser linkParser() {
        LinkParser linkParser = new LinkParserStackOverflow();
        linkParser.setNextParser(new LinkParserGitHub());
        return linkParser;
    }
}
