package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Update;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.bot.tg.Bot;
@NoArgsConstructor
public abstract class MessageHandler {
    protected MessageHandler nextHandler;

    @Autowired
    protected Bot bot;

    protected final String defaultMassage = "Команда обработана: ";

    public MessageHandler(Bot bot) {
        this.bot = bot;
    }

    /**
     * Added next handler
     * @param nextHandler next handler
     * @return next handler
     */
    public MessageHandler setNextHandler(MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public abstract void handleMessage(Update update);
}
