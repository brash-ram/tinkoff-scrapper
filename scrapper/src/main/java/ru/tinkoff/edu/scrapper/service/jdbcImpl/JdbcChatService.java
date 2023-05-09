package ru.tinkoff.edu.scrapper.service.jdbcImpl;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.respository.ChatRepository;
import ru.tinkoff.edu.scrapper.service.ChatService;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Chat register(long tgChatId) {
        return chatRepository.save(new Chat()
                .setChatId(tgChatId)
        );
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.remove(chatRepository.findByChatId(tgChatId).getId());
    }

    @Override
    public Chat getById(long id) {
        return chatRepository.findByChatId(id);
    }

    @Override
    public Chat getByChatId(long tgChatId) {
        return chatRepository.findByChatId(tgChatId);
    }
}
