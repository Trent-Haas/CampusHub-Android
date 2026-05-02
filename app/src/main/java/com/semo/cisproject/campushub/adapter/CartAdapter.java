package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);
        if (holder.title != null) holder.title.setText(cart.getTitle());
        if (holder.attribute != null) holder.attribute.setText(cart.getAttribute());
        if (holder.quantity != null) holder.quantity.setText(cart.getQuantity());
        if (holder.currency != null) holder.currency.setText("$");
        if (holder.price != null) holder.price.setText(cart.getPrice());
        if (holder.subTotal != null) {
            try {
                double subTotalValue = Double.parseDouble(cart.getPrice()) * Integer.parseInt(cart.getQuantity());
                holder.subTotal.setText(String.format("%.2f", subTotalValue));
            } catch (Exception e) {
                holder.subTotal.setText("0.00");
            }
        }
        if (holder.imageView != null) {
            Picasso.get().load(cart.getImage()).placeholder(R.drawable.no_image).into(holder.imageView, new Callback() {
                @Override
                public void onSuccess() { if (holder.progressBar != null) holder.progressBar.setVisibility(View.GONE); }
                @Override
                public void onError(Exception e) { if (holder.progressBar != null) holder.progressBar.setVisibility(View.GONE); }
            });
        }
        if (holder.plus != null) holder.plus.setOnClickListener(v -> updateItemQuantity(position, 1));
        if (holder.minus != null) holder.minus.setOnClickListener(v -> updateItemQuantity(position, -1));
        if (holder.delete != null) holder.delete.setOnClickListener(v -> removeItem(position));
    }

    private void updateItemQuantity(int position, int delta) {
        Cart cart = cartList.get(position);
        try {
            int currentQty = Integer.parseInt(cart.getQuantity());
            int newQty = currentQty + delta;
            if (newQty > 0) {
                cart.setQuantity(String.valueOf(newQty));
                double price = Double.parseDouble(cart.getPrice());
                cart.setSubTotal(String.valueOf(price * newQty));
                saveAndRefresh();
                notifyItemChanged(position);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void removeItem(int position) {
        cartList.remove(position);
        saveAndRefresh();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartList.size());
    }

    private void saveAndRefresh() {
        localStorage.setCart(gson.toJson(cartList));
        if (context instanceof CartActivity) { ((CartActivity) context).updateTotalPrice(); }
    }

    @Override
    public int getItemCount() { return cartList != null ? cartList.size() : 0; }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, currency, price, quantity, attribute, subTotal;
        ProgressBar progressBar;
        View plus, minus, delete;
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