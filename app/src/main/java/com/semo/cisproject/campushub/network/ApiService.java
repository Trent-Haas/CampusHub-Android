package com.semo.cisproject.campushub.network;

import com.semo.cisproject.campushub.model.Category;
import com.semo.cisproject.campushub.model.Offer;
import com.semo.cisproject.campushub.model.Order;
import com.semo.cisproject.campushub.model.OrderItem;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.model.UserAddress;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // --- Auth ---
    @POST("auth/login.php")
    Call<User> login(@Body LoginRequest body);

    @POST("auth/register.php")
    Call<User> register(@Body User user);

    // --- Users ---
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String userId);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") String userId, @Body User user);

    // --- Categories ---
    @GET("categories")
    Call<List<Category>> getCategories();

    // --- Products ---
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products")
    Call<List<Product>> getProductsByCategory(@Query("category_id") String categoryId);

    @GET("products")
    Call<List<Product>> getProductsByDepartment(@Query("department") String department);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") String productId);

    // --- Offers ---
    @GET("offers")
    Call<List<Offer>> getOffers();

    // --- Addresses ---
    @GET("users/{id}/addresses")
    Call<List<UserAddress>> getUserAddresses(@Path("id") String userId);

    @POST("users/{id}/addresses")
    Call<UserAddress> addUserAddress(@Path("id") String userId, @Body UserAddress address);

    @DELETE("users/{id}/addresses/{addressId}")
    Call<Void> deleteUserAddress(@Path("id") String userId, @Path("addressId") String addressId);

    // --- Orders ---
    @GET("users/{id}/orders")
    Call<List<Order>> getUserOrders(@Path("id") String userId);

    @POST("orders")
    Call<Order> placeOrder(@Body PlaceOrderRequest body);

    @GET("orders/{id}/items")
    Call<List<OrderItem>> getOrderItems(@Path("id") String orderId);

    // --- Request bodies ---

    class LoginRequest {
        public String email;
        public String password_hash;

        public LoginRequest(String email, String password_hash) {
            this.email = email;
            this.password_hash = password_hash;
        }
    }

    class PlaceOrderRequest {
        public String user_id;
        public String address_id;
        public String pickup_location;
        public List<OrderItemRequest> items;

        public PlaceOrderRequest(String user_id, String address_id, String pickup_location, List<OrderItemRequest> items) {
            this.user_id = user_id;
            this.address_id = address_id;
            this.pickup_location = pickup_location;
            this.items = items;
        }
    }

    class OrderItemRequest {
        public String product_id;
        public int quantity;

        public OrderItemRequest(String product_id, int quantity) {
            this.product_id = product_id;
            this.quantity = quantity;
        }
    }
}
