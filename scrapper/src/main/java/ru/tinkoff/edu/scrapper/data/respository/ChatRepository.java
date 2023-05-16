package ru.tinkoff.edu.scrapper.data.respository;

import java.util.List;
import java.util.Optional;
import ru.tinkoff.edu.scrapper.data.entity.Chat;

public interface ChatRepository {

    Chat save(Chat chat);

    void remove(Long id);

    List<Chat> findAll();

    Optional<Chat> findById(Long id);

    Chat findByChatId(Long tgChatId);
}
