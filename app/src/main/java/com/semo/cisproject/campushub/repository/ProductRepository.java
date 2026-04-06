package com.semo.cisproject.campushub.repository;

import com.semo.cisproject.campushub.model.Category;
import com.semo.cisproject.campushub.model.Offer;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.network.ApiCallback;
import com.semo.cisproject.campushub.network.ApiService;
import com.semo.cisproject.campushub.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private final ApiService api;

    public ProductRepository() {
        api = RetrofitClient.getInstance().getApiService();
    }

    public void getProducts(ApiCallback<List<Product>> callback) {
        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getProductsByCategory(String categoryId, ApiCallback<List<Product>> callback) {
        api.getProductsByCategory(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getProductsByDepartment(String department, ApiCallback<List<Product>> callback) {
        api.getProductsByDepartment(department).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getProduct(String productId, ApiCallback<Product> callback) {
        api.getProduct(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load product: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getCategories(ApiCallback<List<Category>> callback) {
        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load categories: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getOffers(ApiCallback<List<Offer>> callback) {
        api.getOffers().enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load offers: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
