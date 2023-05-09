package ru.tinkoff.edu.scrapper.data.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.scrapper.data.entity.Chat;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Chat findByChatId(Long chatId);
}