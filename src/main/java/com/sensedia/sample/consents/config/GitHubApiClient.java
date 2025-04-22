package com.sensedia.sample.consents.config;

import com.sensedia.sample.consents.dto.response.GitHubUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubApiClient {

    private final WebClient webClient;

    public GitHubApiClient(@Value("${external.github.api.url}") String githubApiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(githubApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> getUserLogin(String username) {
        return webClient.get()
                .uri("/users/{username}", username)
                .retrieve()
                .bodyToMono(GitHubUserResponse.class)
                .map(GitHubUserResponse::login)
                .onErrorReturn("Informação não disponível");
    }
}
