package ru.tinkoff.edu.bot.configuaration;

import com.pengrad.telegrambot.TelegramBot;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
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

//    @Bean
//    public MeterRegistry prometheusMeterRegistry() {
//        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
//    }

    @Bean
    public Counter rabbitCounter(MeterRegistry prometheusMeterRegistry) {
        return Counter.builder("rabbitmq.messages.processed")
            .description("Number of processed RabbitMQ messages")
            .register(prometheusMeterRegistry);
    }
}
