package ru.tinkoff.edu.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.LinkParserApplication;
import ru.tinkoff.edu.scrapper.configuration.ApplicationConfig;

@SpringBootApplication()
//@EnableConfigurationProperties({ApplicationConfig.class})
@ConfigurationPropertiesScan
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
