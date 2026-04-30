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
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckoutCartAdapter extends RecyclerView.Adapter<CheckoutCartAdapter.MyViewHolder> {

    private List<Cart> cartList;
    private Context context;
    private LocalStorage localStorage;
    private Gson gson;

    public CheckoutCartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        this.localStorage = new LocalStorage(context);
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
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

        try {
            double priceValue = Double.parseDouble(cart.getPrice());
            int qtyValue = Integer.parseInt(cart.getQuantity());
            double subTotalValue = priceValue * qtyValue;
            holder.subTotal.setText(String.format("%.2f", subTotalValue));
        } catch (Exception e) {
            holder.subTotal.setText(cart.getSubTotal());
            Log.e("CheckoutAdapter", "Error calculating subtotal", e);
        }

        holder.plus.setVisibility(View.GONE);
        holder.minus.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

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
                        Log.e("Picasso", "Checkout item image failed: " + e.getMessage());
                    }
                });
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
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            delete = itemView.findViewById(R.id.cart_delete);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
            currency = itemView.findViewById(R.id.product_currency);
        }
    }
}