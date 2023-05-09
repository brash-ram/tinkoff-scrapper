package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.client.ScrapperClient;
import ru.tinkoff.edu.bot.dto.scrapper.request.RemoveLinkRequest;
import ru.tinkoff.edu.bot.dto.scrapper.response.LinkResponse;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@Component
@Slf4j
public class UntrackCommandHandler extends MessageHandler {

    private final ScrapperClient scrapperClient;

    public UntrackCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        List<String> stringUri = new ArrayList<>(List.of(message.text().split(" ")));
        String allowedMessage = stringUri.remove(0);
        if ("/untrack".equals(allowedMessage)) {
            if (stringUri.size() == 0) {
                String messageForGetLink = "Чтобы удалить ссылку отправьте команду "
                    + "/untrack с нужными ссылками, разделенными пробелами.";
                bot.send(new SendMessageAdapter(message.chat().id(), messageForGetLink)
                        .getSendMessage());
            } else {
                List<URI> urls = parseUris(stringUri);
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("Удалено %d из %d ссылок", urls.size(), stringUri.size()))
                        .append("\n");
                if (urls.size() != 0) {
                    sb.append("Удалены следующие ссылки:\n");
                    for (URI uri : urls) {
                        Optional<LinkResponse> response = scrapperClient.deleteLink(new RemoveLinkRequest(uri), message.chat().id());
                        response.ifPresent(linkResponse -> sb.append(linkResponse.url().toString()));
                    }
                }
                bot.send(new SendMessageAdapter(message.chat().id(), sb.toString())
                        .getSendMessage());
            }
        } else {
            nextHandler.handleMessage(update);
        }
    }

    public List<URI> parseUris(List<String> stringUris) {
        List<URI> uris = new ArrayList<>();
        for (String s : stringUris) {
            try {

                uris.add(new URI(s));

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return uris;
    }
}
