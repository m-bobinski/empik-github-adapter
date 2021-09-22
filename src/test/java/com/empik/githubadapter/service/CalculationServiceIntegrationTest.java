package com.empik.githubadapter.service;

import com.empik.githubadapter.model.github.GitHubProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculationServiceIntegrationTest {

    @Autowired
    private CalculationService service;

    @Test
    void shouldPickDefaultStrategyAndCalculate() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(4005).publicRepos(8).build();

        //when
        String result = service.calculate(profile);

        //then
        assertEquals("0.014981273408239700374531835205992510", result);
    }
}