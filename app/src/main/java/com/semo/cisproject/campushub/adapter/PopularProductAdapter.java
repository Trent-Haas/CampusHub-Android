package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.BaseActivity;
import com.semo.cisproject.campushub.activity.ProductViewActivity;
import com.semo.cisproject.campushub.interfaces.AddorRemoveCallbacks;
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context context;
    private String tag;
    private LocalStorage localStorage;
    private Gson gson;
    private List<Cart> cartList;

    public PopularProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        initUtils();
    }

    public PopularProductAdapter(List<Product> productList, Context context, String tag) {
        this.productList = productList;
        this.context = context;
        this.tag = tag;
        initUtils();
    }

    private void initUtils() {
        this.localStorage = new LocalStorage(context);
        this.gson = new Gson();
        this.cartList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (tag != null && tag.equalsIgnoreCase("Home")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_popular_home_products, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_popuular_products, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Product product = productList.get(position);
        cartList = ((BaseActivity) context).getCartList();

        holder.title.setText(product.getTitle());
        holder.attribute.setText(product.getAttribute());
        holder.currency.setText("$");
        holder.price.setText(product.getPrice());
        holder.quantity.setText("1");

        Picasso.get()
                .load(product.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() { holder.progressBar.setVisibility(View.GONE); }
                    @Override
                    public void onError(Exception e) { holder.progressBar.setVisibility(View.GONE); }
                });

        updateQuantityUI(holder, product.getId());

        holder.shopNow.setOnClickListener(v -> addToCart(holder, product, position));
        holder.plus.setOnClickListener(v -> changeQuantity(holder, product, 1));
        holder.minus.setOnClickListener(v -> changeQuantity(holder, product, -1));

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductViewActivity.class);
            intent.putExtra("id", product.getId());
            intent.putExtra("title", product.getTitle());
            intent.putExtra("image", product.getImage());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("currency", "$");
            intent.putExtra("attribute", product.getAttribute());
            intent.putExtra("discount", product.getDiscount());
            intent.putExtra("description", product.getDescription());
            context.startActivity(intent);
        });
    }

    private void updateQuantityUI(MyViewHolder holder, String productId) {
        boolean isInCart = false;
        for (Cart item : cartList) {
            if (item.getId().equalsIgnoreCase(productId)) {
                holder.shopNow.setVisibility(View.GONE);
                holder.quantity_ll.setVisibility(View.VISIBLE);
                holder.quantity.setText(item.getQuantity());
                isInCart = true;
                break;
            }
        }
        if (!isInCart) {
            holder.shopNow.setVisibility(View.VISIBLE);
            holder.quantity_ll.setVisibility(View.GONE);
        }
    }

    private void addToCart(MyViewHolder holder, Product product, int position) {
        String qty = holder.quantity.getText().toString();
        double subtotal = Double.parseDouble(product.getPrice()) * Integer.parseInt(qty);

        Cart newCartItem = new Cart(
                product.getId(), product.getTitle(), product.getImage(),
                "$", product.getPrice(), product.getAttribute(), qty, String.valueOf(subtotal)
        );

        cartList = ((BaseActivity) context).getCartList();
        cartList.add(newCartItem);
        saveCartChanges();

        if (context instanceof AddorRemoveCallbacks) {
            ((AddorRemoveCallbacks) context).onAddProduct();
        }
        notifyItemChanged(position);
    }

    private void changeQuantity(MyViewHolder holder, Product product, int delta) {
        cartList = ((BaseActivity) context).getCartList();
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId().equalsIgnoreCase(product.getId())) {
                int currentQty = Integer.parseInt(cartList.get(i).getQuantity());
                int newQty = currentQty + delta;

                if (newQty > 0) {
                    cartList.get(i).setQuantity(String.valueOf(newQty));
                    double price = Double.parseDouble(product.getPrice());
                    cartList.get(i).setSubTotal(String.valueOf(price * newQty));
                    holder.quantity.setText(String.valueOf(newQty));
                    saveCartChanges();
                } else {
                    cartList.remove(i);
                    saveCartChanges();
                    if (context instanceof AddorRemoveCallbacks) {
                        ((AddorRemoveCallbacks) context).onRemoveProduct();
                    }
                    updateQuantityUI(holder, product.getId());
                }
                break;
            }
        }
    }

    private void saveCartChanges() {
        localStorage.setCart(gson.toJson(cartList));
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, attribute, currency, price, shopNow, plus, minus, quantity;
        ProgressBar progressBar;
        LinearLayout quantity_ll;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            attribute = itemView.findViewById(R.id.product_attribute);
            price = itemView.findViewById(R.id.product_price);
            currency = itemView.findViewById(R.id.product_currency);
            shopNow = itemView.findViewById(R.id.shop_now);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity_ll = itemView.findViewById(R.id.quantity_ll);
            quantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}