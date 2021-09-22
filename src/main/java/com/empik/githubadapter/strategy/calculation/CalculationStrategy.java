package com.empik.githubadapter.strategy.calculation;

import com.empik.githubadapter.model.github.GitHubProfile;

public interface CalculationStrategy {

    /**
     * Performs calculation on a given profile
     *
     * @param profile Profile gotten from GitHub's users API
     * @return Calculation result
     */
    String calculate(GitHubProfile profile);

}
