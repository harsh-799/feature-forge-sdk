package com.featureforge.sdk.exception;

public class FeatureForgeException extends RuntimeException {

    private final int statusCode;

    public FeatureForgeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
