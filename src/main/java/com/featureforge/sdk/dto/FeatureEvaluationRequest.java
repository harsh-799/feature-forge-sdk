package com.featureforge.sdk.dto;

public class FeatureEvaluationRequest {
    private final String featureKey;
    private final String user;

    public FeatureEvaluationRequest(String featureKey, String user) {
        this.featureKey = featureKey;
        this.user = user;
    }

    public String getFeatureKey() {
        return featureKey;
    }

    public String getUser() {
        return user;
    }
}
