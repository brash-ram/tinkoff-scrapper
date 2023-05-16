package ru.tinkoff.edu.scrapper.client;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.scrapper.dto.response.StackOverflowQuestionResponse;

@Service
@RequiredArgsConstructor
public class StackOverflowClient {
    private final WebClient stackOverflowWebClient;

    @Value("${default.timeout}")
    private Integer defaultTimeout;

    public StackOverflowQuestionResponse getQuestionInfo(Long id) {
        return stackOverflowWebClient.get()
                .uri("/questions/{id}?site=stackoverflow", id)
                .retrieve()
                .bodyToMono(StackOverflowQuestionResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .blockOptional()
                .orElse(null);
    }
}
