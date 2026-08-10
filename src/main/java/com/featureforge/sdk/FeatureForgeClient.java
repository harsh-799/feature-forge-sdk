package com.featureforge.sdk;

import com.featureforge.sdk.dto.FeatureEvaluationRequest;

import java.net.http.HttpClient;

public class FeatureForgeClient {
    private final String apiKey;
    private final String url;
    private final HttpClient httpClient;

    public FeatureForgeClient(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
        this.httpClient = HttpClient.newHttpClient();
    }
}
