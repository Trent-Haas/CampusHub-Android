package com.semo.cisproject.campushub.network;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
