package ru.tinkoff.edu.scrapper.repository.jpa;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.scrapper.environment.IntegrationEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import(IntegrationEnvironment.JpaIntegrationEnvironmentConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LinkRepositoryJpaImplTests  {
    private static Link testLink;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @BeforeAll
    public static void setTestLink() throws URISyntaxException {
        testLink = new Link()
                .setUrl(new URI("http://localhost:8080"))
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Link link = jpaLinkRepository.save(testLink);
        assertNotNull(link.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Link link = jpaLinkRepository.save(testLink);
        jpaLinkRepository.delete(link);
        assertEquals(jpaLinkRepository.findAll().size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll() {
        jpaLinkRepository.save(testLink);
        jpaLinkRepository.save(testLink);

        assertEquals(jpaLinkRepository.findAll().size(), 2);
    }

    @Test
    @Transactional
    @Rollback
    public void updateLastUpdateAll() {
        Link link = jpaLinkRepository.save(testLink);
        Timestamp timestamp = new Timestamp(100000000L);
        jpaLinkRepository.save(link.setLastUpdate(timestamp));
        assertEquals(jpaLinkRepository.findAll().size(), 1);
        link = jpaLinkRepository.findAll().get(0);

        assertEquals(link.getLastUpdate(), timestamp);
    }

}
