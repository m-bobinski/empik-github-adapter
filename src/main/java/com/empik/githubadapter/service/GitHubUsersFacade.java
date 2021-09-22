package com.empik.githubadapter.service;

import com.empik.githubadapter.model.GitHubAdapterResponse;
import com.empik.githubadapter.model.github.GitHubProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubUsersFacade {

    private final GitHubProfileMappingService mappingService;
    private final RequestCountService requestCountService;
    private final GitHubIntegrationService gitHubIntegrationService;
    private final CalculationService calculationService;

    /**
     * Facade for processing GitHub users API requests
     *
     * @param login Users login to process
     * @return Response object for API
     */
    public GitHubAdapterResponse getGitHubProfile(String login) {
        requestCountService.processCounter(login);
        GitHubProfile userProfile = gitHubIntegrationService.getUserProfile(login);
        GitHubAdapterResponse response = mappingService.mapResponse(userProfile);
        response.setCalculations(calculationService.calculate(userProfile));
        return response;
    }
}
