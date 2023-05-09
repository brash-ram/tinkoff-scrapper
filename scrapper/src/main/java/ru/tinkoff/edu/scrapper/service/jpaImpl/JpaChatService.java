package ru.tinkoff.edu.scrapper.service.jpaImpl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaChatRepository;
import ru.tinkoff.edu.scrapper.service.ChatService;


@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository jpaChatRepository;

    @Override
    public Chat register(long tgChatId) {
        return jpaChatRepository.save(new Chat()
                .setChatId(tgChatId)
        );
    }

    @Override
    public void unregister(long tgChatId) {
        jpaChatRepository.delete(jpaChatRepository.findByChatId(tgChatId));
    }

    @Override
    public Chat getById(long id) {
        Chat chat = null;
        Optional<Chat> optionalChat = jpaChatRepository.findById(id);
        if (optionalChat.isPresent()) {
            chat = optionalChat.get();
        }
        return chat;
    }

    @Override
    public Chat getByChatId(long tgChatId) {
        return jpaChatRepository.findByChatId(tgChatId);
    }
}
