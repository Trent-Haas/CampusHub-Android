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
import com.semo.cisproject.campushub.activity.ProductActivity;
import com.semo.cisproject.campushub.model.Category;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    private Context context;
    private String tag;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    public CategoryAdapter(List<Category> categoryList, Context context, String tag) {
        this.categoryList = categoryList;
        this.context = context;
        this.tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (tag != null && tag.equalsIgnoreCase("Home")) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_category, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.title.setText(category.getTitle());

        if (category.getImage() != null && !category.getImage().isEmpty()) {
            Picasso.get().load(category.getImage()).error(R.drawable.no_image).into(holder.imageView, new Callback() {
                @Override public void onSuccess() { holder.progressBar.setVisibility(View.GONE); }
                @Override public void onError(Exception e) { holder.progressBar.setVisibility(View.GONE); }
            });
        } else {
            holder.imageView.setImageResource(R.drawable.no_image);
            holder.progressBar.setVisibility(View.GONE);
        }

        View.OnClickListener clickListener = v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            // Ensure this matches the key in ProductActivity
            intent.putExtra("category_title", category.getTitle());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        };

        holder.cardView.setOnClickListener(clickListener);
        holder.title.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        if (categoryList == null) return 0;
        if (tag != null && tag.equalsIgnoreCase("Home")) {
            return Math.min(categoryList.size(), 6);
        }
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}