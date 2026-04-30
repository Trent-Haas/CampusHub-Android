package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.BaseActivity;
import com.semo.cisproject.campushub.activity.ProductActivity;
import com.semo.cisproject.campushub.activity.ProductViewActivity;
import com.semo.cisproject.campushub.interfaces.AddorRemoveCallbacks;
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context context;
    private String tag;
    private LocalStorage localStorage;
    private Gson gson;
    private List<Cart> cartList;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        initUtils();
    }

    public ProductAdapter(List<Product> productList, Context context, String tag) {
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
        if (tag != null && tag.equalsIgnoreCase("List")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_products, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_grid_products, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Product product = productList.get(position);
        cartList = ((BaseActivity) context).getCartList();

        holder.title.setText(product.getTitle());
        holder.offer.setText(product.getDiscount());
        holder.attribute.setText(product.getAttribute());
        holder.currency.setText("$");
        holder.price.setText(product.getPrice());

        if (product.getDiscount() == null || product.getDiscount().isEmpty()) {
            holder.offer.setVisibility(View.GONE);
        } else {
            holder.offer.setVisibility(View.VISIBLE);
        }

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

        updateCartUI(holder, product);

        holder.plus.setOnClickListener(v -> updateQuantity(holder, product, 1, position));
        holder.minus.setOnClickListener(v -> updateQuantity(holder, product, -1, position));

        holder.addToCart.setOnClickListener(v -> {
            String qty = holder.quantity.getText().toString();
            if (!qty.equals("0")) {
                double price = Double.parseDouble(product.getPrice());
                double subTotal = price * Integer.parseInt(qty);

                Cart cartItem = new Cart(product.getId(), product.getTitle(), product.getImage(), "$",
                        product.getPrice(), product.getAttribute(), qty, String.valueOf(subTotal));

                cartList = ((BaseActivity) context).getCartList();
                cartList.add(cartItem);
                saveCart();

                if (context instanceof AddorRemoveCallbacks) {
                    ((AddorRemoveCallbacks) context).onAddProduct();
                }
                notifyItemChanged(position);
            } else {
                Toast.makeText(context, "Please Add Quantity", Toast.LENGTH_SHORT).show();
            }
        });

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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });
    }

    private void updateCartUI(MyViewHolder holder, Product product) {
        boolean found = false;
        for (Cart item : cartList) {
            if (item.getId().equalsIgnoreCase(product.getId())) {
                holder.addToCart.setVisibility(View.GONE);
                holder.subTotal.setVisibility(View.VISIBLE);
                holder.quantity.setText(item.getQuantity());
                double subTotal = Double.parseDouble(product.getPrice()) * Integer.parseInt(item.getQuantity());
                holder.subTotal.setText(item.getQuantity() + " X " + product.getPrice() + " = $" + String.format("%.2f", subTotal));
                found = true;
                break;
            }
        }
        if (!found) {
            holder.addToCart.setVisibility(View.VISIBLE);
            holder.subTotal.setVisibility(View.GONE);
            holder.quantity.setText("1");
        }
    }

    private void updateQuantity(MyViewHolder holder, Product product, int delta, int position) {
        int currentQty = Integer.parseInt(holder.quantity.getText().toString());
        int newQty = currentQty + delta;

        if (newQty >= 1) {
            holder.quantity.setText(String.valueOf(newQty));
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getId().equalsIgnoreCase(product.getId())) {
                    double price = Double.parseDouble(product.getPrice());
                    double subTotal = price * newQty;
                    cartList.get(i).setQuantity(String.valueOf(newQty));
                    cartList.get(i).setSubTotal(String.valueOf(subTotal));
                    saveCart();
                    notifyItemChanged(position);
                    break;
                }
            }
        }
    }

    private void saveCart() {
        localStorage.setCart(gson.toJson(cartList));
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, offer, currency, price, quantity, attribute, addToCart, subTotal;
        ProgressBar progressBar;
        CardView cardView;
        Button plus, minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);
            offer = itemView.findViewById(R.id.product_discount);
            currency = itemView.findViewById(R.id.product_currency);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.quantity);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            subTotal = itemView.findViewById(R.id.sub_total);
        }
    }
}