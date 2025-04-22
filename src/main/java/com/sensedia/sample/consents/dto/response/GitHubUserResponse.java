package com.sensedia.sample.consents.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubUserResponse(
        String login,
        String name,
        String bio,
        @JsonProperty("public_repos") int publicRepos,
        int followers,
        int following
) {}