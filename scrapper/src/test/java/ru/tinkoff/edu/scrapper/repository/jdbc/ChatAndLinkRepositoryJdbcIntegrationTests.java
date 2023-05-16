package ru.tinkoff.edu.scrapper.repository.jdbc;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.ChatRepository;
import ru.tinkoff.edu.scrapper.data.respository.LinkRepository;
import ru.tinkoff.edu.scrapper.data.respository.jdbcImpl.ChatRepositoryJdbcImpl;
import ru.tinkoff.edu.scrapper.data.respository.jdbcImpl.LinkRepositoryJdbcImpl;
import ru.tinkoff.edu.scrapper.environment.IntegrationEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        IntegrationEnvironment.IntegrationEnvironmentConfiguration.class,
        ChatRepositoryJdbcImpl.class,
        LinkRepositoryJdbcImpl.class
})
public class ChatAndLinkRepositoryJdbcIntegrationTests {

    private static Chat testChat;
    private static Link testLink;

    @Autowired
    private ChatRepository chatRepositoryJdbcImpl;

    @Autowired
    private LinkRepository linkRepository;

    @BeforeAll
    public static void setTestData() throws URISyntaxException {
        testChat = new Chat()
                .setChatId(1L);
        testLink = new Link()
                .setUrl(new URI("http://localhost:8080"))
                .setLastUpdate(new Timestamp(System.currentTimeMillis()))
                .setChat(testChat);
    }

    @Test
    @Transactional
    @Rollback
    public void saveChatAndLinkTest() {
        chatRepositoryJdbcImpl.save(testChat);
        linkRepository.save(testLink);

        Optional<Chat> savingChat = chatRepositoryJdbcImpl.findById(testChat.getId());

        assertTrue(savingChat.isPresent());
        assertEquals(savingChat.get().getLinks().size(), 1);
    }


    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteChatAndLinkTest() {
        chatRepositoryJdbcImpl.save(testChat);
        linkRepository.save(testLink);
        linkRepository.save(testLink);

        Optional<Chat> savingChat = chatRepositoryJdbcImpl.findById(testChat.getId());

        assertTrue(savingChat.isPresent());
        assertEquals(savingChat.get().getLinks().size(), 2);

        chatRepositoryJdbcImpl.remove(savingChat.get().getId());

        savingChat = chatRepositoryJdbcImpl.findById(testChat.getId());
        List<Link> links = linkRepository.findAll();

        assertTrue(links.isEmpty());
    }
}
