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
        api = RetrofitClient.getInstance().getApiService();
    }

    public void getUserOrders(String userId, ApiCallback<List<Order>> callback) {
        api.getUserOrders(userId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load orders: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void placeOrder(ApiService.PlaceOrderRequest request, ApiCallback<Order> callback) {
        api.placeOrder(request).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to place order: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getOrderItems(String orderId, ApiCallback<List<OrderItem>> callback) {
        api.getOrderItems(orderId).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load order items: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
