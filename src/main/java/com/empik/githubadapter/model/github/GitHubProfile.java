package com.empik.githubadapter.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubProfile {
    private String login;
    private Integer id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String type;
    private String name;
    @JsonProperty("public_repos")
    private Integer publicRepos;
    private Integer followers;
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

}
