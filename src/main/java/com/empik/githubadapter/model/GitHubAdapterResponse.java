package com.empik.githubadapter.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class GitHubAdapterResponse {
    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private ZonedDateTime createdAt;
    private String calculations;
}
