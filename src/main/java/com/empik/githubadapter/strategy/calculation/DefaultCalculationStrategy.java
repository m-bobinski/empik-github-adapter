package com.empik.githubadapter.strategy.calculation;

import com.empik.githubadapter.model.github.GitHubProfile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
public class DefaultCalculationStrategy implements CalculationStrategy {

    @Override
    public String calculate(GitHubProfile profile) {
        //Behaviour wasn't described in the task, so I assumed it should just not return calculation result if calculation is impossible
        if (profile.getFollowers() == null || profile.getFollowers() == 0 || profile.getPublicRepos() == null) {
            return null;
        }
        return BigDecimal.valueOf(6).divide(BigDecimal.valueOf(profile.getFollowers()), MathContext.DECIMAL128).multiply(BigDecimal.valueOf(2 + profile.getPublicRepos())).toString();
    }

}
