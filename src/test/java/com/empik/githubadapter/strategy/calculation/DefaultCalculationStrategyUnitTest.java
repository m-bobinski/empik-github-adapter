package com.empik.githubadapter.strategy.calculation;

import com.empik.githubadapter.model.github.GitHubProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultCalculationStrategyUnitTest {

    @Test
    void shouldCalculateCorrectValue() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(4005).publicRepos(8).build();

        //when
        String result = new DefaultCalculationStrategy().calculate(profile);

        //then
        assertEquals("0.014981273408239700374531835205992510", result);
    }

    @Test
    void shouldCalculateCorrectValueWithZeroPublicRepos() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(4005).publicRepos(0).build();

        //when
        String result = new DefaultCalculationStrategy().calculate(profile);

        //then
        assertEquals("0.002996254681647940074906367041198502", result);
    }

    @Test
    void shouldReturnCalculationErrorMessageWithNullFollowersCount() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(null).publicRepos(8).build();

        //when
        String result = new DefaultCalculationStrategy().calculate(profile);

        //then
        assertNull(result);
    }

    @Test
    void shouldReturnCalculationErrorMessageWithZeroFollowers() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(0).publicRepos(8).build();

        //when
        String result = new DefaultCalculationStrategy().calculate(profile);

        //then
        assertNull(result);
    }

    @Test
    void shouldReturnCalculationErrorMessageWithNullPublicReposCount() {
        //given
        GitHubProfile profile = GitHubProfile.builder().followers(4005).publicRepos(null).build();

        //when
        String result = new DefaultCalculationStrategy().calculate(profile);

        //then
        assertNull(result);
    }

}