package ru.tinkoff.edu.bot.handler;

import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.bot.tg.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class MessageHandlerConfiguration {
    private final ApplicationContext context;
    @Bean
    public MessageHandler messageHandler() {
        List<Class<? extends MessageHandler>> handlers = new ArrayList<>(new Reflections(
                ClasspathHelper.forClass(MessageHandler.class))
                .getSubTypesOf(MessageHandler.class));

        handlers.remove(DefaultHandler.class);
        handlers.remove(StartCommandHandler.class);

        MessageHandler messageHandler = context.getBean(StartCommandHandler.class);

        Class<? extends MessageHandler> currentHandlerClass = handlers.remove(0);
        MessageHandler currentHandler = context.getBean(currentHandlerClass);
        messageHandler.setNextHandler(currentHandler);

        for (Class<? extends MessageHandler> handlerClass : handlers) {
            currentHandler = currentHandler.setNextHandler(context.getBean(handlerClass));
        }
        currentHandler.setNextHandler(context.getBean(DefaultHandler.class));

        return messageHandler;
    }
}
