package ru.tinkoff.edu.bot.dto.scrapper.response;

import jakarta.validation.Valid;
import java.util.List;

/**
 * ListLinksResponse
 */


public record ListLinksResponse (
        @Valid
        List<LinkResponse> links,

        Integer size
) {}

