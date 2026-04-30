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

/**
 * Repository for managing User data and Authentication.
 * Connects the CampusHub frontend to the SEMO backend database.
 */
public class UserRepository {

    private final ApiService api;

    public UserRepository() {
        this.api = RetrofitClient.getInstance().getApiService();
    }

    /**
     * Authenticates a user against the marketplace database.
     */
    public void login(String email, String password, ApiCallback<User> callback) {
        api.login(new ApiService.LoginRequest(email, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                handleResponse(response, callback, "Authentication failed");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Registers a new student account.
     */
    public void register(User user, ApiCallback<User> callback) {
        api.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                handleResponse(response, callback, "Account registration failed");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Connection error: " + t.getMessage());
            }
        });
    }

    /**
     * Fetches current profile data for a specific student.
     */
    public void getUser(String userId, ApiCallback<User> callback) {
        api.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                handleResponse(response, callback, "User profile not found");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Server unreachable: " + t.getMessage());
            }
        });
    }

    /**
     * Updates profile details (Name, Mobile, etc.) for a user.
     */
    public void updateUser(String userId, User user, ApiCallback<User> callback) {
        api.updateUser(userId, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                handleResponse(response, callback, "Profile update failed");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network failure during update: " + t.getMessage());
            }
        });
    }

    /**
     * Retrieves all saved delivery addresses for a student.
     */
    public void getUserAddresses(String userId, ApiCallback<List<UserAddress>> callback) {
        api.getUserAddresses(userId).enqueue(new Callback<List<UserAddress>>() {
            @Override
            public void onResponse(Call<List<UserAddress>> call, Response<List<UserAddress>> response) {
                handleResponse(response, callback, "Unable to load addresses");
            }

            @Override
            public void onFailure(Call<List<UserAddress>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Adds a new delivery location to the user's profile.
     */
    public void addUserAddress(String userId, UserAddress address, ApiCallback<UserAddress> callback) {
        api.addUserAddress(userId, address).enqueue(new Callback<UserAddress>() {
            @Override
            public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                handleResponse(response, callback, "Failed to save address");
            }

            @Override
            public void onFailure(Call<UserAddress> call, Throwable t) {
                callback.onError("Connection lost: " + t.getMessage());
            }
        });
    }

    /**
     * Removes a delivery address from the student's records.
     */
    public void deleteUserAddress(String userId, String addressId, ApiCallback<Void> callback) {
        api.deleteUserAddress(userId, addressId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Address removal failed (Code: " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Generic helper to process API responses.
     */
    private <T> void handleResponse(Response<T> response, ApiCallback<T> callback, String defaultError) {
        if (response.isSuccessful() && response.body() != null) {
            callback.onSuccess(response.body());
        } else {
            callback.onError(defaultError + " (Status: " + response.code() + ")");
        }
    }
}