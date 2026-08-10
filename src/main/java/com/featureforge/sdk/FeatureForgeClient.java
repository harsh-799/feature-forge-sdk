package com.featureforge.sdk;

import com.featureforge.sdk.dto.FeatureEvaluationRequest;
import com.featureforge.sdk.dto.FeatureEvaluationResponse;
import com.featureforge.sdk.exception.FeatureForgeException;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FeatureForgeClient {
    private final String apiKey;
    private final String baseUrl;
    private final HttpClient httpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FeatureForgeClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
    }

    public boolean isEnabled(String featureKey, String user) {
        FeatureEvaluationRequest request = new FeatureEvaluationRequest(featureKey, user);

        try {
            String jsonBody = objectMapper.writeValueAsString(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/v1/evaluate"))
                    .header("Content-Type", "application/json")
                    .header("X-API-Key",apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new FeatureForgeException(
                        response.body(),
                        response.statusCode()
                );
            }

            FeatureEvaluationResponse evaluationResponse =
                    objectMapper.readValue(
                            response.body(),
                            FeatureEvaluationResponse.class
                    );

            return evaluationResponse.isEnabled();

        } catch (FeatureForgeException e) {
            throw e;
        } catch (Exception e) {
            throw new FeatureForgeException(
                    "Failed to evaluate feature: " + e.getMessage(),
                    0
            );
        }
    }
}
