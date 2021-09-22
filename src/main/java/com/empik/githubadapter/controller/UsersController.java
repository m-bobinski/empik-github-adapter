package com.empik.githubadapter.controller;

import com.empik.githubadapter.model.ErrorDetails;
import com.empik.githubadapter.model.GitHubAdapterResponse;
import com.empik.githubadapter.service.GitHubUsersFacade;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
class UsersController {

    private final GitHubUsersFacade gitHubAdapterFacade;

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = GitHubAdapterResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)})
    public ResponseEntity<GitHubAdapterResponse> getUserProfile(@PathVariable String login) {
        return ResponseEntity.ok(gitHubAdapterFacade.getGitHubProfile(login));
    }
}
