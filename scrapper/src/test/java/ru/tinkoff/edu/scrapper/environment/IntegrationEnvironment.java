package ru.tinkoff.edu.scrapper.environment;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaChatRepository;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.scrapper.service.ChatService;
import ru.tinkoff.edu.scrapper.service.LinkService;
import ru.tinkoff.edu.scrapper.service.jpaImpl.JpaChatService;
import ru.tinkoff.edu.scrapper.service.jpaImpl.JpaLinkService;

@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfiguration.class)
public abstract class IntegrationEnvironment {

    private static final DataSource TEST_DATA_SOURCE;

    private static final PostgreSQLContainer POSTGRESQL_CONTAINER;


    static {
        String username = "postgres";
        String password = "qwerty";
        String dbName = "postgres";
        Integer dbPort = 5432;
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName(dbName)
            .withUsername(username)
            .withPassword(password)
            .withExposedPorts(dbPort);
        POSTGRESQL_CONTAINER.start();
        runMigration();
        TEST_DATA_SOURCE = DataSourceBuilder.create()
            .url(POSTGRESQL_CONTAINER.getJdbcUrl())
            .username(POSTGRESQL_CONTAINER.getUsername())
            .password(POSTGRESQL_CONTAINER.getPassword())
            .build();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    private static void runMigration() {
        try {
            String url = POSTGRESQL_CONTAINER.getJdbcUrl();
            String username = POSTGRESQL_CONTAINER.getUsername();
            String password = POSTGRESQL_CONTAINER.getPassword();
            Connection connection = DriverManager.getConnection(url, username, password);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Path changeLogPath = new File(".").toPath().toAbsolutePath().getParent().getParent()
                .resolve("migrations");
            Liquibase liquibase = new liquibase.Liquibase("master.xml",
                new DirectoryResourceAccessor(changeLogPath), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Configuration
    public static class JpaIntegrationEnvironmentConfiguration {
        @Bean
        public DataSource dataSource() {
            return TEST_DATA_SOURCE;
        }

        @Bean
        public ChatService jpaChatService(JpaChatRepository jpaChatRepository) {
            return new JpaChatService(jpaChatRepository);
        }

        @Bean
        public LinkService jpaLinkService(
                JpaLinkRepository jpaLinkRepository,
                ChatService chatService
        ) {
            return new JpaLinkService(jpaLinkRepository, chatService);
        }
    }

    @Configuration
    public static class IntegrationEnvironmentConfiguration {

        @Bean
        public DataSource dataSource() {
            return TEST_DATA_SOURCE;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new JdbcTransactionManager(dataSource);
        }

    }

}
