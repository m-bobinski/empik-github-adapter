package com.empik.githubadapter.configuration;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;


@Getter
@Validated
@ConfigurationProperties(prefix = "github")
@ConstructorBinding
@RequiredArgsConstructor
public class GitHubIntegrationConfiguration {

    @NonNull
    private final String url;

}
