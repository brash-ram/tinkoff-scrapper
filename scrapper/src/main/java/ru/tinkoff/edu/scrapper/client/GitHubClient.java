package ru.tinkoff.edu.scrapper.client;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.scrapper.dto.response.GitHubRepositoryInfoResponse;

@Service
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient gitHubWebClient;
    @Value("${default.timeout}")
    private Integer defaultTimeout;

    @Value("${api.github.token}")
    private String githubToken;

    public GitHubRepositoryInfoResponse getGitHubRepositoryInfo(String username, String repositoryName) {
        return gitHubWebClient.get()
                .uri("/repos/{username}/{repositoryName}", username, repositoryName)
                .header("Authorization", "Bearer " + githubToken)
                .retrieve()
                .bodyToMono(GitHubRepositoryInfoResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .blockOptional()
                .orElse(null);
    }
}
