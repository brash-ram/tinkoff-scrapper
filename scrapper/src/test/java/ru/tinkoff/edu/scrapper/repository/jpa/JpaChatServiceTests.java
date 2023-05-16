package ru.tinkoff.edu.scrapper.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaChatRepository;
import ru.tinkoff.edu.scrapper.environment.IntegrationEnvironment;
import ru.tinkoff.edu.scrapper.service.ChatService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import(IntegrationEnvironment.JpaIntegrationEnvironmentConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaChatServiceTests {

    private static Chat testChat;

    @Autowired
    private ChatService jpaChatService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @BeforeEach
    public void setTestChat() {
        testChat = new Chat()
                .setChatId(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void registerTest() {
        Chat registeredChat = jpaChatService.register(testChat.getChatId());
        assertNotNull(jpaChatService.getById(registeredChat.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void unregisterTest() {
        Chat registeredChat = jpaChatService.register(testChat.getChatId());
        jpaChatService.unregister(registeredChat.getChatId());
        assertNull(jpaChatService.getById(registeredChat.getId()));
    }


    @Test
    @Transactional
    @Rollback
    public void getByChatIdTest() {
        Chat registeredChat = jpaChatService.register(testChat.getChatId());
        Chat savedChat = jpaChatService.getByChatId(registeredChat.getChatId());
        assertEquals(registeredChat, savedChat);
    }
}
