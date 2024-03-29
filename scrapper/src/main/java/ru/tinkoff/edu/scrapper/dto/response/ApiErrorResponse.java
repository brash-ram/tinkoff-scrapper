package ru.tinkoff.edu.scrapper.dto.response;

import jakarta.validation.Valid;
import java.util.List;

/**
 * ApiErrorResponse
 */

public record ApiErrorResponse (
        String description,

        String code,

        String exceptionName,

        String exceptionMessage,

        @Valid
        List<String> stacktrace
) { }

