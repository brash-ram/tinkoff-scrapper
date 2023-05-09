package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.client.ScrapperClient;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@Component
@Slf4j
public class StartCommandHandler extends MessageHandler {

    private final ScrapperClient scrapperClient;
    public StartCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/start")) {
            Optional<String> response = scrapperClient.addChat(message.chat().id());
            bot.send(new SendMessageAdapter(message.chat().id(), defaultMassage + "start")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
