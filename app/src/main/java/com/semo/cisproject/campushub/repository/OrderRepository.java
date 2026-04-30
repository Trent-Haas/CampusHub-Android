package com.semo.cisproject.campushub.repository;

import com.semo.cisproject.campushub.model.Order;
import com.semo.cisproject.campushub.model.OrderItem;
import com.semo.cisproject.campushub.network.ApiCallback;
import com.semo.cisproject.campushub.network.ApiService;
import com.semo.cisproject.campushub.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private final ApiService api;

    public OrderRepository() {
        this.api = RetrofitClient.getInstance().getApiService();
    }

    /**
     * Fetches all orders associated with a specific student ID.
     */
    public void getUserOrders(String userId, ApiCallback<List<Order>> callback) {
        api.getUserOrders(userId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                handleResponse(response, callback, "Failed to load order history");
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Submits a new order request to the CampusHub server.
     */
    public void placeOrder(ApiService.PlaceOrderRequest request, ApiCallback<Order> callback) {
        api.placeOrder(request).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                handleResponse(response, callback, "Order placement failed");
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callback.onError("Submission error: " + t.getMessage());
            }
        });
    }

    /**
     * Retrieves specific items contained within a single order.
     */
    public void getOrderItems(String orderId, ApiCallback<List<OrderItem>> callback) {
        api.getOrderItems(orderId).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                handleResponse(response, callback, "Unable to load item details");
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                callback.onError("Connection error: " + t.getMessage());
            }
        });
    }

    /**
     * Internal helper to standardize response handling across all calls.
     */
    private <T> void handleResponse(Response<T> response, ApiCallback<T> callback, String defaultError) {
        if (response.isSuccessful() && response.body() != null) {
            callback.onSuccess(response.body());
        } else {
            callback.onError(defaultError + " (Code: " + response.code() + ")");
        }
    }
}