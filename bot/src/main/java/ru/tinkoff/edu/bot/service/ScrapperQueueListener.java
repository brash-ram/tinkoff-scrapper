package ru.tinkoff.edu.bot.service;

import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.bot.dto.LinkUpdate;
import ru.tinkoff.edu.bot.tg.BotMessageSender;

@RabbitListener(queues = "${rabbit.queue}")
@Component
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final BotMessageSender botMessageSender;
    private final Counter rabbitCounter;

    @RabbitHandler
    public void receiver(LinkUpdate update) {
        botMessageSender.sendMessage(update);
        rabbitCounter.increment();
    }
}
