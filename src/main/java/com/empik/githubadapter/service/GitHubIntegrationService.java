package com.empik.githubadapter.service;

import com.empik.githubadapter.configuration.GitHubIntegrationConfiguration;
import com.empik.githubadapter.constants.UrlConstants;
import com.empik.githubadapter.model.github.GitHubProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
class GitHubIntegrationService {

    private final GitHubIntegrationConfiguration configuration;
    private final RestTemplate restTemplate;

    /**
     * Request GitHub's users API to get user's profile
     *
     * @param login Users login to get
     * @return users profile
     */
    public GitHubProfile getUserProfile(String login) {
        return restTemplate.getForObject(buildUserProfileUrl(login), GitHubProfile.class);
    }

    private String buildUserProfileUrl(String login) {
        return UriComponentsBuilder.fromHttpUrl(configuration.getUrl())
                .path(UrlConstants.USERS_SUFFIX)
                .path(login)
                .toUriString();
    }

}
