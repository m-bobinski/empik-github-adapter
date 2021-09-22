package com.empik.githubadapter.service;

import com.empik.githubadapter.model.github.GitHubProfile;
import com.empik.githubadapter.strategy.calculation.DefaultCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CalculationService {

    private final DefaultCalculationStrategy defaultCalculationStrategy;

    /**
     * Picks calculation strategy and returns the result
     *
     * @param gitHubProfile Profile gotten from GitHub's users API
     * @return Calculation result
     */
    public String calculate(GitHubProfile gitHubProfile) {
        return defaultCalculationStrategy.calculate(gitHubProfile);
    }
}
