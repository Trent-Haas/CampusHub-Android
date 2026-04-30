package com.semo.cisproject.campushub.network;

/**
 * Generic callback interface for handling asynchronous network responses.
 * Standardized for the CampusHub marketplace network layer.
 *
 * @param <T> The expected response type.
 */
public interface ApiCallback<T> {

    /**
     * Triggered when the API request completes successfully.
     * @param result The data object returned from the server.
     */
    void onSuccess(T result);

    /**
     * Triggered when the API request fails due to network, server, or parsing errors.
     * @param errorMessage A readable description of the failure.
     */
    void onError(String errorMessage);
}