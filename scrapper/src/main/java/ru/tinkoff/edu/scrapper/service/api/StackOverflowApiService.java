package ru.tinkoff.edu.scrapper.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.linkParser.dto.LinkData;
import ru.tinkoff.edu.linkParser.dto.LinkDataStackOverflow;
import ru.tinkoff.edu.linkParser.enums.Site;
import ru.tinkoff.edu.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.scrapper.dto.response.StackOverflowQuestionResponse;
import ru.tinkoff.edu.scrapper.dto.stackoverflow.StackOverflowQuestion;

@Service
@RequiredArgsConstructor
class StackOverflowApiService extends ApiService {

    private final StackOverflowClient stackOverflowClient;

    @Override
    public String checkUpdate(LinkData linkData) {
        if (linkData.getSite().equals(Site.STACK_OVERFLOW)) {
            LinkDataStackOverflow linkDataStackOverflow = (LinkDataStackOverflow) linkData;
            StackOverflowQuestionResponse infoResponse = stackOverflowClient.getQuestionInfo(
                    linkDataStackOverflow.getId()
            );
            StackOverflowQuestion question = infoResponse.items().get(0);
            StringBuilder sb = new StringBuilder();
            sb.append("Обновление [вопроса](")
                    .append(linkDataStackOverflow.getUrl())
                    .append(")\n")
                    .append("Последняя активность: ")
                    .append(question.last_activity_date().toString())
                    .append("\n")
                    .append("Последний обновление: ")
                    .append(question.last_edit_date().toString())
                    .append("\n")
                    .append("На вопрос ")
                    .append(question.is_answered() ? "есть ответы" : "нет ответов")
                    .append("\n");
            return sb.toString();
        } else if (nextService != null) {
            return nextService.checkUpdate(linkData);
        } else {
            return null;
        }
    }
}
