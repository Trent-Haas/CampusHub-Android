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

/**
 * Repository class responsible for handling Product-related data operations.
 * Acts as the intermediary between the ApiService and the UI layer.
 */
public class ProductRepository {

    private final ApiService api;

    public ProductRepository() {
        this.api = RetrofitClient.getInstance().getApiService();
    }

    /**
     * Retrieves the complete list of products available in the CampusHub marketplace.
     */
    public void getProducts(ApiCallback<List<Product>> callback) {
        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                handleResponse(response, callback, "Unable to load product list");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Filters products by a specific Category ID.
     */
    public void getProductsByCategory(String categoryId, ApiCallback<List<Product>> callback) {
        api.getProductsByCategory(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                handleResponse(response, callback, "Failed to load category products");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError("Connection error: " + t.getMessage());
            }
        });
    }

    /**
     * Filters products based on the SEMO Department (e.g., Computer Science, Art).
     */
    public void getProductsByDepartment(String department, ApiCallback<List<Product>> callback) {
        api.getProductsByDepartment(department).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                handleResponse(response, callback, "Failed to load department items");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError("Network failure: " + t.getMessage());
            }
        });
    }

    /**
     * Fetches detailed information for a single product.
     */
    public void getProduct(String productId, ApiCallback<Product> callback) {
        api.getProduct(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                handleResponse(response, callback, "Product details not found");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onError("Server error: " + t.getMessage());
            }
        });
    }

    /**
     * Retrieves all available marketplace categories for navigation.
     */
    public void getCategories(ApiCallback<List<Category>> callback) {
        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                handleResponse(response, callback, "Failed to load categories");
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                callback.onError("Connectivity issue: " + t.getMessage());
            }
        });
    }

    /**
     * Fetches the current active offers and promotional banners.
     */
    public void getOffers(ApiCallback<List<Offer>> callback) {
        api.getOffers().enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                handleResponse(response, callback, "Offers currently unavailable");
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                callback.onError("Network error fetching offers: " + t.getMessage());
            }
        });
    }

    /**
     * Generic helper method to process Retrofit responses and trigger the appropriate ApiCallback.
     */
    private <T> void handleResponse(Response<T> response, ApiCallback<T> callback, String defaultError) {
        if (response.isSuccessful() && response.body() != null) {
            callback.onSuccess(response.body());
        } else {
            callback.onError(defaultError + " (Status: " + response.code() + ")");
        }
    }
}