package ru.tinkoff.edu.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.scrapper.environment.IntegrationEnvironment;

@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfiguration.class})
public class IntegrationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void databaseTest() {
        jdbcTemplate.queryForList("SELECT * FROM chats");
    }
}
