package ru.tinkoff.edu.scrapper.repository.jpa;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
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
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.environment.IntegrationEnvironment;
import ru.tinkoff.edu.scrapper.service.ChatService;
import ru.tinkoff.edu.scrapper.service.LinkService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import(IntegrationEnvironment.JpaIntegrationEnvironmentConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaLinkServiceTests {

    @Autowired
    private LinkService jpaLinkService;

    @Autowired
    private ChatService jpaChatService;

    private static Link testLink;

    private static Chat testChat;

    @BeforeEach
    public void setTestLink() throws URISyntaxException {
        testLink = new Link()
                .setUrl(new URI("http://localhost:8080"))
                .setLastUpdate(new Timestamp(400000L));
        testChat = new Chat()
                .setChatId(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTimeUpdateTest() {
        Link link = jpaLinkService.add(testChat.getChatId(), testLink.getUrl());
        jpaLinkService.updateTimeUpdate(link.getId(), testLink.getLastUpdate());
        assertEquals(jpaLinkService.getAll().get(0).getLastUpdate(), testLink.getLastUpdate());
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        jpaChatService.register(testChat.getChatId());
        Link link = jpaLinkService.add(testChat.getChatId(), testLink.getUrl());
        assertNotNull(link.getId());
    }

}
