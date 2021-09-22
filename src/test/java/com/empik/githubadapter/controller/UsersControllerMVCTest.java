package com.empik.githubadapter.controller;

import com.empik.githubadapter.model.github.GitHubProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc
@SpringBootTest
class UsersControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void shouldReturn200KAndCorrectResponse() throws Exception {
        //given
        int id = 123;
        int followers = 4005;
        int publicRepos = 8;
        String avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4";
        String name = "name";
        String type = "type";
        String login = "login";
        String calculationResult = "0.014981273408239700374531835205992510";
        GitHubProfile profile = GitHubProfile.builder()
                .id(id)
                .name(name)
                .followers(followers)
                .publicRepos(publicRepos)
                .avatarUrl(avatarUrl)
                .type(type)
                .login(login)
                .build();

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(GitHubProfile.class))).thenReturn(profile);

        //when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/test"));

        //then
        action.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("calculations").value(calculationResult))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("login").value(login))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("type").value(type))
                .andExpect(MockMvcResultMatchers.jsonPath("avatarUrl").value(avatarUrl));
    }

    @Test
    void shouldReturnNotFoundAndCorrectErrorMessage() throws Exception {
        //given
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(GitHubProfile.class))).thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Status text", new HttpHeaders(), null, null));

        //when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/test"));

        //then
        action.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("User not found"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenGitHubReturns500() throws Exception {
        //given
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(GitHubProfile.class))).thenThrow(HttpClientErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Status text", new HttpHeaders(), null, null));

        //when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/test"));

        //then
        action.andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Internal error occurred"))
                .andExpect(MockMvcResultMatchers.jsonPath("spanId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("traceId").isString());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedExceptionOccurs() throws Exception {
        //given
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(GitHubProfile.class))).thenThrow(new RuntimeException("Test exception"));

        //when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/test"));

        //then
        action.andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Internal error occurred"))
                .andExpect(MockMvcResultMatchers.jsonPath("spanId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("traceId").isString());
    }
}