package ru.tinkoff.edu.bot.configuaration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.tinkoff.edu.linkParser.service.LinkParseService;
import ru.tinkoff.edu.linkParser.service.parser.ParserConfiguration;

@Configuration
@RequiredArgsConstructor
@Import({LinkParseService.class, ParserConfiguration.class})
public class ApplicationConfiguration {

    private final ApplicationConfig config;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(config.token());
    }
}
