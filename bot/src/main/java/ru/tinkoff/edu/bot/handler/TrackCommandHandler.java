package ru.tinkoff.edu.bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.client.ScrapperClient;
import ru.tinkoff.edu.bot.dto.scrapper.request.AddLinkRequest;
import ru.tinkoff.edu.bot.dto.scrapper.response.LinkResponse;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;
import ru.tinkoff.edu.linkParser.service.LinkParseService;

@Component
@ComponentScan("ru.tinkoff.edu.linkParser.service")
@Slf4j
public class TrackCommandHandler extends MessageHandler {

    private final ScrapperClient scrapperClient;

    private final LinkParseService linkParseService;

    public TrackCommandHandler(Bot bot, ScrapperClient scrapperClient, LinkParseService linkParseService) {
        super(bot);
        this.scrapperClient = scrapperClient;
        this.linkParseService = linkParseService;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        List<String> stringUri = new ArrayList<>(List.of(message.text().split(" ")));
        String allowedMessage = stringUri.remove(0);
        if ("/track".equals(allowedMessage)) {
            if (stringUri.size() == 0) {
                String messageForGetLink = "Чтобы добавить ссылку отправьте команду "
                    + "/track с нужными ссылками, разделенными пробелами.";
                bot.send(new SendMessageAdapter(message.chat().id(), messageForGetLink)
                        .getSendMessage());
            } else {
                List<URI> urls = parseUris(stringUri);
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("Добавлено %d из %d ссылок", urls.size(), stringUri.size()))
                        .append("\n");
                if (urls.size() != 0) {
                    sb.append("Добавлены следующие ссылки:\n");
                    for (URI uri : urls) {
                        Optional<LinkResponse> response = scrapperClient.addLink(new AddLinkRequest(uri), message.chat().id());
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

                    URI uri = new URI(s);
                    if (linkParseService.parseLink(uri) != null) {
                        uris.add(uri);
                    }

                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            return uris;
    }
}
