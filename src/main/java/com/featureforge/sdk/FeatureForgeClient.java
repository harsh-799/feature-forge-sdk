package com.featureforge.sdk;

import com.featureforge.sdk.dto.FeatureEvaluationRequest;
import com.featureforge.sdk.dto.FeatureEvaluationResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FeatureForgeClient {
    private final String apiKey;
    private final String url;
    private final HttpClient httpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FeatureForgeClient(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
        this.httpClient = HttpClient.newHttpClient();
    }

    public boolean isEnabled(String featureKey, String user) {
        FeatureEvaluationRequest request = new FeatureEvaluationRequest(featureKey, user);

        try {
            String jsonBody = objectMapper.writeValueAsString(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url + "/api/v1/evaluate"))
                    .header("Content-Type", "application/json")
                    .header("X-API-Key",apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            FeatureEvaluationResponse evaluationResponse =
                    objectMapper.readValue(
                            response.body(),
                            FeatureEvaluationResponse.class
                    );

            return evaluationResponse.isEnabled();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create evaluation request, e");
        }
    }
}
