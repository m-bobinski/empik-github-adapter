package com.empik.githubadapter.service;

import com.empik.githubadapter.model.GitHubAdapterResponse;
import com.empik.githubadapter.model.github.GitHubProfile;
import com.empik.githubadapter.repository.RequestCountEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GitHubAdapterFacadeIntegrationTest {

    @Autowired
    private GitHubUsersFacade facade;

    @Autowired
    private RequestCountEntityRepository repository;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldProcessRequestCorrectly() {
        //given
        int id = 123;
        int followers = 4005;
        int publicRepos = 8;
        String avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4";
        String name = "name";
        ZonedDateTime createdAt = ZonedDateTime.now();
        String type = "type";
        String login = "login";
        String calculationResult = "0.014981273408239700374531835205992510";
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

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(GitHubProfile.class))).thenReturn(profile);

        //when
        GitHubAdapterResponse response = facade.getGitHubProfile(login);

        //then
        assertEquals(1, repository.count());
        assertEquals(login, repository.findAll().get(0).getLogin());
        assertEquals(String.valueOf(id), response.getId());
        assertEquals(login, response.getLogin());
        assertEquals(avatarUrl, response.getAvatarUrl());
        assertEquals(name, response.getName());
        assertEquals(createdAt, response.getCreatedAt());
        assertEquals(type, response.getType());
        assertEquals(calculationResult, response.getCalculations());
    }


}