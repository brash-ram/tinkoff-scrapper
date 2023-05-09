package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.client.ScrapperClient;
import ru.tinkoff.edu.bot.dto.scrapper.response.LinkResponse;
import ru.tinkoff.edu.bot.dto.scrapper.response.ListLinksResponse;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@Component
@Slf4j
public class ListCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    public ListCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/list")) {
            Optional<ListLinksResponse> response = scrapperClient.getLinks(message.chat().id());

            if (response.isPresent()) {
                ListLinksResponse listLinksResponse = response.get();
                if (listLinksResponse.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Сейчас отслеживаются следующие ссылки:\n");
                    for (LinkResponse linkResponse : response.get().links()) {
                        sb.append(linkResponse.url().toString())
                                .append("\n");
                    }
                    bot.send(new SendMessageAdapter(message.chat().id(), sb.toString())
                            .getSendMessage());
                } else {
                    String answer = "Сейчас не отслеживаются ссылки.";
                    bot.send(new SendMessageAdapter(message.chat().id(), answer)
                            .getSendMessage());
                }

            } else {
                bot.send(new SendMessageAdapter(message.chat().id(), "Непредвиденная ошибка. Повторите позже")
                        .getSendMessage());
            }

        } else {
            nextHandler.handleMessage(update);
        }
    }
}
