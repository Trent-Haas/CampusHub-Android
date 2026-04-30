package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.CartActivity;
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Cart> cartList;
    private Context context;
    private LocalStorage localStorage;
    private Gson gson;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        this.localStorage = new LocalStorage(context);
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);

        holder.title.setText(cart.getTitle());
        holder.attribute.setText(cart.getAttribute());
        holder.quantity.setText(cart.getQuantity());

        holder.currency.setText("$");
        holder.price.setText(cart.getPrice());

        double subTotalValue = Double.parseDouble(cart.getPrice()) * Integer.parseInt(cart.getQuantity());
        holder.subTotal.setText(String.format("%.2f", subTotalValue));

        Picasso.get()
                .load(cart.getImage())
                .placeholder(R.drawable.no_image)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                        Log.e("Picasso", "Error loading cart image: " + e.getMessage());
                    }
                });

        holder.plus.setOnClickListener(v -> updateItemQuantity(position, 1));
        holder.minus.setOnClickListener(v -> updateItemQuantity(position, -1));
        holder.delete.setOnClickListener(v -> removeItem(position));
    }

    private void updateItemQuantity(int position, int delta) {
        Cart cart = cartList.get(position);
        int currentQty = Integer.parseInt(cart.getQuantity());
        int newQty = currentQty + delta;

        if (newQty > 0) {
            cart.setQuantity(String.valueOf(newQty));

            double price = Double.parseDouble(cart.getPrice());
            cart.setSubTotal(String.valueOf(price * newQty));

            saveAndRefresh();
            notifyItemChanged(position);
        }
    }

    private void removeItem(int position) {
        cartList.remove(position);
        saveAndRefresh();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartList.size());
    }

    private void saveAndRefresh() {
        String cartStr = gson.toJson(cartList);
        localStorage.setCart(cartStr);

        if (context instanceof CartActivity) {
            ((CartActivity) context).updateTotalPrice();
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, currency, price, quantity, attribute, subTotal;
        ProgressBar progressBar;
        Button plus, minus, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity = itemView.findViewById(R.id.quantity);
            currency = itemView.findViewById(R.id.product_currency);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            delete = itemView.findViewById(R.id.cart_delete);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}