package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@Component

public class StartCommandHandler extends MessageHandler{
    public StartCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/start")) {
            bot.send(new SendMessageAdapter(message.chat().id(), DEFAULT_MASSAGE + "start")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
