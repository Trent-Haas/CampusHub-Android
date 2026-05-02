package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.ProductViewActivity;
import com.semo.cisproject.campushub.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.MyViewHolder> {
    private List<Product> productList;
    private Context context;
    private String tag;

    public PopularProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public PopularProductAdapter(List<Product> productList, Context context, String tag) {
        this.productList = productList;
        this.context = context;
        this.tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (tag != null && tag.equalsIgnoreCase("Home")) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_popular_home_products, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_popular_products, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(String.format("$%s", product.getPrice()));

        Picasso.get().load(product.getImage()).placeholder(R.drawable.no_image).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() { holder.progressBar.setVisibility(View.GONE); }
            @Override
            public void onError(Exception e) { holder.progressBar.setVisibility(View.GONE); }
        });

        View.OnClickListener openDetails = v -> {
            Intent intent = new Intent(context, ProductViewActivity.class);
            intent.putExtra("id", product.getId());
            intent.putExtra("title", product.getTitle());
            intent.putExtra("image", product.getImage());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("attribute", product.getAttribute());
            intent.putExtra("discount", product.getDiscount());
            intent.putExtra("description", product.getDescription());
            context.startActivity(intent);
        };

        holder.cardView.setOnClickListener(openDetails);
        if (holder.shopNow != null) holder.shopNow.setOnClickListener(openDetails);
    }

    @Override
    public int getItemCount() { return productList != null ? productList.size() : 0; }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, price, shopNow;
        ProgressBar progressBar;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            shopNow = itemView.findViewById(R.id.shop_now);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}