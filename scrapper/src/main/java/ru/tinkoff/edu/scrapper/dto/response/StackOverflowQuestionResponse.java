package ru.tinkoff.edu.scrapper.dto.response;

import java.util.List;
import ru.tinkoff.edu.scrapper.dto.stackoverflow.StackOverflowQuestion;

public record StackOverflowQuestionResponse(
        List<StackOverflowQuestion> items
) {
}
