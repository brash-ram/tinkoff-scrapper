package ru.tinkoff.edu.bot.configuaration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbit", ignoreUnknownFields = false)
public record RabbitMQConfig(
        String queue,
        String exchange
) {
}
