package com.empik.githubadapter.service;

import com.empik.githubadapter.model.GitHubAdapterResponse;
import com.empik.githubadapter.model.github.GitHubProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GitHubProfileMappingServiceIntegrationTest {

    @Autowired
    private GitHubProfileMappingService gitHubProfileMappingService;

    @Test
    void shouldMapResponseCorrectly() {
        //given
        int id = 123;
        int followers = 4005;
        int publicRepos = 8;
        String avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4";
        String name = "name";
        ZonedDateTime createdAt = ZonedDateTime.now();
        String type = "type";
        String login = "login";
        GitHubProfile profile = GitHubProfile.builder()
                .id(id)
                .name(name)
                .followers(followers)
                .publicRepos(publicRepos)
                .avatarUrl(avatarUrl)
                .createdAt(createdAt)
                .type(type)
                .login(login)
                .build();

        //when
        GitHubAdapterResponse response = gitHubProfileMappingService.mapResponse(profile);

        //then
        assertEquals(String.valueOf(id), response.getId());
        assertEquals(login, response.getLogin());
        assertEquals(avatarUrl, response.getAvatarUrl());
        assertEquals(name, response.getName());
        assertEquals(createdAt, response.getCreatedAt());
        assertEquals(type, response.getType());
    }

}