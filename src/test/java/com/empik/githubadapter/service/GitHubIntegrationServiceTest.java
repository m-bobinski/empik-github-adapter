package com.empik.githubadapter.service;

import com.empik.githubadapter.configuration.GitHubIntegrationConfiguration;
import com.empik.githubadapter.model.github.GitHubProfile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GitHubIntegrationServiceTest {

    @Autowired
    private GitHubIntegrationConfiguration configuration;

    @Autowired
    private GitHubIntegrationService gitHubIntegrationService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void shouldBuildUrlCorrectly() {
        //when
        gitHubIntegrationService.getUserProfile("test");

        //then
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(restTemplate).getForObject(argument.capture(), Mockito.eq(GitHubProfile.class));
        assertEquals(configuration.getUrl() + "/users/test", argument.getValue());
    }
}