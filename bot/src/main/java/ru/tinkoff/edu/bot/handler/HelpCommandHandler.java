package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@Component
public class HelpCommandHandler extends MessageHandler {
    private String helpMessage;

    public HelpCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/help")) {
            bot.send(new SendMessageAdapter(message.chat().id(), getHelpMessage())
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }

    private String getHelpMessage() {
        if (helpMessage == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("/start -- зарегистрировать пользователя\n")
                    .append("/help -- вывести окно с командами\n")
                    .append("/track -- начать отслеживание ссылки\n")
                    .append("/untrack -- прекратить отслеживание ссылки\n")
                    .append("/list -- показать список отслеживаемых ссылок\n");
            helpMessage = sb.toString();
        }
        return helpMessage;
    }
}
