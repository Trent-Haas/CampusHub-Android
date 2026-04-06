package com.semo.cisproject.campushub.repository;

import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.model.UserAddress;
import com.semo.cisproject.campushub.network.ApiCallback;
import com.semo.cisproject.campushub.network.ApiService;
import com.semo.cisproject.campushub.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final ApiService api;

    public UserRepository() {
        api = RetrofitClient.getInstance().getApiService();
    }

    public void login(String email, String password, ApiCallback<User> callback) {
        api.login(new ApiService.LoginRequest(email, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Login failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void register(User user, ApiCallback<User> callback) {
        api.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getUser(String userId, ApiCallback<User> callback) {
        api.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load user: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void updateUser(String userId, User user, ApiCallback<User> callback) {
        api.updateUser(userId, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to update user: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getUserAddresses(String userId, ApiCallback<List<UserAddress>> callback) {
        api.getUserAddresses(userId).enqueue(new Callback<List<UserAddress>>() {
            @Override
            public void onResponse(Call<List<UserAddress>> call, Response<List<UserAddress>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load addresses: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UserAddress>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void addUserAddress(String userId, UserAddress address, ApiCallback<UserAddress> callback) {
        api.addUserAddress(userId, address).enqueue(new Callback<UserAddress>() {
            @Override
            public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to add address: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserAddress> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void deleteUserAddress(String userId, String addressId, ApiCallback<Void> callback) {
        api.deleteUserAddress(userId, addressId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Failed to delete address: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
