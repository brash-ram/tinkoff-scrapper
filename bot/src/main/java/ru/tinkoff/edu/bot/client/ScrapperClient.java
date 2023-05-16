package ru.tinkoff.edu.bot.client;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.bot.dto.scrapper.request.AddLinkRequest;
import ru.tinkoff.edu.bot.dto.scrapper.request.RemoveLinkRequest;
import ru.tinkoff.edu.bot.dto.scrapper.response.LinkResponse;
import ru.tinkoff.edu.bot.dto.scrapper.response.ListLinksResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScrapperClient {

    private final WebClient scrapperWebClient;

    @Value("${default.timeout}")
    private Integer defaultTimeout;

    @Value("${baseUrl.scrapper}")
    private String scrapperUrl;

    public Optional<LinkResponse> addLink(AddLinkRequest addLinkRequest, Long id) {
        return scrapperWebClient.post()
                .uri(scrapperUrl + "/links")
                .header("Tg-Chat-Id", id.toString())
                .body(BodyInserters.fromValue(addLinkRequest))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<LinkResponse> deleteLink(RemoveLinkRequest removeLinkRequest, Long id) {
        return scrapperWebClient.post()
                .uri(scrapperUrl + "/links/delete")
                .header("Tg-Chat-Id", id.toString())
                .body(BodyInserters.fromValue(removeLinkRequest))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<ListLinksResponse> getLinks(Long id) {
        return scrapperWebClient.get()
                .uri(scrapperUrl + "/links")
                .header("Tg-Chat-Id", id.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<String> addChat(Long id) {
        return scrapperWebClient.post()
                .uri(scrapperUrl + "/tg-chat/" + id.toString())
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<String> deleteChat(Long id) {
        return scrapperWebClient.delete()
                .uri(scrapperUrl + "/tg-chat/" + id.toString())
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }
}
