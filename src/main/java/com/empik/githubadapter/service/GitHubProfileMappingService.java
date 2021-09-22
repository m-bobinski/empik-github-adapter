package com.empik.githubadapter.service;

import com.empik.githubadapter.model.GitHubAdapterResponse;
import com.empik.githubadapter.model.github.GitHubProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class GitHubProfileMappingService {

    /**
     * Maps GitHub's response
     *
     * @param profile Profile gotten from GitHub's users API
     * @return Mapped response
     */
    public GitHubAdapterResponse mapResponse(GitHubProfile profile) {
        GitHubAdapterResponse response = new GitHubAdapterResponse();
        Optional.ofNullable(profile.getId())
                .map(String::valueOf)
                .ifPresent(response::setId);
        response.setLogin(profile.getLogin());
        response.setName(profile.getName());
        response.setType(profile.getType());
        response.setAvatarUrl(profile.getAvatarUrl());
        response.setCreatedAt(profile.getCreatedAt());
        return response;
    }
}
